# Pokédex Android App

Una aplicación nativa para Android moderna desarrollada con **Kotlin** y **Jetpack Compose** que consume la [PokéAPI](https://pokeapi.co/api/v2/) para mostrar una lista de Pokémon con imágenes, detalles y paginación infinita.

---

## 📱 Características

- **Paginación Infinita**: Carga automática de nuevos Pokémon al desplazarse cerca del final de la lista.
- **UI Moderna con Jetpack Compose**: Interfaz reactiva declarativa utilizando componentes **Material 3**.
- **Modo Claro y Oscuro**: Compatibilidad nativa con temas claro y oscuro (`Dark Mode`).
- **Edge-to-Edge**: Integración completa con las barras del sistema para una experiencia inmersiva.
- **Carga Eficiente de Imágenes**: Procesamiento y visualización asíncrona de sprites utilizando **Coil**.

---

## 🏗️ Arquitectura y Patrones

El proyecto sigue los principios de **Clean Architecture** y el patrón **MVVM (Model-View-ViewModel)**:

```text
com.pedroewaquim.pokedex
├── core/                   # Capa de Dominio y Datos
│   ├── action/             # Casos de uso / Acciones (e.g., GetPokemonList)
│   ├── callback/           # Interfaz de respuesta
│   ├── domain/             # Modelos de dominio (e.g., Pokemon)
│   ├── response/           # DTOs para Retrofit (e.g., PokemonResponse, PokemonListResponse)
│   └── service/            # Definición de servicios API (Retrofit)
├── di/                     # Inyección de Dependencias (Hilt Modules)
├── presentation/           # Capa de Presentación (UI)
│   ├── component/          # Componentes reutilizables de Compose (e.g., PokemonItem)
│   ├── screen/             # Pantallas principales (e.g., PokemonListScreen)
│   ├── theme/              # Configuración del tema Material 3 (Color, Theme)
│   └── viewmodel/          # ViewModels (e.g., PokemonListViewModel)
└── PokedexApplication.kt   # Clase Application anotada con @HiltAndroidApp
```

---

## 🛠️ Tech Stack & Librerías

- **Lenguaje**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Inyección de Dependencias**: [Hilt](https://dagger.dev/hilt/) (Dagger Hilt + KSP)
- **Cliente HTTP**: [Retrofit 2](https://square.github.io/retrofit/) + Gson Converter
- **Asincronía & Estado**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) + `StateFlow` + `collectAsStateWithLifecycle`
- **Carga de Imágenes**: [Coil Compose](https://coil-kt.github.io/coil/compose/)
- **Procesador de Anotaciones**: [KSP (Kotlin Symbol Processing)](https://kotlinlang.org/docs/ksp-overview.html)
- **Pruebas**:
  - Pruebas Unitarias: JUnit 4 + [MockK](https://mockk.io/)
  - Pruebas UI / Instrumentadas: Jetpack Compose Test Rule + Espresso

---

## 📋 Requisitos Previos

- **Android Studio**: Iguana (2023.2.1) o posterior (recomendado Ladybug / Jellyfish)
- **JDK**: Java 17 u 8
- **SDK de Android**:
  - `compileSdk`: 34
  - `minSdk`: 24 (Android 7.0)
  - `targetSdk`: 34

---

## 🚀 Cómo Ejecutar el Proyecto

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu-usuario/pokedex.git
   cd pokedex
   ```

2. **Abrir en Android Studio**:
   Abre Android Studio y selecciona `Open an Existing Project`, seleccionando la carpeta del repositorio.

3. **Compilar y Ejecutar**:
   Selecciona un emulador o dispositivo físico con Android 7.0 (API 24) o superior y presiona **Run** (`Shift + F10`).

---

## 🧪 Pruebas (Testing)

### Ejecutar Pruebas Unitarias
Para ejecutar las pruebas unitarias del ViewModel y la lógica de negocio:
```bash
./gradlew testDebugUnitTest
```

### Ejecutar Pruebas Instrumentadas (UI)
Asegúrate de tener un emulador o dispositivo conectado y ejecuta:
```bash
./gradlew connectedDebugAndroidTest
```

---

## 🌐 API Utilizada

Esta aplicación utiliza la API pública de **PokéAPI**:
- Base URL: `https://pokeapi.co/api/v2/`
- Endpoints consumidos:
  - `GET /pokemon?limit={limit}&offset={offset}` - Obtiene el listado de Pokémon paginado.
  - `GET /pokemon/{id}` - Obtiene la información detallada y los sprites de un Pokémon.
