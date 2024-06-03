package com.dicoding.storyviewapp.data.remote.api

import com.dicoding.storyviewapp.data.remote.response.ListStoryResponse
import com.dicoding.storyviewapp.data.remote.response.LoginResponse
import com.dicoding.storyviewapp.data.remote.response.RegisterResponse
import com.dicoding.storyviewapp.data.remote.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    //register
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    //login
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ListStoryResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location: Int = 1,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 100
    ): ListStoryResponse

    @GET("stories")
    fun getStories(): Call<ListStoryResponse>

    //upload story
    @Multipart
    @POST("stories")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<UploadResponse>

}