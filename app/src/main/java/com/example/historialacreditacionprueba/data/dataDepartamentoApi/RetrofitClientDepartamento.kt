package com.example.historialacreditacionprueba.data.dataDepartamentoApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientDepartamento {

    private const val BASE_URL = "http://10.0.2.2:5091/"

    val departamentoApiService: DepartamentoApiService by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DepartamentoApiService::class.java)
    }
}
