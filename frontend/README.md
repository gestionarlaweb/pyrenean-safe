# PyreneanSafe

Backend API for Pyrenean route planning and weather safety alerts. Built as a final graduation project demonstrating professional backend engineering practices with Java and Spring Boot.

## Tech Stack
- **Language/Framework:** Java 21, Spring Boot 3.3 (Spring Web, Spring Security with JWT, Spring Data JPA)
- **Database:** PostgreSQL 16
- **Containerization:** Docker & Docker Compose
- **Testing:** JUnit 5, Mockito, Testcontainers

## Project Structure
- `backend/`: Spring Boot application source code.
- `frontend/`: Future Flutter PWA client.
- `docker-compose.yml`: Local infrastructure orchestration.

## Configuración de Seguridad y Endpoints (Spring Security)

Para permitir que la aplicación web (Frontend en Vue.js) pueda listar y filtrar los registros sin requerir un proceso de autenticación previo para los visitantes, la seguridad del backend está configurada de la siguiente manera:

* **Lectura (`GET`):** Los endpoints de consulta para picos y refugios (`/api/v1/shelters/**`) son **públicos** (`permitAll()`).
* **Mutaciones (`POST`, `PUT`, `DELETE`):** La creación, actualización y eliminación de registros siguen estando **protegidas** y requieren un token JWT válido (`authenticated()`).


## Inicialización de Datos
El proyecto incluye un inicializador automático (`DataInitializer.java`) que comprueba al arrancar si la base de datos está vacía (`repository.count() == 0`). 
- Si no hay registros, inserta automáticamente una lista inicial con 10 picos y refugios icónicos de los Pirineos con sus respectivas coordenadas y tipos.
- Si ya existen registros, respeta los datos actuales y arranca con normalidad sin duplicar información.