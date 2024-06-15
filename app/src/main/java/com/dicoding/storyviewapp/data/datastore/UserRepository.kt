package com.dicoding.storyviewapp.data.datastore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.storyviewapp.data.database.StoryRoomDatabase
import com.dicoding.storyviewapp.data.remote.api.ApiConfig
import com.dicoding.storyviewapp.data.remote.api.ApiService
import com.dicoding.storyviewapp.data.remote.response.ListStoryItem
import com.dicoding.storyviewapp.data.remote.response.ListStoryResponse
import com.dicoding.storyviewapp.data.remote.response.LoginResponse
import com.dicoding.storyviewapp.data.remote.response.UploadResponse
import com.dicoding.storyviewapp.result.Result
import com.dicoding.storyviewapp.utils.StoryRemoteMediator
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val storyRoomDatabase: StoryRoomDatabase
) {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()

    suspend fun register(name: String, email: String, password: String) = apiService.register(name, email, password)

    suspend fun login(email: String, password: String) {
        _loginResult.postValue(Result.Loading)
        try {
            val response = apiService.login(email, password)
            if (!response.error) {
                _loginResult.postValue(Result.Success(response))
            } else {
                _loginResult.postValue(Result.Error("Login failed: ${response.message}"))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            _loginResult.postValue(Result.Error("Login failed: ${errorResponse.message}"))
        } catch (e: Exception) {
            _loginResult.postValue(Result.Error("An unexpected error occurred: ${e.message}"))
        }
    }


    fun listStory(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return try {
            Pager(
                config = PagingConfig(
                    pageSize = 5
                ),
                remoteMediator = StoryRemoteMediator(storyRoomDatabase, userPreference),
                pagingSourceFactory = {
                    storyRoomDatabase.storyDao().getAllStory()
                }
            ).liveData
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    fun uploadStory(file: MultipartBody.Part, description: RequestBody) {
        _isLoading.value = true
        val client = apiService.uploadImage(file, description)
        client.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Repository", "error: ${t.message}")
            }
        })
    }

    fun getStoriesWithLocation(): LiveData<Result<ListStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val getToken = userPreference.getSession().first()
            val apiService = ApiConfig.getApiService(getToken.token)
            val locationStory = apiService.getStoriesWithLocation()
            emit(Result.Success(locationStory))
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(error, UploadResponse::class.java)
            val errorMessage = errorResponse?.message ?: "An error occurred"
            emit(Result.Error("Failed: $errorMessage"))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference,
            storyRoomDatabase: StoryRoomDatabase
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference, storyRoomDatabase)
            }.also { instance = it }

        fun clearInstance() {
            instance = null
        }
    }
}
