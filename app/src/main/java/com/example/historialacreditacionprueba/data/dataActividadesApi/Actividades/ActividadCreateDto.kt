package com.example.historialacreditacionprueba.data.dataActividadesApi.Actividades

data class ActividadCreateDto(
    val nombre: String,
    val descripcion: String,
    val fechaInicio: String,    // ISO‑8601, ej “2025-07-28T10:00:00Z”
    val fechaFin: String,
    val creditos: Double,
    val capacidad: Int,
    val dias: Int,              // 1..7 según enum Dias
    val horaInicio: String,     // “08:00:00”
    val horaFin: String,
    val tipoActividad: Int,     // 1..4 según enum TipoActividad
    val estadoActividad: Int,   // 1-Activo, 2-Progreso, 3-Finalizado
    val imagenNombre: String,
    val departamentoId: Int,
    val carreraIds: List<Int>,
    val genero: Int             // 1-Hombre 2-Mujer
)


//data class ActividadCreateDto(
//    val nombre: String,
//    val descripcion: String,
//    @SerializedName("fechaInicio")
//    val fechaInicioIso: String,    // “2025-07-28T10:00:00Z”
//    @SerializedName("fechaFin")
//    val fechaFinIso: String,
//    val creditos: Double,
//    val capacidad: Int,
//    val dias: Int,                  // o String, según tu API
//    val horaInicio: String,         // “08:00:00”
//    val horaFin: String,            // “10:00:00”
//    val tipoActividad: Int,         // enums como enteros
//    val estadoActividad: Int,
//    val imagenNombre: String,
//    val departamentoId: Int,
//    val carreraIds: List<Int>,
//    val genero: Int
//)
