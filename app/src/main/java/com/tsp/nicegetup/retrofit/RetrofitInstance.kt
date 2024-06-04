package com.tsp.nicegetup.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        private  const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        private var INSTANCE: Retrofit? = null

        fun getInstance(): Retrofit {
            if (INSTANCE == null){
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //ConverterFactory: 서버에서 온 JSON 응답을 데이터 클래스 객체로 자동 변환, json이라고도 한다
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE!!
        }

    }

}