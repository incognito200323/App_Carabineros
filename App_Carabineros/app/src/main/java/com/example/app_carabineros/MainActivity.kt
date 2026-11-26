package com.example.app_carabineros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.LaunchedEffect
import com.example.app_carabineros.data.AppDatabase
import com.example.app_carabineros.data.DocumentoRepository
import com.example.app_carabineros.data.UsuarioRepository
import com.example.app_carabineros.navegacion.AppNavigation
import com.example.app_carabineros.vistas.FondoApp
import com.example.app_carabineros.vistas.VerdeEsfocar
import com.example.app_carabineros.vistas.VerdeEsfocarOscuro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getDatabase(applicationContext)
        val usuarioRepository = UsuarioRepository(db.usuarioDao())
        val documentoRepository = DocumentoRepository(db.documentoDao())

        setContent {
            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) {
                    usuarioRepository.precargarUsuariosDePrueba()
                    documentoRepository.precargarDocumentosIniciales()
                }
            }

            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = VerdeEsfocar,
                    secondary = VerdeEsfocarOscuro,
                    background = FondoApp
                )
            ) {
                AppNavigation(
                    usuarioRepository = usuarioRepository,
                    documentoRepository = documentoRepository
                )
            }
        }
    }
}