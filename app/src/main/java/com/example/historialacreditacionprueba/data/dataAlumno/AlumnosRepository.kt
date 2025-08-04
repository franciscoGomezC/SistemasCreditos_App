package com.example.historialacreditacionprueba.data.dataAlumno

import com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos.AlumnoConCursos
import kotlinx.coroutines.flow.Flow

interface AlumnosRepository {

    fun getAllAlumnos(): Flow<List<Alumno>> // Lista de todos los alumnos

    suspend fun getAlumno(numeroControl: String): Flow<Alumno> // Obtiene un alumno por su ID

    suspend fun insertAlumno(alumno: Alumno)
    suspend fun updateAlumno(alumno: Alumno)
    suspend fun deleteAlumno(alumno: Alumno)

    fun obtenerAlumnosConCursos(): Flow<List<AlumnoConCursos>>

}