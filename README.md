# Sensors API

This is a sample Spring REST API project for managing sensors. It allows you to perform CRUD (Create, Read, Update, Delete) operations on sensor data.

## Prerequisites

Before running the project, ensure that you have the following installed:

- Java Development Kit (JDK) 8 or higher
- Apache Maven
- MySQL (optional, you can use an in-memory database like H2 for testing purposes)

## Getting Started

1. Clone the repository:
`git clone https://github.com/denomelchenko/rest-api-sensor.git`
2. Navigate to the project directory: `cd RestApiProject`
3. Build the project using Maven: `mvn clean install`
4. Run the application: `mvn spring-boot:ru`

## API Endpoints
- **GET /measurements/** - Get all measurements.
- **POST /sensors/** - Add a new sensor.
- **POST /measurements/** - Add a new measurement.
- **GET /sensors/** - Get all sensors.
- **GET /measurements/rainyDaysCount/** - Get count of rainy days.


## Technologies Used
* REST API
* Spring
* Hibernate
* Java Persistence API
* Validation of items
* MySQL - Relational database management system
* Maven - Build and dependency management tool

## Contributing
Contributions are welcome! If you find any issues or would like to add new features, please submit a pull request.

## License
This project is licensed under the MIT License. See the LICENSE file for details.