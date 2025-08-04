package com.example.historialacreditacionprueba.data.dataActividadesAlumnosApi

import com.example.historialacreditacionprueba.data.dataActividadesAlumnosApi.ActividadesAlumnosDto.AlumnoActividadUpdateDto
import com.example.historialacreditacionprueba.data.dataActividadesAlumnosApi.ActividadesAlumnosDto.AlumnoInscritoDto
import retrofit2.Response
import retrofit2.http.*

interface AlumnoActividadApiService {
    @GET("api/AlumnoActividad/alumnos-inscritos/{actividadId}")
    suspend fun obtenerInscritos(
        @Header("Authorization") bearerToken: String,
        @Path("actividadId") actividadId: Int
    ): List<AlumnoInscritoDto>


    @DELETE("api/AlumnoActividad/{alumnoId}/{actividadId}")
    suspend fun eliminarInscripcion(
        @Header("Authorization") bearerToken: String,
        @Path("alumnoId") alumnoId: Int,
        @Path("actividadId") actividadId: Int
    ): Response<Void>

    @PUT("api/AlumnoActividad/{alumnoId}/{actividadId}")
    suspend fun actualizarEstado(
        @Header("Authorization") bearerToken: String,
        @Path("alumnoId") alumnoId: Int,
        @Path("actividadId") actividadId: Int,
        @Body nuevoEstado: Int
    ): Response<Void>
}
