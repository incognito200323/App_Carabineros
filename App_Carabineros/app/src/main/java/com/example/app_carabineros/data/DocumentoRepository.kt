package com.example.app_carabineros.data

import com.example.app_carabineros.model.Documento
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DocumentoRepository(private val documentoDao: DocumentoDao) {

    fun obtenerDocumentos(): Flow<List<Documento>> = documentoDao.obtenerTodos()

    suspend fun agregarDocumento(documento: Documento) {
        documentoDao.insertar(documento)
    }

    suspend fun eliminarDocumento(documento: Documento) {
        documentoDao.eliminar(documento)
    }

    suspend fun precargarDocumentosIniciales() {
        if (documentoDao.obtenerTodos().first().isEmpty()) {
            val apuntesBase = listOf(
                Documento(
                    titulo = "Control de Identidad Preventivo (Ley 20.931)",
                    contenido = "Faculta a Carabineros y PDI a solicitar la identificación de cualquier persona mayor de 18 años en vías o lugares públicos. El procedimiento no puede extenderse por más de 1 hora y debe realizarse respetando siempre la dignidad de la persona.",
                    fechaCreacion = "01/03/2026"
                ),
                Documento(
                    titulo = "Lectura de Derechos del Detenido (Art. 93 y 94 CPP)",
                    contenido = "Al momento de practicar una detención, el funcionario debe informar de manera clara y comprensible el motivo de la misma, el derecho a guardar silencio, a contar con un abogado defensor y a que se informe de su situación a un familiar o persona de confianza.",
                    fechaCreacion = "01/03/2026"
                )
            )
            apuntesBase.forEach { documentoDao.insertar(it) }
        }
    }
}