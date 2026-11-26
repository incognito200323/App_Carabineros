package com.example.app_carabineros.data

import com.example.app_carabineros.model.Usuario

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun precargarUsuariosDePrueba() {
        val usuariosIniciales = listOf(
            Usuario(rut = "12345678-9", nombre = "Estudiante 1", contrasena = "contrasena1"),
            Usuario(rut = "98765432-1", nombre = "Estudiante 2", contrasena = "contrasena2")
        )
        usuariosIniciales.forEach { usuarioDao.insertar(it) }
    }

    suspend fun validarLogin(rut: String, contrasena: String): Usuario? {
        return usuarioDao.validarLogin(rut.trim(), contrasena.trim())
    }

    suspend fun registrarUsuario(usuario: Usuario): Boolean {
        val rutLimpio = usuario.rut.trim()
        if (usuarioDao.buscarPorRut(rutLimpio) != null) {
            return false // Ya existe un estudiante con ese RUT
        }
        val resultado = usuarioDao.insertar(
            usuario.copy(
                rut = rutLimpio,
                nombre = usuario.nombre.trim(),
                contrasena = usuario.contrasena.trim()
            )
        )
        return resultado != -1L
    }
}