package com.example.storyapp.api

import com.example.storyapp.request.LoginRequest
import com.example.storyapp.request.RegisterRequest
import com.example.storyapp.response.DetailStoryResponse
import com.example.storyapp.response.FetchStoriesResponse
import com.example.storyapp.response.LoginResponse
import com.example.storyapp.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("register")
    fun register(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @POST("login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @GET("stories")
    fun fetchStories(
        @Header("Authorization") token: String,
    ): Call<FetchStoriesResponse>

    @GET("stories")
    suspend fun fetchStoriesWithParams(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<FetchStoriesResponse>

    @GET("stories?location=1")
    fun fetchStoriesWithLocation(
        @Header("Authorization") token: String
    ): Call<FetchStoriesResponse>

    @GET("stories/{id}")
    fun fetchDetailStories(
        @Path("id") id:String,
        @Header("Authorization") token: String
    ): Call<DetailStoryResponse>

    @Multipart
    @POST("stories")
    fun postStories(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Header("Authorization") token: String
    ): Call<RegisterResponse>
}