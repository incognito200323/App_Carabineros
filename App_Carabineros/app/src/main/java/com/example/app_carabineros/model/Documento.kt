package com.example.app_carabineros.model

data class Documento(
    val id: Int,
    val titulo: String,
    val contenido: String,
    val fechaCreacion: String,
    val uriArchivo: String? = null
)