# 🚗 Car Booking Backend

A backend system for a **car rental platform** built with **Spring Boot**, **Java 17**, and **PostgreSQL**.  
Supports multiple roles — **Admin**, **Owner**, and **Customer** — with secure **JWT authentication** and **Swagger API documentation**.

---

## ✨ Features
- 🔐 User registration & login with JWT  
- 👥 Role-based access control (Admin, Owner, Customer)  
- 🚘 Manage cars, rentals, and users (CRUD)  
- 📅 Car ownership & booking management  
- 📘 API documentation via Swagger  
- 🧪 Unit tests using JUnit 5 & Testcontainers  

---

## 🛠️ Tech Stack
- **Java 17**  
- **Spring Boot 3**  
- **PostgreSQL**  
- **Spring Security (JWT)**  
- **Swagger / OpenAPI**  
- **JUnit 5**, **Testcontainers**  
- **Docker**

---

## ⚙️ Setup
1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/car-rent-backend.git
2. Configure the database in **application.yml**

3. Run the project
    ```bash
    ./mvnw spring-boot:run
4. Open Swagger UI
```bash
http://localhost:8080/swagger-ui.html
