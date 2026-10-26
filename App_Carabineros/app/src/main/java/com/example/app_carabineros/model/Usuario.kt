package com.example.app_carabineros.model

data class Usuario(
    val rut: String,
    val nombre: String,
    val contrasena: String,
    val activo: Boolean = true
)