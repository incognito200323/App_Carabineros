package com.example.app_carabineros.data

import android.util.Log
import com.example.app_carabineros.model.Usuario
import com.example.app_carabineros.network.LoginRequest
import com.example.app_carabineros.network.RetrofitClient
import com.example.app_carabineros.network.Usuario as UsuarioApi

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun precargarUsuariosDePrueba() {
        val usuariosIniciales = listOf(
            Usuario(rut = "12345678-9", nombre = "Estudiante 1", contrasena = "contrasena1"),
            Usuario(rut = "98765432-1", nombre = "Estudiante 2", contrasena = "contrasena2")
        )

        usuariosIniciales.forEach { usuario ->
            usuarioDao.insertar(usuario)
            try {
                RetrofitClient.api.register(
                    UsuarioApi(
                        rut = usuario.rut,
                        nombre = usuario.nombre,
                        contrasena = usuario.contrasena
                    )
                )
            } catch (e: Exception) {
                Log.d("USER_REPO", "Precarga en Render omitida o sin red: ${e.message}")
            }
        }
    }

    suspend fun validarLogin(rut: String, contrasena: String): Usuario? {
        val rutLimpio = rut.trim()
        val passLimpia = contrasena.trim()

        try {
            val response = RetrofitClient.api.login(
                LoginRequest(rut = rutLimpio, contrasena = passLimpia)
            )
            if (response.isSuccessful) {
                val remoto = response.body()
                if (remoto != null) {
                    val usuarioLocal = Usuario(
                        rut = remoto.rut,
                        nombre = remoto.nombre,
                        contrasena = passLimpia
                    )
                    usuarioDao.insertar(usuarioLocal)
                    return usuarioLocal
                }
            } else if (response.code() == 400 || response.code() == 401) {
                // El servidor confirmó que las credenciales son inválidas
                return null
            } else {
                Log.e("USER_REPO", "Error en login Render: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("USER_REPO", "Sin conexión en login, usando respaldo local: ${e.message}")
        }

        // Respaldo local solo si falló la conexión a internet
        return usuarioDao.validarLogin(rutLimpio, passLimpia)
    }

    suspend fun registrarUsuario(usuario: Usuario): Boolean {
        val rutLimpio = usuario.rut.trim()
        val nombreLimpio = usuario.nombre.trim()
        val passLimpia = usuario.contrasena.trim()

        try {
            val response = RetrofitClient.api.register(
                UsuarioApi(
                    rut = rutLimpio,
                    nombre = nombreLimpio,
                    contrasena = passLimpia
                )
            )
            if (response.isSuccessful) {
                usuarioDao.insertar(
                    usuario.copy(
                        rut = rutLimpio,
                        nombre = nombreLimpio,
                        contrasena = passLimpia
                    )
                )
                return true
            } else if (response.code() == 400 || response.code() == 409) {
                // El RUT ya existe en PostgreSQL o faltan datos
                return false
            } else {
                Log.e("USER_REPO", "Error al registrar en Render: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("USER_REPO", "Sin conexión al registrar, guardando local: ${e.message}")
        }

        if (usuarioDao.buscarPorRut(rutLimpio) != null) {
            return false
        }
        val resultado = usuarioDao.insertar(
            usuario.copy(
                rut = rutLimpio,
                nombre = nombreLimpio,
                contrasena = passLimpia
            )
        )
        return resultado != -1L
    }
}