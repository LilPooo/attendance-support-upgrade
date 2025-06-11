# Attendance Support - Backend
The attendance support allows lecturers to track schedules, create attendance
sessions, monitor statistics, and authenticate students through location and device
ID. I developed the messaging feature, enabling users to communicate in groups or
1-to-1. I"m using Flutter for app - a crossplatform can run on both Android and iOS.
# Technologies Used
Backend: Java Spring Boot, JPA, Spring Security (Authentication and Authorization via JWT)\
Real-Time Communication: WebSocket (Enables real-time, bidirectional communication between two users)

Database: MySQL
# Prerequisites
A MySQL server
Java SDK 21
# Start application
mvn spring-boot:run
# Step Instructions
1. Clone Repository
  `git clone https://github.com/LilPooo/attendance-support-upgrade.git`
   `cd attendance-support-upgrade`
   
2. Configure MySQL Database.\
   You need to create a database in MySQL to store the application data. Follow these steps:
   Step 1: Create a MySQL Database:\
   `CREATE DATABASE attendance_support_upgrade;`\
   Step 2: Configure Database Connection:\
   In the `src/main/resources/application.properties` file, set up the connection to your MySQL database:\
   `spring.datasource.url=jdbc:mysql://localhost:3306/attendance_support`\
   `spring.datasource.username=root  # Your MySQL username`\
   `spring.datasource.password=password  # Your MySQL password`
3. Build and Run The Project\
   `mvn spring-boot:run`
# Using Swagger to Test the API
Swagger UI is integrated into the project to help you interact with and test the API endpoints easily.\
1. Access Swagger UI
   After starting the backend application, open your browser and navigate to:\
   http://localhost:8080/identity/swagger-ui/index.html
   
2. Test API Endpoint
   Login admin:
   http://localhost:8080/identity/auth/token
   with "username": "admin", and "password" is also "admin". You will get a token, then you can use this token for other request.

# Notes
Ensure that both MySQL and Redis are running on your local system before starting the application.\
You may want to use a .env file or an external configuration management system for production environments instead of hardcoding sensitive information (like database credentials).


   





