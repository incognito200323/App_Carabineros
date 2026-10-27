package com.example.app_carabineros.data

import com.example.app_carabineros.model.Usuario

object UsuarioRepository{
    private val usuariosRegistrados = mutableListOf(
        Usuario(rut = "12345678-9", nombre = "Estudiante 1", contrasena = "contrasena1"),
        Usuario(rut = "98765432-1", nombre = "Estudiante 2", contrasena = "contrasena2")
    )

    fun validarLogin(rut: String, contrasena: String): Boolean{
        return usuariosRegistrados.any { it.rut == rut && it.contrasena == contrasena }
    }

    fun registrarUsuario(usuario: Usuario){
        usuariosRegistrados.add(usuario)
    }
}