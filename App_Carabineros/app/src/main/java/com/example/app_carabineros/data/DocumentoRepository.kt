package com.example.app_carabineros.data

import com.example.app_carabineros.model.Documento

class DocumentoRepository{
    private val listaDocumento = mutableListOf<Documento>()

    fun obtenerDocumentos(): List<Documento> = listaDocumento

    fun agregarDocumentos(documento: Documento){
        listaDocumento.add(documento)
    }
    fun eliminarDocumento(documento: Documento){
        listaDocumento.remove(documento)
    }
}
