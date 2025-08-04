package com.example.historialacreditacionprueba.data.dataCarreras

import retrofit2.http.GET

interface CarreraApiService {
    @GET("api/Carrera/carreras")
    suspend fun obtenerCarreras(): List<Carrera>
}