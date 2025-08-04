package com.example.historialacreditacionprueba.data.dataAlumno

import androidx.room.*
import com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos.AlumnoConCursos
import kotlinx.coroutines.flow.Flow

// La anotación @Dao indica que esta interfaz es un Data Access Object (DAO) que permite interactuar con la base de datos.
@Dao
interface AlumnoDao {
    // Consulta para obtener una lista de todos los alumnos como un flujo reactivo de datos (Flow).
    @Query("SELECT * FROM alumno")
    fun getAlumnos(): Flow<List<Alumno>>

    // Inserta un alumno en la base de datos. Si hay conflicto (por ejemplo, una clave primaria duplicada),
    // reemplazará el registro existente.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarAlumno(alumno: Alumno)

    // Actualiza un alumno existente en la base de datos.
    @Update
    suspend fun actualizarAlumno(alumno: Alumno)

    // Elimina un alumno de la base de datos.
    @Delete
    suspend fun eliminarAlumno(alumno: Alumno)

    // Consulta para obtener un alumno específico por su numero de control. Devuelve null si no lo encuentra.
    @Query("SELECT * FROM alumno WHERE numeroControl = :numeroControl")
    fun obtenerAlumnoPorId(numeroControl: String): Flow<Alumno>

    @Transaction
    @Query("SELECT * FROM alumno")
    fun obtenerAlumnosConCursos(): Flow<List<AlumnoConCursos>>

}
