# 🏢 Employee Management System (EMS) - Backend

A **Spring Boot** backend for managing employees, leaves, and roles with **role-based access control (RBAC)**.  
This backend powers the EMS web application and exposes secure REST APIs for the frontend.

---

## 📌 Features

- **Employee Management**  
  - Add, update, delete, and view employees  
  - Auto-email employee credentials upon creation

- **Leave Management**  
  - Employees can apply for leave  
  - Admin can approve/reject leave requests  
  - View leave history and status

- **Role-Based Access Control (RBAC)**  
  - Roles: `Admin`, `User`  
  - Secured endpoints based on role

- **Authentication & Authorization**  
  - JWT-based authentication  
  - Secure login & registration APIs

- **MySQL Database Integration**  
  - Persistent storage of employees, leaves, and roles

---

## 🛠️ Tech Stack

- **Backend Framework:** Spring Boot  
- **Database:** MySQL  
- **ORM:** Hibernate / JPA  
- **Security:** Spring Security with JWT  
- **Build Tool:** Maven  
- **Other:** JavaMailSender for email notifications

---

## 🎨 Frontend Code

🔗 [Employee_Management_System-Frontend](https://github.com/Rutikyadav71/Employee_Management_System-frontend)

---

## 🌐 Live Demo

🔗 [Live Site](https://ry-ems.vercel.app/)

---
