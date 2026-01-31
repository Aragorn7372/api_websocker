<<<<<<< HEAD
# api_websocker
=======
# Funko API

REST API developed with Spring Boot for managing Funko Pop figures and Categories.

## 🚀 Technologies

- **Java 25**
- **Spring Boot 3.5.6**
- **Gradle** - Dependency Management
- **H2 Database** - In-memory database
- **Docker** - Containerization
- **WebSocket** - Real-time support
- **Lombok** - Boilerplate code reduction

## 🛠️ Setup & Run

### Prerequisites

- Java 25
- Docker (optional)

### Local Run

To run the application locally using Gradle:

```bash
./gradlew bootRun
```

The API will be available at `http://localhost:3000` (based on `docker-compose` ports, defaulting to 8080 usually but mapped to 3000 in docker-compose. Please check `application.properties` for local port if not 8080).

### Docker

To run the application using Docker Compose:

```bash
docker-compose up -d --build
```

The API will be accessible at `http://localhost:3000`.

## 📚 API Endpoints

### Funkos (`/funkos`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/funkos` | Get all funkos |
| `GET` | `/funkos/{id}` | Get funko by ID |
| `GET` | `/funkos/name/{name}` | Get funko by name |
| `GET` | `/funkos/min/price/{price}` | Get funkos cheaper than price |
| `GET` | `/funkos/category/{categoria}` | Get funkos by category |
| `GET` | `/funkos/uuid/{uuid}` | Get funko by UUID |
| `POST` | `/funkos` | Create a new funko |
| `PUT` | `/funkos/{id}` | Update a funko |
| `PATCH` | `/funkos/{id}` | Partial update of a funko |
| `DELETE` | `/funkos/{id}` | Delete a funko |

### Categories (`/categorias`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/categorias` | Get all categories |
| `GET` | `/categorias/{id}` | Get category by ID (UUID) |
| `POST` | `/categorias` | Create a new category |
| `PUT` | `/categorias/{id}` | Update a category |
| `DELETE` | `/categorias/{id}` | Delete a category |

### Storage (`/storage`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/storage/{filename}` | Retrieve a stored file/image |

## 🧪 Testing

To run the tests and generate the Jacoco report:

```bash
./gradlew test jacocoTestReport
```

Reports will be available in `build/reports/jacoco/test/html/index.html`.

## 📦 Examples

### Create Funko (POST /funkos)

```json
{
  "name": "Goku Super Saiyan",
  "price": 29.99,
  "cantidad": 15,
  "imagen": "https://example.com/images/goku-funko.jpg",
  "categoria": "ANIME"
}
```

### Update Funko (PUT /funkos/{id})

```json
{
  "name": "Batman Classic",
  "price": 25.5,
  "cantidad": 10,
  "imagen": "https://example.com/images/batman-funko.jpg",
  "categoria": "COMICS"
}
```

## 📂 Project Structure

```text
📦 Funko API
 ┣ 📂 .git
 ┣ 📂 gradle
 ┣ 📂 logs
 ┣ 📂 src
 ┃ ┣ 📂 main
 ┃ ┃ ┣ 📂 java
 ┃ ┃ ┃ ┗ 📂 org.example.funko2
 ┃ ┃ ┃ ┃ ┣ 📂 controller
 ┃ ┃ ┃ ┃ ┣ 📂 dto
 ┃ ┃ ┃ ┃ ┣ 📂 exceptions
 ┃ ┃ ┃ ┃ ┣ 📂 mapper
 ┃ ┃ ┃ ┃ ┣ 📂 model
 ┃ ┃ ┃ ┃ ┣ 📂 repository
 ┃ ┃ ┃ ┃ ┣ 📂 service
 ┃ ┃ ┃ ┃ ┗ 📂 storage
 ┃ ┃ ┗ 📂 resources
 ┃ ┃ ┃ ┣ 📜 application.properties
 ┃ ┃ ┃ ┗ 📜 banner.txt
 ┃ ┗ 📂 test
 ┣ 📜 .env
 ┣ 📜 .gitattributes
 ┣ 📜 .gitignore
 ┣ 🐳 Dockerfile
 ┣ 📜 README.md
 ┣ 🐘 build.gradle.kts
 ┣ 🐳 docker-compose.yml
 ┣ 📜 ejemplosApi.md
 ┣ 🐘 gradlew
 ┣ 📜 gradlew.bat
 ┗ 🐘 settings.gradle.kts
```

## ✒️ Author

<div align="center">
    <a href="https://github.com/Aragorn7372" target="_blank">
        <img src="https://github.com/Aragorn7372.png" width="150px" alt="Aragorn7372" style="border-radius: 50%;">
    </a>
    <h3>Aragorn7372</h3>
    <a href="https://github.com/Aragorn7372" target="_blank">
        <img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white" alt="GitHub Profile">
    </a>
</div>

>>>>>>> dev
