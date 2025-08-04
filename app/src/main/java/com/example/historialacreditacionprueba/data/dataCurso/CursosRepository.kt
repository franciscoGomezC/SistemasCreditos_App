package com.example.historialacreditacionprueba.data.dataCurso

import kotlinx.coroutines.flow.Flow

//Capa intermedia entre la vista y la base de datos
interface CursosRepository {
    fun getAllCursos(): Flow<List<Curso>> //obtiene todos los cursos
    fun getCurso(id: Int): Flow<Curso?> //obtiene un curso por su id

    suspend fun insertarCurso(curso: Curso)
    suspend fun updateCurso(curso: Curso)
    suspend fun deleteCurso(curso: Curso)
}