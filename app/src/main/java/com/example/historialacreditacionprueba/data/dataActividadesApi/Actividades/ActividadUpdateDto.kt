package com.example.historialacreditacionprueba.data.dataActividadesApi.Actividades

data class ActividadUpdateDto(

    val descripcion: String,
    val fechaInicio: String,
    val fechaFin: String,
    val creditos: Double,
    val capacidad: Int,
    val estadoActividad: Int,
    val carreraIds: List<Int>

)
