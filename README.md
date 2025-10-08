# RetoRick — Android MVVM + Jetpack Compose

## Descripción

App Android que consume la API pública de Rick & Morty y muestra personajes usando arquitectura MVVM, Jetpack Compose, Hilt, Retrofit y Moshi.

## Requisitos
- Android Studio (Arctic Fox o superior recomendado)
- JDK 11+
- Conexión a internet

## Ejecución del proyecto

1. **Clona el repositorio:**
   ```
   git clone https://github.com/lucho9903/RetoRick
   ```
2. **Abre el proyecto en Android Studio:**
   - Selecciona la carpeta `RetoRick`.
3. **Sincroniza dependencias:**
   - Android Studio lo hará automáticamente al abrir el proyecto.
4. **Ejecuta la app:**
   - Haz clic en el botón "Run" 
   - Selecciona un emulador o dispositivo físico.

## Funcionamiento
- Al abrir la app, se muestra la pantalla vacía y se realiza la primera petición.
- Mientras carga la primera vez, se muestra un loader.
- Cada 10 segundos o al pulsar el botón "Refrescar", la app avanza automáticamente a la siguiente página y muestra los primeros 3 personajes.
- Si ocurre un error, se muestra un mensaje tipo Snackbar y se mantienen los datos previos.


