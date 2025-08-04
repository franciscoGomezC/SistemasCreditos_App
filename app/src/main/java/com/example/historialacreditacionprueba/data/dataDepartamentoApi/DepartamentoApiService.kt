package com.example.historialacreditacionprueba.data.dataDepartamentoApi

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DepartamentoApiService {
    @GET("api/Departamento/{id}")
    suspend fun getDepartamentoById(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int
    ): DepartamentoDto
}