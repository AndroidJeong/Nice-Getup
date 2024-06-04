package com.tsp.nicegetup.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("forecast")
    fun getWeatherList(@Query("lat") lat: String, @Query("lon") lon: String, @Query("appid") APIkey: String, @Query("units") units: String): Call<WeatherResponse>

}