package com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface AlumnoCursoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarAlumnoCursoCrossRef(crossRef: AlumnoCursoCrossRef)

    //se crea una función para obtener los cursos de un alumno
    @Transaction
    @Query("SELECT * FROM alumno WHERE numeroControl = :numeroControl")
    fun obtenerCursosDeAlumno(numeroControl: String): Flow<List<AlumnoConCursos>> //se cambió a string por la clase Alumno

    //se crea una función para obtener los alumnos de un curso
    @Transaction
    @Query("SELECT * FROM curso WHERE cursoId = :cursoId")
    fun obtenerAlumnosDeCurso(cursoId: Int): Flow<List<CursoConAlumnos>>

    @Delete
    suspend fun eliminarAlumnoCursoCrossRef(crossRef: AlumnoCursoCrossRef)

}
