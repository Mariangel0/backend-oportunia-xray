# OportunIA – Backend (Spring Boot)

Aplicación backend que provee los servicios REST para **OportunIA**, una plataforma que apoya a estudiantes de informática en su preparación laboral: simulación de entrevistas con IA, análisis de currículums con Textract e IA, gestión de usuarios y recomendaciones.

El backend está desarrollado en **Kotlin con Spring Boot**, exponiendo APIs REST y desplegado en infraestructura AWS.  

---

## Tecnologías

- **Kotlin · Spring Boot 3**
- **Spring Data JPA** (Hibernate, PostgreSQL)
- **Spring Security** (JWT)
- **MapStruct** (DTO ⇄ entidades)
- **Gradle Kotlin DSL**
- **AWS SDK** (S3, Textract)
- **OpenAI API**
- **Docker / Docker Compose**

---

## Características principales

- Autenticación JWT con roles y privilegios.
- Gestión de usuarios y estudiantes (Student, Curriculum, CompanyReview, etc.).
- Subida de currículum a S3 y análisis con AWS Textract + parsing automático.
- Simulación de entrevistas con IA (via OpenAI API).
- Análisis automático post-entrevista con feedback y sugerencias.
- Rachas (streak) para seguimiento de uso.
- Endpoints REST consumidos por el frontend Android.

---

## Arquitectura

- **Controller layer:** Endpoints REST.  
- **Service layer:** Lógica de negocio (`InterviewService`, `IAAnalysisService`, etc.).  
- **Repository layer:** Persistencia con Spring Data JPA.  
- **DTOs + Mappers:** Transformación entre entidades y modelos de red.  
- **Infra/AWS integration:** Servicios para S3 y Textract.  
- **AI integration:** Cliente OpenAI para entrevistas y análisis.  

---

## Infraestructura

- **Base de datos:** PostgreSQL  
- **Cloud:** AWS (Elastic Beanstalk, S3, Textract)  
- **Contenedores:** Docker & Docker Compose (app + db)

---

## Requisitos

- **JDK 17**  
- **Gradle 8+**  
- **PostgreSQL** (local o vía Docker)  
- **AWS CLI configurado** (para S3/Textract)  
- **OpenAI API Key**  
