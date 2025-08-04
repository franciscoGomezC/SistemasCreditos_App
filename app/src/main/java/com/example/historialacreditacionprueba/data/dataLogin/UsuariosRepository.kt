package com.example.historialacreditacionprueba.data.dataLogin

import com.example.historialacreditacionprueba.data.dataAlumno.Alumno

//la interfaz
interface UsuariosRepository {

    // Obtener un alumno por su número de control
    suspend fun obtenerAlumnoPorNumeroControlInicioSesion(numeroControl: String): Alumno?


    suspend fun obtenerUsuarioPorEmail(email: String): Usuario?

    suspend fun insertarUsuario(usuario: Usuario)

    suspend fun login(correo: String, contraseña: String): Usuario? // Si no encuentra el usuario devuelve null

}

// Clase que implementa la interfaz UsuariosRepository
class UsuariosRepositoryImpl(
    private val usuarioDao: UsuarioDao // sirve para interactuar con la base de datos
) : UsuariosRepository { // implementa la interfaz UsuariosRepository

    // Implementa los métodos de la interfaz UsuariosRepository
    override suspend fun obtenerAlumnoPorNumeroControlInicioSesion(numeroControl: String): Alumno? {
        return usuarioDao.obtenerAlumnoPorNumeroControlInicioSesion(numeroControl)
    }

    override suspend fun login(correo: String, contraseña: String): Usuario? {
        return usuarioDao.login(correo, contraseña)
    }

    override suspend fun insertarUsuario(usuario: Usuario) {
        usuarioDao.insertarUsuario(usuario)
    }

    override suspend fun obtenerUsuarioPorEmail(email: String): Usuario? {
        // puedes crear una query extra en el dao si la necesitas
        return null
    }
}
