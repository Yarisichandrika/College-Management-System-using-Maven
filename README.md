# College Management System

A full-stack web application for managing college activities including student enrollment, faculty assignments, course management, and attendance tracking.

## Technology Stack

### Backend
- **Java 21+**
- **Spring Boot 3.2.0**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA** (Hibernate)
- **MySQL Database**
- **Maven** (Build Tool)

### Frontend
- **React 18**
- **Vite** (Build Tool)
- **React Router** (Navigation)
- **Axios** (API Client)
- **Bootstrap 5** (Styling)

## Project Structure

```
college-management-system/
├── backend/
│   ├── src/main/java/com/college/management/
│   │   ├── config/          # Security and application configuration
│   │   ├── controller/      # REST API controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── entity/          # JPA Entities (Database tables)
│   │   ├── enums/           # Enumerations
│   │   ├── exception/       # Custom exceptions
│   │   ├── repository/      # JPA Repositories
│   │   ├── security/        # JWT and Security components
│   │   └── service/         # Business logic layer
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
└── frontend/
    ├── src/
    │   ├── components/      # Reusable React components
    │   ├── pages/           # Page components
    │   ├── utils/           # Utility functions (API client)
    │   ├── App.jsx
    │   ├── main.jsx
    │   └── index.css
    ├── index.html
    ├── package.json
    └── vite.config.js
```

## Database Schema

The application uses the following tables:

- **users** - Authentication and role information
- **student_profile** - Student-specific details
- **faculty_profile** - Faculty-specific details
- **courses** - Course information
- **student_assigned_courses** - Student enrollments
- **faculty_assigned_courses** - Faculty course assignments
- **student_attendance** - Student attendance records
- **faculty_attendance** - Faculty attendance records
- **holidays** - Holiday management
- **academic_sessions** - Academic year/semester tracking

## Features

### Admin Features
- Create and manage student accounts
- Create and manage faculty accounts
- Create and manage courses
- Assign faculty to courses
- Manage student enrollments
- View all attendance reports
- View dashboard statistics

### Student Features
- Register new account
- View available courses
- Enroll in courses
- View personal attendance
- View timetable
- Update profile

### Faculty Features
- View assigned courses
- View enrolled students
- Mark student attendance
- Mark own attendance
- View attendance reports
- View timetable
- Update profile

## Attendance Business Rules

1. **Weekend Rule**: Saturday and Sunday are automatically holidays - attendance cannot be marked
2. **Holiday Rule**: Attendance cannot be marked on declared holidays
3. **Faculty Assignment Rule**: Faculty can only mark attendance for their assigned courses
4. **Enrollment Rule**: Students can only have attendance for courses they are enrolled in
5. **Unique Record Rule**: Only one attendance record per student, course, and date combination
6. **Admin Access**: Admin can view all attendance reports

## Prerequisites

### For Backend
- Java 21 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (Eclipse/STS or IntelliJ IDEA)

### For Frontend
- Node.js 18+ 
- npm or yarn

## Setup Instructions

### 1. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE college_management_db;
```

Update database credentials in `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/college_management_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

### 2. Backend Setup

Navigate to the backend directory:

```bash
cd backend
```

Build the project with Maven:

```bash
mvn clean install
```

Run the Spring Boot application:

```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### 3. Frontend Setup

Navigate to the frontend directory:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Run the development server:

```bash
npm run dev
```

The frontend will start on `http://localhost:5173`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Courses
- `GET /api/courses` - Get all courses
- `GET /api/courses/active` - Get active courses
- `GET /api/courses/{id}` - Get course by ID
- `POST /api/courses` - Create course (Admin only)
- `PUT /api/courses/{id}` - Update course (Admin only)
- `DELETE /api/courses/{id}` - Delete course (Admin only)

### Admin
- `GET /api/admin/students` - Get all students
- `POST /api/admin/students` - Create student
- `PUT /api/admin/students/{id}` - Update student
- `PUT /api/admin/students/{id}/deactivate` - Deactivate student
- `PUT /api/admin/students/{id}/activate` - Activate student
- `GET /api/admin/faculty` - Get all faculty
- `POST /api/admin/faculty` - Create faculty
- `PUT /api/admin/faculty/{id}` - Update faculty
- `PUT /api/admin/faculty/{id}/deactivate` - Deactivate faculty
- `PUT /api/admin/faculty/{id}/activate` - Activate faculty

### Attendance
- `POST /api/attendance/mark-student` - Mark student attendance (Faculty)
- `POST /api/attendance/mark-faculty` - Mark faculty attendance (Faculty)
- `GET /api/attendance/student/{studentId}` - Get student attendance
- `GET /api/attendance/course/{courseId}` - Get course attendance
- `GET /api/attendance/date/{date}` - Get attendance by date (Admin)
- `GET /api/attendance/faculty/{facultyId}` - Get faculty attendance

### Enrollments
- `GET /api/enrollments/student/{studentId}` - Get student enrollments
- `GET /api/enrollments/course/{courseId}` - Get course enrollments
- `POST /api/enrollments/enroll` - Enroll student in course
- `DELETE /api/enrollments/{enrollmentId}` - Drop enrollment

### Faculty Assignments
- `GET /api/faculty-assignments/faculty/{facultyId}` - Get faculty assignments
- `GET /api/faculty-assignments/course/{courseId}` - Get course assignments
- `POST /api/faculty-assignments/assign` - Assign faculty to course (Admin)
- `DELETE /api/faculty-assignments/{assignmentId}` - Remove assignment (Admin)

### Dashboard
- `GET /api/dashboard/admin` - Get admin dashboard statistics

## Default Credentials

After first run, you can register users through the registration page. For initial admin setup, you may need to:

1. Register a new user with role ADMIN through the registration page
2. Or directly insert an admin user into the database

## Building for Production

### Backend
```bash
cd backend
mvn clean package
java -jar target/college-management-system-1.0.0.jar
```

### Frontend
```bash
cd frontend
npm run build
```

The build output will be in the `dist` directory.

## Security Features

- JWT-based authentication
- Role-based access control (RBAC)
- Password encryption using BCrypt
- CORS configuration
- Protected API endpoints

## Testing with Postman

You can test the API endpoints using Postman:

1. Import the API endpoints
2. First register a user via `POST /api/auth/register`
3. Login via `POST /api/auth/login` to get JWT token
4. Add the token to request headers: `Authorization: Bearer <token>`
5. Access protected endpoints

## Troubleshooting

### Backend won't start
- Check MySQL is running
- Verify database credentials in application.properties
- Check if port 8080 is available

### Frontend can't connect to backend
- Ensure backend is running on port 8080
- Check CORS configuration in SecurityConfig.java
- Verify API_BASE_URL in src/utils/api.js

### Database connection issues
- Verify MySQL service is running
- Check database name and credentials
- Ensure MySQL allows connections from localhost

## Future Enhancements

- Google OAuth 2.0 integration
- Email notifications
- File upload for profile pictures
- Advanced reporting and analytics
- Timetable management
- Grade management
- Fee management
- Library management integration

## License

This project is created for educational purposes.

## Contact

For questions or issues, please contact the development team.
