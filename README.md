# 🚀 Employee Management System — Backend (Spring Boot)

> This is the backend REST API for the Employee Management System built using **Spring Boot**, **Hibernate**, and **MySQL**. It powers the React-based frontend.

---

## 🌐 Live API

🔗 [Deployed on Railway](https://joyful-bravery-production.up.railway.app)

---

## ⚙️ Features

- 📋 RESTful APIs to manage employees  
- 🧾 Leave request handling for employees  
- 🔒 CORS configured for frontend integration  
- 🧠 Auto table creation with Hibernate  
- ✅ Connected to Railway-hosted MySQL DB  

---

## 🔧 Tech Stack

**Backend:**  
- Java 17  
- Spring Boot  
- Spring Data JPA (Hibernate)  
- MySQL  
- HikariCP Connection Pool  
- Maven  

**Deployment:**  
- Railway (Free cloud platform)

---

## 🛠️ API Endpoints

| Method | Endpoint                | Description              |
|--------|-------------------------|--------------------------|
| GET    | `/api/employees`        | Get all employees        |
| POST   | `/api/employees`        | Add a new employee       |
| PUT    | `/api/employees/{id}`   | Update an employee       |
| DELETE | `/api/employees/{id}`   | Delete an employee       |
| POST   | `/api/leaves`           | Apply for leave          |

---

## ⚙️ Configuration

### `application.properties`

```properties
spring.application.name=EMS-backend

server.port=8080

spring.datasource.url=jdbc:mysql://<username>:<password>@<host>:<port>/<database>
spring.datasource.username=<your_username>
spring.datasource.password=<your_password>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Optional CORS for frontend (or use Java Config)
spring.mvc.cors.allowed-origins=https://ry-employee-management-system.vercel.app
