package com.example.app_carabineros.vistas

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.app_carabineros.MainActivity
import com.example.app_carabineros.data.UsuarioRepository
import com.example.app_carabineros.model.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistro() {
    var rut by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    val contexto = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Registro de estudiante", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = rut,
            onValueChange = { rut = it },
            label = { Text("Rut del estudiante") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (rut.isNotBlank() && nombre.isNotBlank() && contrasena.isNotBlank()) {
                    // Creamos el usuario y lo guardamos en el repositorio
                    val nuevoUsuario = Usuario(rut.trim(), nombre.trim(), contrasena.trim())
                    UsuarioRepository.registrarUsuario(nuevoUsuario)

                    android.widget.Toast.makeText(contexto, "Usuario creado con éxito", android.widget.Toast.LENGTH_SHORT).show()

                    // Volvemos al Login
                    val intent = Intent(contexto, LoginActivity::class.java)
                    contexto.startActivity(intent)
                    (contexto as? android.app.Activity)?.finish()
                } else {
                    mensajeError = "Debes completar todos los campos"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color(0xFF004B23))
        ) {
            Text("Crear cuenta")
        }

        if (mensajeError.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = mensajeError, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = {
                val intent = Intent(contexto, MainActivity::class.java)
                contexto.startActivity(intent)
                (contexto as? android.app.Activity)?.finish()
            }
        ) {
            Text("Volver al Login")
        }
    }
}