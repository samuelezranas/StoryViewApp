package com.dicoding.storyviewapp.data.database

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dicoding.storyviewapp.data.datastore.UserPreference
import com.dicoding.storyviewapp.data.remote.api.ApiService
import com.dicoding.storyviewapp.data.remote.response.ListStoryItem
import com.dicoding.storyviewapp.data.remote.response.ListStoryResponse
import com.dicoding.storyviewapp.data.remote.response.LoginResponse
import com.dicoding.storyviewapp.data.remote.response.UploadResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    private val _login = MutableLiveData<LoginResponse>()
    var login: MutableLiveData<LoginResponse> = _login

    private var _listStory = MutableLiveData<List<ListStoryItem>>()
    var listStory: MutableLiveData<List<ListStoryItem>> = _listStory

    private val _isLoading = MutableLiveData<Boolean>()

    suspend fun register(name: String, email: String, password: String) = apiService.register(name, email, password)

    fun login(email: String, password: String) {
        _isLoading.value = true
        val client = apiService.login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _login.value = response.body()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("Repository", "error: ${t.message}")
            }
        })
    }

    fun listStory() {
        _isLoading.value = true
        val client = apiService.getStories()
        client.enqueue(object : Callback<ListStoryResponse> {
            override fun onResponse(
                call: Call<ListStoryResponse>,
                response: Response<ListStoryResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _listStory.value = response.body()?.listStory
                }
            }

            override fun onFailure(call: Call<ListStoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Repository", "error: ${t.message}")
            }
        })
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
            apiService: ApiService, userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }

        fun clearInstance() {
            instance = null
        }
    }
}