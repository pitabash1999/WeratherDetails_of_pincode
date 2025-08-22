# Weather API Project

## Overview

A RESTful API for fetching weather details based on pincode and date. The service first retrieves longitude and latitude coordinates for a given pincode, then fetches weather data from an external API, and finally stores the information in an H2 database.

## Repository
**GitHub URL**: https://github.com/pitabash1999/WeratherDetails_of_pincode.git

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Git
- OpenWeatherMap API account (for API key)

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/pitabash1999/WeratherDetails_of_pincode.git
cd WeratherDetails_of_pincode
```

### 2. Configure API Keys

Create `src/main/resources/application-secrets.properties`:

```properties
weather.api.key=your_openweather_api_key_here
```

**Note**: Never commit API keys to version control. The file `application-secrets.properties` is already in `.gitignore`.

### 3. Build the Project

```poweshell
./mvnw clean install
```

### 4. Run the Application

**Using Maven Spring Boot plugin:**
```poweshell
./mvnw spring-boot:run
```

**Running the JAR file:**
```bash
java -jar target/weather-details-pincode.jar
```

## Application Configuration

The application runs on port `8011` by default. Configuration in `src/main/resources/application.properties`:

```properties
server.port=8011
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# External API endpoints
weather.geo.api.url=http://api.openweathermap.org/geo/1.0/zip
weather.data.api.url=https://api.openweathermap.org/data/2.5/weather
```

## API Endpoints

### Get Weather Details by Pincode and Date

**Endpoint:** `POST /api/weather`

**Request Body:**
```json
{
    "pincode": 752012,
    "for_date": "2020-02-15"
}
```

**Response:**
```json
{
    "pincodeDetails": {
        "pincode": "752012",
        "lon": 86.654,
        "lat": 20.549
    },
    "weatherDetails": {
        "temprature": 24.91,
        "humidity": 91,
        "windSpeed": 4.12,
        "date": "2020-02-15",
        "countryId": "IN",
        "locationName": "Patāmundai"
    }
}
```

## Access Points

- **API Base URL**: http://localhost:8011/api/weather
- **H2 Database Console**: http://localhost:8011/h2-console
- **JDBC URL**: jdbc:h2:mem:testdb
- **Username**: sa
- **Password**: (leave empty)

## External API Integration

The service integrates with two OpenWeatherMap APIs:

1. **Geocoding API**: Gets coordinates from pincode
   ```
   GET http://api.openweathermap.org/geo/1.0/zip?zip={pincode},{country}&appid={api_key}
   ```

2. **Weather API**: Gets weather data from coordinates
   ```
   GET https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={api_key}
   ```

## Running Tests

### Run All Tests

```poweshell
./mvnw test
```



## Testing Strategy

### Controller Tests (`WeatherControllerTest`)
- Tests API endpoints and request validation
- Mock service layer responses
- Verify HTTP status codes and response formats

### Service Tests (`WeatherServiceTest`)
- Tests business logic and external API integration
- Tests error handling and data transformation


## Dependencies

Key dependencies include:
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- H2 Database
- Jackson for JSON processing
- JUnit 5
- Mockito
- RestTemplate for external API calls


### Common Issues

1. **External API Errors**: Check internet connection and API key validity
2. **Port Conflict**: Change port in `application.properties` if 8080 is occupied
3. **Database Issues**: Verify H2 console is accessible at http://localhost:8080/h2-console



## Support

For issues and questions:
1. Check existing test cases for implementation examples
2. Review Spring Boot documentation
3. Verify external API connectivity and credentials
4. Examine H2 database console for data persistence issues
