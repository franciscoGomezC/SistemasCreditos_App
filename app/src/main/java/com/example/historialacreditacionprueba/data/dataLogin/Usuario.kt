package com.example.historialacreditacionprueba.data.dataLogin

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey val idUsuario: String, // Para alumno: número de control (8 dígitos), para depto: nombre corto único
    val correoUsuario: String,         // Ej: L23350344@chetumal.tecnm.mx o dae@chetumal.tecnm.mx
    val contraseñaUsuario: String,    // Contraseña simple
    val nombreUsuario: String,        // Nombre del alumno o del responsable del departamento
    val tipoUsuario: String    // "alumno" o "departamento"
)
