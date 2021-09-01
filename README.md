# Veterinaria
Aplicación móvil para Android desarrollada en el marco del bootcamp Android Kotlin que lleve a cabo en 2021. 
Es aplicación destinada a gestionar la atención de consultas en una veterinaria únicamente en fecha actual. La misma cuenta con las siguientes características:
- Al registrar nuevas consultas se toman los datos de la mascota (perro, gato o conejo) y la causa de atención, y luego se selecciona un veterninario de los disponibles.
- Las nuevas consultas se guardan con la fecha actual y con estado de pendientes.
- Una vez que el veterinario atiende la mascota se modifica la consulta agregándole la descripción de las causas y los medicamentos recetados, al hacer esto la consulta pasa al estado de atendida.
- En la pantalla principal se podrán ver las consultas asignadas a todos los veterinarios permitiendo filtrar para ver las de cada uno en particular.
- Por reglas del lugar, a cada veterinario no se le puede asignar más de 5 consultas en el día y la veterínaria no puede atender más de 20 consultas en total en el día.
- En total se cuenta con 5 veterinarios disponibles.

Este sistema se desarrollo utilizando las siguientes tecnologías y herramientas:
- Herramientas de desarrollo: Android Studio
- Lenguaje utilizado: Kotlin
- Motor de base de datos: SQLite
