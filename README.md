# JARCK 1.0 — proyecto Android

Aplicación Android nativa sencilla para organizar códigos de canje.

## Funciones
- Pegar códigos, uno por línea.
- Guardar la lista localmente en el dispositivo.
- Seleccionar un renglón y copiar su código.
- Cambiar el estado entre Pendiente, Revisado y Canjeado.
- Abrir la página oficial https://redeem.microsoft.com/.

La app no valida códigos ni realiza intentos automáticos de canje.

## Crear el APK gratis con GitHub Actions (sin Android Studio)
1. Entra a https://github.com/ y crea una cuenta o inicia sesión.
2. Crea un repositorio nuevo, por ejemplo `JARCK-1.0`. Puede ser **Private**.
3. Descomprime este ZIP y sube todos los archivos y carpetas del proyecto al repositorio. Asegúrate de incluir la carpeta oculta `.github/workflows`.
4. Abre la pestaña **Actions** del repositorio.
5. En el flujo **Build JARCK 1.0 APK**, pulsa **Run workflow** si no se inició automáticamente al subir los archivos.
6. Espera a que termine correctamente. Abre la ejecución y baja a **Artifacts**.
7. Descarga `JARCK-1.0-APK.zip`, descomprímelo y encontrarás `app-debug.apk`.
8. Pasa el APK a tu teléfono e instálalo. Android puede pedirte permiso para instalar apps desde esa fuente.

## Generar localmente
También puedes abrir la carpeta en Android Studio y usar **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

Requisitos: Android Studio reciente, JDK 17 y conexión a internet durante la primera sincronización.
