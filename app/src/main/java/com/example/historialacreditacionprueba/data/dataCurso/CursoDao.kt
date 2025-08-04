package com.example.historialacreditacionprueba.data.dataCurso

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// La anotación @Dao indica que esta interfaz es un Data Access Object (DAO) que permite interactuar con la base de datos.
@Dao
interface CursoDao {
    //se usan suspend porque son operaciones que pueden tardar y se deben ejecutar con corrutinas

    // Consulta para obtener una lista de todos los cursos como un flujo reactivo de datos (Flow).
    @Query("SELECT * FROM Curso")
    fun getCursos(): Flow<List<Curso>>

    // Inserta un curso en la base de datos. Si hay conflicto (por ejemplo, una clave primaria duplicada),
    // reemplazará el registro existente.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCurso(curso: Curso)

    // Actualiza un curso existente en la base de datos.
    @Update
    suspend fun actualizarCurso(curso: Curso)

    // Elimina un curso de la base de datos.
    @Delete
    suspend fun eliminarCurso(curso: Curso)

    // Consulta para obtener un curso específico por su ID. Devuelve null si no lo encuentra.
    @Query("SELECT * FROM curso WHERE cursoId = :cursoId")
    fun obtenerCursoPorId(cursoId: Int): Flow<Curso?>
}
