# App Carabineros (ESFOCAR)

Aplicación Android creada en Kotlin y Jetpack Compose (Material 3) para el proyecto EFT de ESFOCAR. Permite gestionar usuarios y documentos de apoyo formativo, sincronizar datos con un servidor backend en Render y base de datos PostgreSQL, mantener respaldo local en Room y consultar APIs externas.

## ¿Qué hace la aplicación?

- **Inicio de sesión y registro:** Permite crear cuenta y entrar a la app, combinando validación con el backend en Render y respaldo local en Room.
- **Gestión de documentos:** Permite crear, buscar, leer y eliminar apuntes o normativas policiales, además de adjuntar archivos PDF o imágenes desde el teléfono y abrirlos.
- **Respaldo local y sincronización:** Guarda información en la base de datos local (`Room`) para que siga disponible y se sincronice con el servidor.
- **Servicios externos:** Consume endpoints desplegados en Render y APIs externas según el flujo de la aplicación.
- **Pruebas unitarias:** Incluye pruebas con JUnit 4 en `app/src/test` para verificar los modelos de datos (`ModelosUnitTest`).

## Rutas y servicios que usa la app

- **Backend en Render:** Endpoints REST para gestión de usuarios y documentos (creación, consulta, login/registro y eliminación).
- **APIs externas:** Servicios externos consultados desde la capa `network` con Retrofit.
- **Base de datos local (Room):** Tablas `usuarios` y `documentos` en la base local del dispositivo.

## ¿Cómo ejecutar la app?

### Requisitos
- Android Studio con Android SDK y `build-tools` `36.0.0`.
- Conexión a internet para Render y APIs externas.
- Emulador o teléfono Android con depuración USB activada.

### Pasos para correr
1. Abre el proyecto en Android Studio y espera que termine la sincronización de Gradle.
2. Selecciona el emulador o dispositivo en la barra superior.
3. Presiona **Run**.

### Pruebas unitarias
Se ejecutan desde Android Studio en `app/src/test` o desde la terminal con:

```powershell
.\gradlew.bat test
```

## APK firmado (`release`) y verificación

1. En Android Studio: **Build -> Generate Signed App Bundle or APK -> APK**.
2. Selecciona `esfocar.jks`, ingresa las credenciales, elige `release` y genera el APK.
3. El archivo se crea en `app/build/outputs/apk/release/app-release.apk`.

Para verificar la firma en PowerShell (reemplazando `<ruta-del-apk>` por la ruta en tu equipo):

```powershell
cd "$env:LOCALAPPDATA\Android\Sdk\build-tools\36.0.0"
.\apksigner.bat verify --verbose "<ruta-del-apk>"
.\apksigner.bat verify --verbose --print-certs "<ruta-del-apk>"
```

