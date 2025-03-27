# Spring Boot CRUD Application

This project demonstrates how to create a CRUD (Create, Read, Update, Delete) application using Spring Boot, MySQL, and Spring Data JPA with a `Person` entity.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Postman Collection](#postman-collection)
- [Technologies Used](#technologies-used)

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- Java 8 or higher
- Maven
- MySQL
- Postman (optional, for API testing)

## Setup

1. **Clone the repository:**

    ```sh
    git clone https://github.com/codingwitharmand/crud-springboot.git
    cd crud-springboot
    ```

2. **Create a MySQL database:**

    ```sql
    CREATE DATABASE crud-springboot;
    ```

3. **Configure the database connection:**

   Open `src/main/resources/application.properties` and configure the following properties with your MySQL credentials:

    ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/crud-springboot
   spring.datasource.username=<your_user>
   spring.datasource.password=<your_user_password>
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
    ```

4. **Install dependencies and build the project:**

    ```sh
    mvn clean install
    ```

## Running the Application

1. **Run the Spring Boot application:**

    ```sh
    ./mvnw spring-boot:run
    ```

2. The application will start and run on `http://localhost:8080`.

## API Endpoints

The following endpoints are available for the CRUD operations on the `Person` entity:

- **GET /api/persons**
    - Retrieve a list of all persons.

- **GET /api/persons/{id}**
    - Retrieve a single person by ID.

- **POST /api/persons**
    - Create a new person.
    - Example request body:
      ```json
      {
          "name": "John Doe",
          "city": "Los Angeles",
          "phoneNumber" : "999-777-444"
      }
      ```

- **PUT /api/persons/{id}**
    - Update an existing person by ID.
    - Example request body:
      ```json
      {
          "city": "New York",
          "phoneNumber" : "999-777-444"
      }
      ```

- **DELETE /api/persons/{id}**
    - Delete a person by ID.

## Postman Collection

A Postman collection to test each endpoint is included at the root of the project folder. You can import this collection into Postman to quickly test the API.

## Technologies Used

- **Spring Boot:** A framework to simplify the bootstrapping and development of new Spring applications.
- **Spring Data JPA:** A part of the larger Spring Data family to easily implement JPA-based repositories.
- **MySQL:** A relational database management system.
- **Maven:** A build automation tool used primarily for Java projects.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or suggestions.


---

Happy coding!
