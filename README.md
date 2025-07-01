Here’s a complete and professional `README.md` file for this project, including:

* Setup instructions
* Environment variables
* Postman collection
* API endpoints
* Contribution notes

---

````markdown
# Paga Assessment - Farm Management API

A backend system built with **Spring Boot** and **MySQL**, designed to manage farms, planting and harvesting records, and generate seasonal reports. This project serves as a submission for the Paga Software Engineering assessment.

---

## 🔧 Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok
- Postman (API testing)

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/dmadindividual/Paga_Assessment.git
cd Paga_Assessment
````

### 2. Configure Your MySQL Database

Update your `application.properties` file (under `src/main/resources/`) with your local MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/paga
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
server.port=8081
```

💡 Replace `YOUR_PASSWORD` with your actual MySQL root password and ensure the database `paga` exists.

### 3. Build and Run the App

```bash
./mvnw spring-boot:run
```

Once running, the app will be accessible at:

```
http://localhost:8081
```

---

## 🧪 Postman Collection

You can test all endpoints using this shared Postman collection:

🔗 [Postman Collection – Paga API](https://www.postman.com/avionics-explorer-29622376/paga/collection/kviiw0g/paga?action=share&creator=29599021)

---

## 🌱 API Endpoints

> All endpoints are prefixed by `/api`

### ✅ **Farm Controller**

| Method | Endpoint                      | Description                            |
| ------ | ----------------------------- | -------------------------------------- |
| POST   | `/api/farms`                  | Create a new farm                      |
| GET    | `/api/farms`                  | Get all farms                          |
| GET    | `/api/farms/{id}`             | Get farm by ID                         |
| GET    | `/api/farms/name?name={name}` | Get farm by name (use query param)     |
| PUT    | `/api/farms/{id}`             | Update farm name                       |
| DELETE | `/api/farms/{id}`             | Delete a farm (if no planting/harvest) |
| GET    | `/api/farms/{id}/details`     | Get full planting/harvest records      |

---

### 🌾 **Planting Controller**

| Method | Endpoint                         | Description              |
| ------ | -------------------------------- | ------------------------ |
| POST   | `/api/plantings`                 | Create a planting record |
| GET    | `/api/plantings`                 | Get all plantings        |
| GET    | `/api/plantings/{id}`            | Get planting by ID       |
| GET    | `/api/plantings/farm/{farmId}`   | Get plantings by farm ID |
| GET    | `/api/plantings/season/{season}` | Get plantings by season  |
| PUT    | `/api/plantings/{id}`            | Update planting record   |
| DELETE | `/api/plantings/{id}`            | Delete planting record   |

---

### 🌽 **Harvest Controller**

| Method | Endpoint                        | Description             |
| ------ | ------------------------------- | ----------------------- |
| POST   | `/api/harvests`                 | Create a harvest record |
| GET    | `/api/harvests`                 | Get all harvests        |
| GET    | `/api/harvests/{id}`            | Get harvest by ID       |
| GET    | `/api/harvests/farm/{farmId}`   | Get harvests by farm ID |
| GET    | `/api/harvests/season/{season}` | Get harvests by season  |
| PUT    | `/api/harvests/{id}`            | Update harvest record   |
| DELETE | `/api/harvests/{id}`            | Delete harvest record   |

---

### 📊 **Report Controller**

| Method | Endpoint                       | Description                         |
| ------ | ------------------------------ | ----------------------------------- |
| GET    | `/api/reports/farms?season=q1` | Get yield report per farm by season |
| GET    | `/api/reports/crops?season=q1` | Get yield report per crop by season |

---

## 📂 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── topg.paga/
│   │       ├── farm/
│   │       ├── planting/
│   │       ├── harvest/
│   │       └── report/
│   └── resources/
│       └── application.properties
```

---

## ⚠️ Notes

* Ensure MySQL is running before starting the app.
* You **must create** the `paga` database manually before running.
* Season values are `Q1`, `Q2`, `Q3`, `Q4` (case-insensitive in APIs).
* Delete restrictions: Farms with existing plantings or harvests **cannot be deleted**.

---

## 📬 Contribution

For feedback or contributions, fork this repository and create a pull request, or contact the maintainer.

---

## 🔗 Repo

📍 GitHub: [https://github.com/dmadindividual/Paga\_Assessment](https://github.com/dmadindividual/Paga_Assessment)

```

---

```
