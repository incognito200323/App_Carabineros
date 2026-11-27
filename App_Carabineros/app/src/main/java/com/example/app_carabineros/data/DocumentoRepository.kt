package com.example.app_carabineros.data

import android.util.Log
import com.example.app_carabineros.model.Documento
import com.example.app_carabineros.network.Documento as DocumentoApi
import com.example.app_carabineros.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DocumentoRepository(private val documentoDao: DocumentoDao) {

    fun obtenerDocumentos(): Flow<List<Documento>> = documentoDao.obtenerTodos()

    suspend fun agregarDocumento(documento: Documento) {
        try {
            val request = DocumentoApi(
                id = null,
                titulo = documento.titulo,
                contenido = documento.contenido,
                fechaCreacion = documento.fechaCreacion,
                uriArchivo = documento.uriArchivo
            )
            val response = RetrofitClient.api.crearDocumento(request)
            if (response.isSuccessful) {
                val creado = response.body()
                if (creado?.id != null) {
                    guardarSiNoExiste(
                        documento.copy(
                            id = creado.id,
                            uriArchivo = creado.uriArchivo
                        )
                    )
                    return
                }
            } else {
                Log.e("DOC_REPO", "Error al crear en Render: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("DOC_REPO", "Sin conexión al crear documento: ${e.message}")
        }

        documentoDao.insertar(documento)
    }

    suspend fun eliminarDocumento(documento: Documento) {
        try {
            val response = RetrofitClient.api.eliminarDocumento(documento.id)
            if (!response.isSuccessful) {
                Log.e("DOC_REPO", "Error al eliminar en Render: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("DOC_REPO", "Sin conexión al eliminar documento: ${e.message}")
        }
        documentoDao.eliminar(documento)
    }

    suspend fun precargarDocumentosIniciales() {
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

        try {
            val response = RetrofitClient.api.listarDocumentos()
            if (response.isSuccessful) {
                val remotos = response.body().orEmpty()
                if (remotos.isNotEmpty()) {
                    remotos.forEach { doc ->
                        val idRemoto = doc.id ?: return@forEach
                        guardarSiNoExiste(
                            Documento(
                                id = idRemoto,
                                titulo = doc.titulo,
                                contenido = doc.contenido,
                                fechaCreacion = doc.fechaCreacion,
                                uriArchivo = doc.uriArchivo
                            )
                        )
                    }
                    return
                } else {
                    apuntesBase.forEach { agregarDocumento(it) }
                    return
                }
            }
        } catch (e: Exception) {
            Log.e("DOC_REPO", "Error al sincronizar con Render: ${e.message}")
        }

        if (documentoDao.obtenerTodos().first().isEmpty()) {
            apuntesBase.forEach { documentoDao.insertar(it) }
        }
    }

    private suspend fun guardarSiNoExiste(documento: Documento) {
        val locales = documentoDao.obtenerTodos().first()
        if (locales.none { it.id == documento.id }) {
            documentoDao.insertar(documento)
        }
    }
}