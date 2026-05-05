# Bitácora de maiky

Aplicación Android tipo bitácora/diario de actividades basada en fechas. Permite registrar, visualizar, editar y eliminar actividades asociadas a fechas específicas con un diseño moderno Material Design 3.

## Capturas de pantalla

La app incluye: pantalla principal con selector de fechas, creación/edición de actividades, búsqueda, estadísticas con gráfico circular, exportación a PDF/CSV, notificaciones y widget.

## Arquitectura

```
MVVM + Clean Architecture
├── data/           → Capa de datos (Room DB, Repository Implementation)
├── domain/         → Capa de dominio (Models, Repository Interface, Use Cases)
├── di/             → Inyección de dependencias (Hilt Modules)
├── ui/             → Capa de presentación (Jetpack Compose Screens, ViewModels)
├── notification/   → Sistema de notificaciones (WorkManager)
├── export/         → Exportación (PDF, CSV)
└── widget/         → Widget de pantalla de inicio (Glance)
```

### Capas

| Capa | Responsabilidad | Tecnologías |
|------|----------------|-------------|
| **Data** | Persistencia local, implementación de repositorios | Room, SQLite |
| **Domain** | Lógica de negocio, modelos, contratos | Kotlin, Coroutines |
| **UI** | Interfaz de usuario, estado, navegación | Jetpack Compose, Material 3 |
| **DI** | Inyección de dependencias | Hilt/Dagger |

### Flujo de datos

```
UI (Compose) → ViewModel → UseCase → Repository (interface) → RepositoryImpl → DAO → Room DB
```

## Tecnologías

- **Lenguaje**: Kotlin
- **UI**: Jetpack Compose + Material Design 3 (Material You)
- **Base de datos**: Room (SQLite)
- **DI**: Hilt (Dagger)
- **Navegación**: Navigation Compose
- **Async**: Kotlin Coroutines + Flow
- **Notificaciones**: WorkManager
- **Widget**: Jetpack Glance
- **Min SDK**: API 26 (Android 8.0)
- **Target SDK**: API 34 (Android 14)

## Funcionalidades

### Principales
- Selección de fecha con DatePicker de Material 3
- Navegación entre días (anterior/siguiente)
- CRUD completo de actividades (crear, editar, eliminar, completar)
- Listado de actividades por fecha con tarjetas modernas
- Búsqueda de actividades por título o descripción
- Filtros: todas, pendientes, completadas
- Swipe para eliminar (derecha a izquierda) o completar (izquierda a derecha)
- Estadísticas por día con gráfico circular animado
- Modo oscuro/claro automático con Material You (colores dinámicos)
- Animaciones suaves en toda la app

### Extras
- Exportar actividades a PDF
- Exportar actividades a CSV
- Notificaciones/recordatorios opcionales para actividades con hora
- Widget de pantalla de inicio con contador de actividades del día
- Re-programación de notificaciones al reiniciar el dispositivo
- Arquitectura preparada para sincronización futura

## Estructura de la Base de Datos

### Tabla: `activities`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | Long (PK, autoincrement) | Identificador único |
| `title` | String | Título de la actividad |
| `description` | String | Descripción detallada |
| `date` | String (yyyy-MM-dd) | Fecha de la actividad |
| `time` | String? (HH:mm) | Hora opcional |
| `isCompleted` | Boolean | Estado de completado |
| `createdAt` | Long | Timestamp de creación |

## Estructura del Proyecto

```
app/src/main/java/com/maiky/bitacora/
├── BitacoraApp.kt                          # Application (Hilt, Notification Channel)
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt                  # Room Database
│   │   ├── dao/ActivityDao.kt              # Data Access Object
│   │   └── entity/ActivityEntity.kt        # Entidad Room
│   └── repository/
│       └── ActivityRepositoryImpl.kt       # Implementación del repositorio
├── di/
│   ├── DatabaseModule.kt                   # Módulo Hilt para Room
│   └── RepositoryModule.kt                 # Módulo Hilt para Repository
├── domain/
│   ├── model/Activity.kt                   # Modelo de dominio
│   ├── repository/ActivityRepository.kt    # Interfaz del repositorio
│   └── usecase/
│       ├── AddActivityUseCase.kt
│       ├── DeleteActivityUseCase.kt
│       ├── GetActivitiesByDateUseCase.kt
│       ├── GetActivityByIdUseCase.kt
│       ├── GetStatisticsUseCase.kt
│       ├── SearchActivitiesUseCase.kt
│       ├── ToggleCompleteUseCase.kt
│       └── UpdateActivityUseCase.kt
├── export/
│   ├── CsvExporter.kt                     # Exportación a CSV
│   └── PdfExporter.kt                     # Exportación a PDF
├── notification/
│   ├── BootReceiver.kt                    # Re-programa notificaciones al boot
│   ├── ReminderScheduler.kt               # Programador de recordatorios
│   └── ReminderWorker.kt                  # Worker de notificación
├── ui/
│   ├── MainActivity.kt                    # Activity principal (Compose)
│   ├── navigation/NavGraph.kt             # Grafo de navegación
│   ├── screen/
│   │   ├── addedit/
│   │   │   ├── AddEditScreen.kt           # Pantalla crear/editar
│   │   │   └── AddEditViewModel.kt
│   │   ├── home/
│   │   │   ├── HomeScreen.kt              # Pantalla principal
│   │   │   ├── HomeViewModel.kt
│   │   │   └── components/
│   │   │       ├── ActivityCard.kt         # Tarjeta de actividad con swipe
│   │   │       ├── DateSelector.kt         # Selector de fecha
│   │   │       ├── EmptyState.kt           # Estado vacío
│   │   │       ├── FilterChips.kt          # Chips de filtro
│   │   │       └── StatsBar.kt             # Barra de progreso
│   │   ├── search/
│   │   │   ├── SearchScreen.kt            # Pantalla de búsqueda
│   │   │   └── SearchViewModel.kt
│   │   └── statistics/
│   │       ├── StatisticsScreen.kt        # Pantalla de estadísticas
│   │       └── StatisticsViewModel.kt
│   └── theme/
│       ├── Color.kt                       # Paleta de colores
│       ├── Theme.kt                       # Tema Material 3 + Dynamic Colors
│       └── Type.kt                        # Tipografía
└── widget/
    ├── BitacoraWidget.kt                  # Widget Glance
    └── BitacoraWidgetReceiver.kt          # Receiver del widget
```

## Instrucciones de Compilación

### Requisitos
- Android Studio Hedgehog (2023.1.1) o superior
- JDK 17
- Android SDK 34

### Pasos

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/Owgod-Kraken/Agenda-.git
   cd Agenda-
   ```

2. **Abrir en Android Studio**:
   - File → Open → seleccionar la carpeta del proyecto
   - Esperar a que Gradle sincronice las dependencias

3. **Compilar (Debug)**:
   ```bash
   ./gradlew assembleDebug
   ```
   El APK se genera en: `app/build/outputs/apk/debug/app-debug.apk`

4. **Compilar (Release / APK firmado)**:
   
   Primero, generar el keystore (solo la primera vez):
   ```bash
   mkdir -p app/keystore
   keytool -genkeypair -v -keystore app/keystore/release.jks \
     -keyalg RSA -keysize 2048 -validity 10000 \
     -alias bitacora -storepass bitacora2024 -keypass bitacora2024 \
     -dname "CN=Bitacora de Maiky, O=Maiky, C=MX"
   ```
   
   Luego compilar:
   ```bash
   ./gradlew assembleRelease
   ```
   El APK firmado se genera en: `app/build/outputs/apk/release/app-release.apk`

5. **Instalar en dispositivo**:
   ```bash
   adb install app/build/outputs/apk/release/app-release.apk
   ```

## Licencia

Proyecto personal - Todos los derechos reservados.
