# Chartered Accountant

A modern web application for managing Chartered Accountant services, including user management, appointment scheduling, and administrative controls.

## 🎯 Project Overview

This is a Spring Boot-based REST API application designed to provide comprehensive appointment and user management services for chartered accounting firms. The application includes authentication, authorization, and role-based access control.

## 🛠️ Technology Stack

### Backend Framework
- **Spring Boot 3.4.0** - Core framework for building REST APIs
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence and ORM

### Database
- **PostgreSQL** - Primary relational database
- **Liquibase** - Database schema versioning and migrations

### Security
- **JWT (JSON Web Tokens)** - Token-based authentication (jjwt 0.11.5)
- **Spring Security** - Role-based access control

### Additional Libraries
- **Lombok** - Code generation for boilerplate reduction
- **Hibernate Validator** - Input validation
- **Maven** - Build and dependency management

### Development & Testing
- **Java 17** - Programming language
- **Spring Boot DevTools** - Development utilities
- **JUnit** - Testing framework

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+**
- **PostgreSQL 12+** (with database and user created)
- **Git** (optional, for cloning the repository)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd chartered-accountant
```

### 2. Configure Database Connection

Update `src/main/resources/application.properties` with your PostgreSQL credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Configure JWT Secret

Update the JWT secret key in `application.properties`:

```properties
app.jwt.secret=your-secret-key-here
app.jwt.expiry=3600000  # Token expiry time in milliseconds (1 hour by default)
```

### 4. Build the Application

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080` by default.

## 📂 Project Structure

```
chartered-accountant/
├── src/main/
│   ├── java/com/example/chartered_accountant/
│   │   ├── CharteredAccountantApplication.java    # Main application entry point
│   │   ├── config/                                 # Configuration classes
│   │   │   └── SecurityConfig.java                 # Spring Security configuration
│   │   ├── controller/                             # REST API endpoints
│   │   │   ├── AdminController.java               # Admin management endpoints
│   │   │   ├── AppointmentController.java         # Appointment management endpoints
│   │   │   ├── AuthController.java                # Authentication endpoints
│   │   │   └── UserController.java                # User management endpoints
│   │   ├── service/                               # Business logic layer
│   │   │   ├── admin/
│   │   │   ├── appointment/
│   │   │   ├── auth/
│   │   │   ├── user/
│   │   │   └── CustomUserDetailsService.java      # Custom user authentication service
│   │   ├── repository/                            # Data access layer
│   │   │   ├── AdminRepo.java
│   │   │   ├── AppointmentRepo.java
│   │   │   └── UserRepo.java
│   │   ├── model/                                 # Domain models
│   │   │   ├── dto/                               # Data Transfer Objects
│   │   │   │   ├── Admin/
│   │   │   │   ├── appointment/
│   │   │   │   ├── Auth/
│   │   │   │   └── user/
│   │   │   └── entity/                            # JPA entities
│   │   │       ├── Admin.java
│   │   │       ├── Appointment.java
│   │   │       ├── BaseEntity.java
│   │   │       └── User.java
│   │   ├── util/                                  # Utility classes
│   │   │   ├── mapper/                            # Entity-DTO mappers
│   │   │   ├── security/                          # Security utilities
│   │   │   │   ├── CustomUserPrincipal.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── JwtUtil.java
│   │   │   └── time/                              # Time utilities
│   │   ├── error/                                 # Exception handling
│   │   │   ├── GlobalHandlerException.java        # Global exception handler
│   │   │   ├── exception/                         # Custom exceptions
│   │   │   │   ├── AdminException.java
│   │   │   │   ├── AppointmentException.java
│   │   │   │   ├── DomainException.java
│   │   │   │   └── UserException.java
│   │   │   └── model/
│   │   │       └── ErrorResponse.java
│   │   └── config/
│   └── resources/
│       ├── application.properties                 # Application configuration
│       ├── db.changelog/                          # Liquibase database migrations
│       │   ├── db.changelog-master.xml
│       │   └── changeset/
│       │       └── 001-init_schema.xml
│       ├── static/                                # Static files (CSS, JS, images)
│       └── templates/                             # HTML templates (if applicable)
├── pom.xml                                        # Maven project configuration
├── mvnw & mvnw.cmd                               # Maven wrapper scripts
└── README.md                                      # This file
```

## 🔐 Authentication & Authorization

### JWT Token-Based Authentication

The application uses JWT (JSON Web Tokens) for stateless authentication:

1. **Login**: Users authenticate via the `/auth/login` endpoint with credentials
2. **Token Generation**: Server returns a JWT token valid for 1 hour
3. **Protected Routes**: Include the token in the `Authorization` header as `Bearer <token>`
4. **Token Validation**: JWT filter validates the token on each request

### Role-Based Access Control

The application supports different user roles:
- **ADMIN** - Full system access
- **USER** - Limited user-specific access
- **GUEST** - Read-only access (if applicable)

Use `@PreAuthorize` annotations on controller methods to enforce role-based permissions.

## 📡 API Endpoints

### Authentication
- `POST /auth/login` - Authenticate user and receive JWT token
- `POST /auth/logout` - Invalidate user session

### User Management
- `GET /users/{id}` - Get user details
- `POST /users` - Create new user
- `PUT /users/{id}` - Update user information
- `PUT /users/{id}/password` - Update user password
- `DELETE /users/{id}` - Delete user account

### Appointment Management
- `GET /appointments` - List all appointments
- `GET /appointments/{id}` - Get appointment details
- `POST /appointments` - Create new appointment
- `PUT /appointments/{id}` - Update appointment
- `DELETE /appointments/{id}` - Cancel appointment

### Admin Management
- `GET /admin/users` - List all users (admin only)
- `GET /admin/appointments` - List all appointments (admin only)
- `POST /admin/users/{id}` - Manage user accounts (admin only)

## 🔧 Configuration

### application.properties

Key configuration options:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres

# JWT Configuration
app.jwt.secret=your-secret-key-here
app.jwt.expiry=3600000  # 1 hour in milliseconds

# Liquibase Configuration
spring.liquibase.enabled=true
spring.liquibase.change-log=db.changelog/db.changelog-master.xml

# JPA Configuration
spring.jpa.open-in-view=false

# Logging
spring.output.ansi.enabled=ALWAYS
```

## 🗄️ Database Management

### Liquibase Migrations

Database schema changes are managed through Liquibase changelog files:

- **Location**: `src/main/resources/db.changelog/`
- **Master File**: `db.changelog-master.xml`
- **Changesets**: Located in `changeset/` directory

Example migration:
```xml
<!-- 001-init_schema.xml -->
<changeSet id="1" author="developer">
    <!-- Create tables and constraints -->
</changeSet>
```

To add new migrations:
1. Create a new XML file in `db.changelog/changeset/`
2. Add the changeset to `db.changelog-master.xml`
3. Liquibase will automatically apply changes on application startup

## ✅ Testing

Run the test suite using Maven:

```bash
mvn test
```

Test files are located in `src/test/java/` directory.

## 🐛 Error Handling

The application implements global exception handling:

- **GlobalHandlerException**: Centralized exception handling for all REST endpoints
- **Custom Exceptions**: Specific exceptions for different domain operations
  - `AdminException` - Admin-related errors
  - `AppointmentException` - Appointment-related errors
  - `UserException` - User-related errors
  - `DomainException` - Base domain exception
- **ErrorResponse**: Standardized error response format

## 📝 Logging

The application uses SLF4J with Lombok's `@Slf4j` annotation for logging:

```java
@Slf4j
public class UserController {
    public void someMethod() {
        log.info("Information message");
        log.error("Error message");
    }
}
```

## 🚨 Security Best Practices

1. **JWT Secret**: Use a strong, randomly generated secret key in production
2. **HTTPS**: Always use HTTPS in production environments
3. **Password Storage**: Passwords are hashed and never stored in plain text
4. **CORS**: Configure CORS settings appropriately in `SecurityConfig.java`
5. **Input Validation**: All user inputs are validated using Hibernate Validator
6. **SQL Injection Prevention**: Use parameterized queries via JPA

## 🔄 Build & Deployment

### Maven Build

```bash
# Clean and build
mvn clean install

# Build without running tests
mvn clean install -DskipTests

# Create executable JAR
mvn clean package
```

### Run JAR File

```bash
java -jar target/chartered-accountant-0.0.1-SNAPSHOT.jar
```

### Environment Variables

Set environment-specific configurations:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://prod-db:5432/ca-db
export SPRING_DATASOURCE_USERNAME=prod_user
export APP_JWT_SECRET=production-secret-key
```

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [JWT Introduction](https://jwt.io/)
- [Liquibase Documentation](https://www.liquibase.org/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

## 🤝 Contributing

1. Create a feature branch: `git checkout -b feature/your-feature`
2. Commit changes: `git commit -m 'Add your feature'`
3. Push to branch: `git push origin feature/your-feature`
4. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Author

**Your Organization/Name**  
Created: February 2026

## 📞 Support

For issues, questions, or suggestions, please:
- Open an issue on the repository
- Contact the development team
- Check existing documentation

---

**Last Updated**: February 24, 2026  
**Version**: 0.0.1-SNAPSHOT
