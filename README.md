Descripción general
La aplicación implementa:

Búsqueda remota de libros mediante Google Books API
Visualización de resultados y detalle
Gestión de favoritos en base de datos local
Arquitectura modular basada en Domain, Data y UI
Navegación entre pantallas con Navigation Compose
Manejo de errores y estados de carga

Arquitectura
La aplicación sigue Clean Architecture, separada en tres capas principales:

Domain
Modelos de dominio

Interfaces de repositorio
Casos de uso:
Buscar libros
Detalle remoto
Gestión de favoritos

Data
Retrofit para acceso remoto
Room para almacenamiento local
Mappers DTO → Domain → Entity
Implementación del repositorio:
LibroRepositoryImpl

UI
Pantallas en Jetpack Compose
ViewModels con StateFlow
Estados inmutables
Navegación con Navigation Compose

Tecnologías utilizadas
Kotlin
Jetpack Compose
Hilt
Retrofit + OkHttp
Room
StateFlow
Navigation Compose
Coil

Funcionalidades
Búsqueda de libros por título
Debounce para evitar múltiples peticiones
Validación de entrada
Detalle de libro con portada, título, autor y descripción
Añadir y eliminar favoritos
Listado reactivo de favoritos
Manejo de errores de red y API
Persistencia local con Room

Navegación
Pantallas principales:
BusquedaLibrosScreen
DetalleRemotoScreen
FavoritosScreen
Implementado con NavHost y rutas definidas en un módulo de navegación.

Autor
Laura Álvarez
DAM/DAW – Desarrollo de Aplicaciones Multiplataforma
