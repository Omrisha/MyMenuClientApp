package com.example.mymenuclientapp.api

import com.example.mymenuclientapp.models.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @GET("users/search?size=1000&sortOrder=ASC")
    fun getUsers() : Call<List<UserModel>>

    @POST("users")
    fun createUser(@Body user: UserModel) : Call<UserModel>
}