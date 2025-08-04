package com.example.historialacreditacionprueba.data.dataActividadesApi

import androidx.room.Update
import com.example.historialacreditacionprueba.data.dataActividadesApi.Actividades.ActividadCreateDto
import com.example.historialacreditacionprueba.data.dataActividadesApi.Actividades.ActividadDto
import com.example.historialacreditacionprueba.data.dataActividadesApi.Actividades.ActividadUpdateDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ActividadApiService {
    @GET("api/Actividades")
    suspend fun obtenerActividades(
        @Header("Authorization") bearerToken: String
    ): List<ActividadDto>

    @GET("api/Actividades/{id}")
    suspend fun getActividadById(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int
    ): ActividadDto

    @POST("api/Actividades")
    suspend fun createActividad(
        @Header("Authorization") bearerToken: String,
        @Body dto: ActividadCreateDto
    )

    @PUT("api/Actividades/{id}")
    suspend fun updateActividad(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
        @Body dto: ActividadUpdateDto
    )
}
