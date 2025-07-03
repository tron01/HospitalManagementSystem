# Hospital Management System Backend

This repository contains the backend component for the Hospital Management System, built primarily with Java. It provides RESTful APIs and core logic to manage hospital operations, including patient management, doctor scheduling, appointments, billing, and more.

## Features

- **Patient Management**: Create, update, and retrieve patient records securely.
- **Doctor & Staff Management**: Manage doctor profiles, schedules, and staff assignments.
- **Appointment Scheduling**: Book, reschedule, or cancel appointments via API endpoints.
- **Billing & Invoicing**: Automatically generate and manage bills for hospital services.
- **Inventory Tracking**: Monitor and manage medical supplies and equipment.
- **Role-Based Access Control**: Secure endpoints for different user roles (Admin, Doctor, Nurse, Receptionist).

## Technology Stack

- **Language**: Java (99.5%)
- **Containerization**: Docker (Dockerfile included)

## API Routes and Controllers

### AdminController (`/api/admin`)

| Method & Path                                  | Description                                              |
|------------------------------------------------|----------------------------------------------------------|
| `GET /dashboard`                               | Get admin dashboard data                                 |
| `GET /users`                                   | Get all users, filterable by role                        |
| `GET /patients`                                | Get all patients                                         |
| `GET /doctors`                                 | Get all doctors                                          |
| `GET /receptionists`                           | Get all receptionists                                    |
| `POST /patients/create-user`                   | Create a new patient                                     |
| `POST /doctors/create-user`                    | Create a new doctor                                      |
| `POST /receptionists/create-user`              | Create a new receptionist                                |
| `GET /appointments`                            | Get all appointments (optionally filtered by status)     |
| `POST /appointments`                           | Create a new appointment                                 |
| `PUT /appointments/{id}/status`                | Update appointment status                                |
| `PUT /doctors/{id}`                            | Update a doctor's info                                   |
| `PUT /patients/{id}`                           | Update a patient's info                                  |
| `PUT /receptionists/{id}`                      | Update a receptionist's info                             |
| `PUT /appointment/{appointmentId}/payment`     | Update payment status of an appointment                  |
| `PATCH /users/{id}/enable`                     | Enable a user                                            |
| `PATCH /users/{id}/disable`                    | Disable a user                                           |
| `PATCH /users/{id}/lock`                       | Lock a user                                              |
| `PATCH /users/{id}/unlock`                     | Unlock a user                                            |
| `GET /appointment{appointmentId}/doctor-notes` | Get doctor notes for an appointment                      |
| `GET /test`                                    | Test endpoint (returns current user info)                |

### DoctorController (`/api/doctor`)

| Method & Path                                         | Description                                         |
|-------------------------------------------------------|-----------------------------------------------------|
| `POST /register`                                      | Register a new doctor                               |
| `GET /test`                                           | Test endpoint (returns current user info)           |
| `GET /dashboard`                                      | Get doctor dashboard data                           |
| `GET /appointments`                                   | Get appointments for logged-in doctor               |
| `PUT /update`                                         | Update doctor profile for logged-in doctor          |
| `POST /appointments/{appointmentId}/note`             | Add note to an appointment                          |
| `GET /appointments/{appointmentId}/note`              | Get note for an appointment                         |

### PatientController (`/api/patient`)

| Method & Path                                         | Description                                                   |
|-------------------------------------------------------|---------------------------------------------------------------|
| `POST /register`                                      | Register a new patient                                        |
| `PUT /update`                                         | Update own patient info                                       |
| `GET /appointments`                                   | Get appointments for logged-in patient                        |
| `GET /appointment/{appointmentId}/billinfo`           | Get billing info by appointment ID                            |
| `GET /appointment/{appointmentId}/doc_note`           | Get doctor's note by appointment ID                           |
| `GET /test`                                           | Test endpoint (returns current user info)                     |

### ReceptionistController (`/api/receptionist`)

| Method & Path                           | Description                                 |
|-----------------------------------------|---------------------------------------------|
| `POST /appointments`                    | Create an appointment                       |
| `PUT /appointments/{id}/status`         | Update appointment status                   |
| `GET /appointments`                     | Get appointments filtered by status         |
| `GET /test`                             | Test endpoint (returns current user info)   |

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven (for building the project)
- Docker (optional, for containerization)
- A relational database (e.g., MySQL or PostgreSQL)

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/tron01/HospitalManagementSystem.git
   cd HospitalManagementSystem
   ```

2. **Configure Environment Variables**
   - Copy `src/main/resources/application.example.properties` to `application.properties` and update values as required for your environment.

3. **Build the Project**
   ```bash
   mvn clean package
   ```

4. **Run the Application**
   ```bash
   java -jar target/HospitalManagementSystem-*.jar
   ```

   Or, using Docker (if a Dockerfile is provided):

   ```bash
   docker build -t hospital-backend .
   docker run -p 8080:8080 hospital-backend
   ```

5. **API Documentation**
   - The API documentation (e.g., Swagger/OpenAPI) is available at `http://localhost:8080/swagger-ui.html` after running the application.

## Contributing

Contributions are welcome! To contribute:

1. Fork this repository.
2. Create a new branch for your feature or bugfix.
3. Commit your changes with clear messages.
4. Open a pull request describing your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For issues or support, please open an issue on this repository.

```
**Note:** Update database connection details and environment-specific settings as per your deployment requirements.
