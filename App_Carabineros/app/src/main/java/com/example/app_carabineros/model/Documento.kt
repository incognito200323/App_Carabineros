package com.example.app_carabineros.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documentos")
data class Documento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val contenido: String,
    val fechaCreacion: String,
    val uriArchivo: String? = null
)