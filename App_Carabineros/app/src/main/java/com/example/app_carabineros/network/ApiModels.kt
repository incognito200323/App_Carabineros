package com.example.app_carabineros.network

data class LoginRequest(
    val rut: String,
    val contrasena: String
)

data class Usuario(
    val rut: String,
    val nombre: String,
    val contrasena: String,
    val activo: Boolean = true
)

data class Documento(
    val id: Int? = null,
    val titulo: String,
    val contenido: String,
    val fechaCreacion: String,
    val uriArchivo: String? = null
)