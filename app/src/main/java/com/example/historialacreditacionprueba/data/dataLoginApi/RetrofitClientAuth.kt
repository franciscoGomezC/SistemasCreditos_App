package com.example.historialacreditacionprueba.data.dataLoginApi

import com.example.historialacreditacionprueba.data.dataActividadesApi.Actividades.ActividadCreateDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ActividadApiService {
    @POST("api/Actividades")
    suspend fun createActividad(
        @Header("Authorization") bearerToken: String,
        @Body dto: ActividadCreateDto
    )
}

object RetrofitClientAuth {
    private const val BASE_URL = "http://10.0.2.2:5091/"

    val authApiService: AuthApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }
}
