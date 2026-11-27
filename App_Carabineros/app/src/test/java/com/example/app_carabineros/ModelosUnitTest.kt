package com.example.app_carabineros

import com.example.app_carabineros.model.Documento
import com.example.app_carabineros.model.Usuario
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ModelosUnitTest {

    @Test
    fun pruebaCrearUsuario() {
        val usuario = Usuario(
            rut = "12345678-9",
            nombre = "Estudiante 1",
            contrasena = "contrasena1"
        )
        assertEquals("12345678-9", usuario.rut)
        assertTrue(usuario.activo)
    }

    @Test
    fun pruebaCrearDocumentoSinAdjunto() {
        val doc = Documento(
            titulo = "Control de Identidad",
            contenido = "Resumen del procedimiento",
            fechaCreacion = "27/11/2026"
        )
        assertEquals("Control de Identidad", doc.titulo)
        assertEquals(null, doc.uriArchivo)
    }

    @Test
    fun pruebaCopiarDocumentoConId() {
        val original = Documento(
            titulo = "Lectura de Derechos",
            contenido = "Art. 93 y 94 CPP",
            fechaCreacion = "27/11/2026"
        )
        val copia = original.copy(id = 10)
        assertEquals(10, copia.id)
        assertEquals(original.titulo, copia.titulo)
    }
}