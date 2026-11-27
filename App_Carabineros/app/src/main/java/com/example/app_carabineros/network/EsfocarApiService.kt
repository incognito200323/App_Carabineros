package com.example.app_carabineros.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EsfocarApiService {

    @GET("api/v1/auth/health")
    suspend fun health(): Response<Any>

    @POST("api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<Usuario>

    @POST("api/v1/auth/register")
    suspend fun register(@Body usuario: Usuario): Response<Usuario>

    @GET("api/v1/documentos")
    suspend fun listarDocumentos(): Response<List<Documento>>

    @POST("api/v1/documentos")
    suspend fun crearDocumento(@Body documento: Documento): Response<Documento>

    @DELETE("api/v1/documentos/{id}")
    suspend fun eliminarDocumento(@Path("id") id: Int): Response<Unit>
}