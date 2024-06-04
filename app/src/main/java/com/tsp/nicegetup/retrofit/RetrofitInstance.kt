package com.tsp.nicegetup.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        private  const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        private var INSTANCE: Retrofit? = null

        private val okHttpClient by lazy {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }



        fun getInstance(): Retrofit {
            if (INSTANCE == null){
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    //ConverterFactory: 서버에서 온 JSON 응답을 데이터 클래스 객체로 자동 변환, json이라고도 한다
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE!!
        }

    }

}