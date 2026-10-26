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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun PantallaLogin(){
    val contexto = LocalContext.current
    var rut by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar Sesion", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        //Campo rut
        TextField(
            value = rut,
            onValueChange = { rut = it },
            label = { Text("Rut del Estudiante") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        //Campo Contraseña
        TextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                // Navega a la pantalla principal
                val intent = Intent(contexto, PrincipalActivity::class.java)
                contexto.startActivity(intent)

                // Cierra la pantalla de Login actual
                (contexto as? android.app.Activity)?.finish()
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Ingresar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = {
            // Navegar a la pantalla de registro
        }) {
            Text("Registrarse")
        }
    }
}