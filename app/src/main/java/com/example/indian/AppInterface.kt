package com.example.indian

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AppInterface {
    @Headers("X-Api-Key:m6Al0C8csuuaCc5UyN7hPg==3LWLpdOpJGBf4wxO")
    @GET("cats")
    fun fetchOfferList(): Call<List<Data>>
}
