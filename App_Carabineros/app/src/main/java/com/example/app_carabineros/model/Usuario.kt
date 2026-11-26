package com.example.app_carabineros.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey val rut: String,
    val nombre: String,
    val contrasena: String,
    val activo: Boolean = true
)