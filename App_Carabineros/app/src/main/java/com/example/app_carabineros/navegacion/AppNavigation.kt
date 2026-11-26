package com.example.app_carabineros.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.app_carabineros.data.DocumentoRepository
import com.example.app_carabineros.data.UsuarioRepository
import com.example.app_carabineros.model.Usuario
import com.example.app_carabineros.vistas.PantallaBienvenida
import com.example.app_carabineros.vistas.PantallaDocumentos
import com.example.app_carabineros.vistas.PantallaLogin
import com.example.app_carabineros.vistas.PantallaPrincipal
import com.example.app_carabineros.vistas.PantallaRegistro

private enum class Pantalla {
    BIENVENIDA,
    LOGIN,
    REGISTRO,
    PRINCIPAL,
    DOCUMENTOS
}

@Composable
fun AppNavigation(
    usuarioRepository: UsuarioRepository,
    documentoRepository: DocumentoRepository
) {
    var pantallaActual by remember { mutableStateOf(Pantalla.BIENVENIDA) }
    var usuarioActivo by remember { mutableStateOf<Usuario?>(null) }

    when (pantallaActual) {
        Pantalla.BIENVENIDA -> PantallaBienvenida(
            onIrALogin = { pantallaActual = Pantalla.LOGIN },
            onIrARegistro = { pantallaActual = Pantalla.REGISTRO }
        )

        Pantalla.LOGIN -> PantallaLogin(
            usuarioRepository = usuarioRepository,
            onVolver = { pantallaActual = Pantalla.BIENVENIDA },
            onIrARegistro = { pantallaActual = Pantalla.REGISTRO },
            onLoginExitoso = { usuario ->
                usuarioActivo = usuario
                pantallaActual = Pantalla.PRINCIPAL
            }
        )

        Pantalla.REGISTRO -> PantallaRegistro(
            usuarioRepository = usuarioRepository,
            onVolverALogin = { pantallaActual = Pantalla.LOGIN },
            onRegistroExitoso = { pantallaActual = Pantalla.LOGIN }
        )

        Pantalla.PRINCIPAL -> {
            val activo = usuarioActivo
            if (activo != null) {
                PantallaPrincipal(
                    usuario = activo,
                    onCerrarSesion = {
                        usuarioActivo = null
                        pantallaActual = Pantalla.LOGIN
                    },
                    onAbrirDocumentos = {
                        pantallaActual = Pantalla.DOCUMENTOS
                    }
                )
            } else {
                pantallaActual = Pantalla.LOGIN
            }
        }

        Pantalla.DOCUMENTOS -> PantallaDocumentos(
            documentoRepository = documentoRepository,
            onVolver = { pantallaActual = Pantalla.PRINCIPAL }
        )
    }
}