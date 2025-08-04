package com.example.historialacreditacionprueba.data.dataCarreras

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientCarrera {
    private const val BASE_URL = "http://10.0.2.2:5091/"

    val carreraApiService: CarreraApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarreraApiService::class.java)
    }
}