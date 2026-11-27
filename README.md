# App Carabineros (ESFOCAR)

Aplicación móvil Android desarrollada en **Kotlin** con **Jetpack Compose** y **Material 3** para la gestión operativa de usuarios y documentos en el contexto del proyecto EFT (**ESFOCAR**). Integra consumo de servicios backend en **Render** con **PostgreSQL**, respaldo local con **Room**, consumo de APIs externas, pruebas unitarias y compilación firmada en modo `release`.

## Estructura del Proyecto

Paquete principal `com.example.app_carabineros`:

- **`data/`**: repositorios y base de datos local con **Room**.
- **`model/`**: modelos de datos y entidades.
- **`navegacion/`**: rutas y navegación entre vistas.
- **`network/`**: clientes **Retrofit** y servicios de red (backend en Render y APIs externas).
- **`ui.theme/`**: tema y estilos de **Material 3**.
- **`vistas/`**: pantallas en Jetpack Compose, formularios validados y estado de UI.
- **`MainActivity`**: punto de entrada de la app.
- **`test/`**: pruebas unitarias (`app/src/test`).
- **`keepRules/rules.keep`**: reglas para compilación `release`.

## Configuración y Ejecución

### Requisitos previos
- **Android Studio** con Android SDK y `build-tools` (`36.0.0`).
- Conexión a internet para servicios en **Render** y APIs externas.
- Emulador o dispositivo Android con **Depuración USB** activada.

### Ejecutar la app
1. Abre el proyecto en **Android Studio** y espera la sincronización de Gradle.
2. Selecciona el emulador o dispositivo en la barra superior.
3. Haz clic en **Run**.

### Pruebas unitarias
Ejecuta los tests desde Android Studio sobre `app/src/test` o en terminal:

```powershell
.\gradlew.bat test
```

## APK Firmado (`release`) y Verificación

1. En Android Studio: **Build → Generate Signed App Bundle or APK → APK**.
2. Selecciona tu keystore (`esfocar.jks`), ingresa las credenciales, elige la variante **`release`** y compila.
3. El APK queda en `app/build/outputs/apk/release/app-release.apk`.

### Verificar firma con `apksigner`
En PowerShell, entra a `build-tools\36.0.0` y ejecuta (reemplazando `<ruta-del-apk>` por la ruta local de tu archivo):

```powershell
cd "$env:LOCALAPPDATA\Android\Sdk\build-tools\36.0.0"
.\apksigner.bat verify --verbose "<ruta-del-apk>"
.\apksigner.bat verify --verbose --print-certs "<ruta-del-apk>"
```

