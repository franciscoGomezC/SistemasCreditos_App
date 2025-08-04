package com.example.historialacreditacionprueba.data.dataActividadesApi.Actividades

import com.google.gson.annotations.SerializedName

data class ActividadDto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    @SerializedName("fechaInicio")
    val fechaInicioIso: String,
    @SerializedName("fechaFin")
    val fechaFinIso: String,
    val creditos: Double,
    val capacidad: Int,
    val tipoActividad: Int,
    val estadoActividad: Int,
    val carreraNombres: List<String>,
    val imagenNombre: String
)