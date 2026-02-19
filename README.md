Task Manager App - Full Stack (Angular & Java)
Este proyecto es una aplicación web para la gestión de tareas, desarrollada como parte de una prueba técnica para el rol de Desarrollador Fullstack. La solución integra una arquitectura de tres capas: Frontend en Angular, Backend en Servlets Java y persistencia en Oracle mediante procedimientos almacenados PL/SQL.

🚀 Tecnologías Utilizadas

Backend 

Java 21
Servlets Java (Jakarta EE)
JDBC para conexión a base de datos
Gson para serialización/deserialización JSON 
Oracle Database con lógica encapsulada en el paquete TASK_PKG 


Frontend 

Angular 17 (Standalone Components)
TypeScript
Bootstrap para el diseño responsivo
RxJS para el manejo de flujos asíncronos y errores 

🏗️ Configuración del Proyecto

1. Base de Datos (Oracle PL/SQL)

Antes de iniciar el backend, se deben ejecutar los scripts para crear la tabla TASKS y el paquete TASK_PKG:

Atributos de Tabla: TASK_ID (PK), TITLE, DESCRIPTION, COMPLETED, CREATED_AT y UPDATED_AT.

Paquete: TASK_PKG incluye procedimientos para GET_ALL_TASKS, GET_TASK_BY_ID, CREATE_TASK, UPDATE_TASK y DELETE_TASK.


2. Backend (Java Servlets) 

Configurar las credenciales de conexión JDBC en la clase DatabaseConfig o similar.
Desplegar el proyecto en un contenedor Apache Tomcat (puerto predeterminado 8080).


Endpoints expuestos: 

GET /api/tasks - Lista todas las tareas.

POST /api/tasks - Crea una nueva tarea.

PUT /api/tasks/{id} - Actualiza una tarea.

DELETE /api/tasks/{id} - Elimina una tarea.

3. Frontend (Angular) 

Navegar a la carpeta del proyecto frontend.
Instalar dependencias: npm install.
Ejecutar el servidor de desarrollo: ng serve.
Acceder a http://localhost:4200.

🛠️ Funcionalidades Implementadas

Gestión de Tareas 

Visualización: Listado dinámico con TaskListComponent que muestra título, descripción y estado.
Formulario: TaskFormComponent para creación y edición con validación de campos obligatorios (título).
Servicios: TaskService centraliza las llamadas HTTP y el manejo de errores mediante catchError de RxJS.


Seguridad y Comunicación

CORS: El backend está configurado para permitir solicitudes desde el origen del frontend (http://localhost:4200).

Manejo de Estados: Implementación de códigos de estado HTTP correctos (201 Created, 404 Not Found, 500 Internal Error) para una comunicación robusta.

📁 Estructura del Código

/backend: Contiene el código Java (Servlets, DAO, Modelos) y scripts SQL.

/frontend: Contiene la aplicación Angular (Componentes, Servicios, Modelos).
