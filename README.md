# PyreneanSafe

Backend API and Web Frontend for Pyrenean route planning and weather safety alerts. Built as a final graduation project demonstrating professional full-stack engineering practices with Java, Spring Boot, and Vue.js.

## Tech Stack

- **Backend:** Java 21, Spring Boot 3.3 (Spring Web, Spring Security with JWT, Spring Data JPA)
- **Frontend:** Vue.js 3 + Vite, Axios
- **Database:** PostgreSQL 16
- **Containerization:** Docker & Docker Compose
- **Testing:** JUnit 5, Mockito, Testcontainers

## Project Structure

- `backend/`: Spring Boot application source code.
- `frontend/`: Vue.js web client dashboard.
- `docker-compose.yml`: Local infrastructure orchestration.

## Configuración de Seguridad y Endpoints (Spring Security)

Para permitir que la aplicación web (Frontend en Vue.js) pueda listar y filtrar los registros sin requerir un proceso de autenticación previo para los visitantes, la seguridad del backend está configurada de la siguiente manera:

* **Lectura (`GET`):** Los endpoints de consulta para picos y refugios (`/api/v1/shelters/**`) son **públicos** (`permitAll()`).
* **Mutaciones (`POST`, `PUT`, `DELETE`):** La creación, actualización y eliminación de registros siguen estando **protegidas** y requieren un token JWT válido (`authenticated()`).

## Cómo arrancar el proyecto en local

1. **Levantar la base de datos con Docker y arrancar el proyecto en local:**
   ```bash
   docker compose up -d
   cd backend
      mvn spring-boot:run
   ```

2. **Arrancar el Frontend (en una nueva terminal):**
   ```bash
   cd frontend
   npm run dev
      La aplicación web estará disponible en http://localhost:5173/
   ```
   ## Inicialización de Datos
El proyecto incluye un inicializador automático (`DataInitializer.java`) que comprueba al arrancar si la base de datos está vacía (`repository.count() == 0`). 
- Si no hay registros, inserta automáticamente una lista inicial con 10 picos y refugios icónicos de los Pirineos con sus respectivas coordenadas y tipos.
- Si ya existen registros, respeta los datos actuales y arranca con normalidad sin duplicar información.
