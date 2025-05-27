# 🏛️ Architecture Portfolio Management System

A full-featured backend application built with **Java 21** and **Spring Boot**, designed to manage architecture projects, user reviews, photo galleries, and tag-based filtering with full CRUD support.

---

## 🚀 Features

### 👤 User Management
- User Registration and Login with role-based access (`USER`, `ADMIN`)

### 📅 Project Management
- Create, Update, Delete Projects
- Search Projects by name or description
- Pagination and dynamic sorting (by title, date, rating)
- Tag management: Add, remove, edit, and filter by tags

### 🌟 Review System
- Add, update, delete reviews for projects
- View reviews by project
- Average rating calculation per project

### 📷 Photo Gallery
- Upload, view, and delete project-related photos

---

## 🛠️ Technologies Used

- Java 21
- Spring Boot 3.4.x
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- Lombok

---

## 🔗 API Endpoints

### 🔐 AuthController (`/api/auth`)

| Method | Endpoint | Description         |
|--------|----------|---------------------|
| POST   | `/register` | Register new user |
| POST   | `/login`    | Login and get token |

### 📅 ProjectController (`/api/v1/project`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/create` | Create new project |
| GET | `/all` | Get all projects |
| GET | `/paginated?offset=&limit=` | Get paginated projects |
| DELETE | `/{projectId}` | Delete project by ID |
| PUT | `/update` | Update project |
| GET | `/search?name=&description=` | Search projects by name/description |
| GET | `/sorted?direction=&offset=&limit=` | Sort projects (asc/desc) |
| GET | `/sorted-by?sortBy=&sortOrder=&offset=&limit=` | Dynamic sort by field |
| GET | `/sorted-by-rating?sortOrder=` | Sort by average rating |
| GET | `/{id}` | Get project by ID |
| PATCH | `/add-tags` | Add tags to project |
| PATCH | `/remove-tag` | Remove a tag from project |
| PATCH | `/edit-tag` | Edit tag name |
| GET | `/filter?tag=` | Filter projects by single tag |
| GET | `/filter-by-tags?tags=` | Filter by multiple tags |

### 📷 PhotoGalleryController (`/api/v1/photo-gallery`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/upload` | Upload photo to a project |
| GET | `/project/{projectId}` | Get all photos for a project |
| DELETE | `/delete-photo/{id}` | Delete photo by ID |

### 💬 ReviewController (`/api/v1/review`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Add review |
| PUT | `/edit` | Edit review (with userId in header) |
| DELETE | `/{id}` | Delete review by ID |
| GET | `/project/{projectId}` | Get reviews for a project |

### 👤 UserController (`/api/v1/user`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Get all users |

## ✅ Getting Started

### 🔧 Prerequisites

- Java 21
- Maven
- PostgreSQL (with a database named `architecture_project`)

---

## 🚦 Run Locally

```bash
git clone https://github.com/senagokhan/java-backend-architecture-portfolio-management-system.git
cd java-backend-architecture-portfolio-management-system

---
