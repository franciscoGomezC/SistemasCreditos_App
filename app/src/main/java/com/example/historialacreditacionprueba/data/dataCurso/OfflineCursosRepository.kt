package com.example.historialacreditacionprueba.data.dataCurso

import kotlinx.coroutines.flow.Flow

class OfflineCursosRepository(private val cursoDao: CursoDao) : CursosRepository {

    // Obtiene todos los cursos como un flujo reactivo.
    override fun getAllCursos() = cursoDao.getCursos()

    // Obtiene un curso específico por su ID como Flow, puede ser null si no se encuentra.
    override fun getCurso(id: Int): Flow<Curso?> = cursoDao.obtenerCursoPorId(id)

    // Inserta un nuevo curso o reemplaza si ya existe con el mismo ID.
    override suspend fun insertarCurso(curso: Curso) = cursoDao.insertarCurso(curso)

    // Actualiza un curso existente.
    override suspend fun updateCurso(curso: Curso) = cursoDao.actualizarCurso(curso)

    // Elimina un curso existente.
    override suspend fun deleteCurso(curso: Curso) = cursoDao.eliminarCurso(curso)
}