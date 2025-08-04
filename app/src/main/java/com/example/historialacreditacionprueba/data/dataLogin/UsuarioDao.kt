package com.example.historialacreditacionprueba.data.dataLogin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.historialacreditacionprueba.data.dataAlumno.Alumno

@Dao
interface UsuarioDao {

    @Query(" SELECT * FROM Alumno WHERE numeroControl = :numeroControl ")
    suspend fun obtenerAlumnoPorNumeroControlInicioSesion(numeroControl: String): Alumno?


    @Query("SELECT * FROM Usuario WHERE correoUsuario = :correo AND contraseñaUsuario = :contraseña LIMIT 1")
    suspend fun login(correo: String, contraseña: String): Usuario? // Si no encuentra el usuario devuelve null

    // Inserta un alumno en la base de datos. Si hay conflicto (por ejemplo, una clave primaria duplicada),
    // reemplazará el registro existente.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: Usuario)
}
