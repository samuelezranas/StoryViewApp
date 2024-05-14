package com.dicoding.storyviewapp.data.remote

import com.dicoding.storyviewapp.data.response.ListStoryResponse
import com.dicoding.storyviewapp.data.response.LoginResponse
import com.dicoding.storyviewapp.data.response.RegisterResponse
import com.dicoding.storyviewapp.data.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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

    //list story
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