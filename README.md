# WellNest - Personalized Health & Fitness Planner

Developed by: **Manideep Bogireddy**

---

## 📌 Project Overview
**WellNest** is a comprehensive full-stack application designed to manage personal health and fitness goals. It empowers users to track physical progress, generate personalized diet and workout plans, log daily activities, and engage with an active community of health enthusiasts.

---

## 🛠️ Tech Stack

### **Backend**
- **Framework:** Java 17, Spring Boot 3.2.2
- **Security:** Spring Security, JWT (JSON Web Tokens), Google OAuth2
- **Database Access:** Spring Data JPA / Hibernate
- **Services:** JavaMailSender for OTP Verification

### **Frontend**
- **Library/Framework:** React 18, Vite
- **HTTP Client:** Axios
- **Styling & UI:** Tailwind CSS, Lucide React Icons

### **Database**
- **Database Engine:** MySQL Database

---

## 🔥 Key Features

1. **🔐 Multi-Factor & Social Authentication**
   - User Registration & Login with JWT Tokens
   - Social Login via Google OAuth2
   - OTP Verification via Email for Secure Signup

2. **📊 Health Analytics & Dashboard**
   - Real-time BMI calculation and metric logging
   - Daily logs for tracking calories, water intake, workouts, and sleep

3. **🏋️ Workout & Nutrition Planner**
   - Customized workout routines and diet plans
   - Daily meal logging and progress visualization

4. **💬 Community & Social Platform**
   - Interactive Blog system with rich content
   - Post likes, comments, and user follow system

5. **🛡️ Moderation & Role Management**
   - Role-based access control (User, Trainer, Admin)
   - Automated role initialization and moderation capabilities

---

## 🚀 Live Application & Repository

- **GitHub Repository:** [https://github.com/ManideepBogireddy/WellNest](https://github.com/ManideepBogireddy/WellNest)

---

## ⚙️ Local Installation & Setup

### **Prerequisites**
- Java 17 SDK installed
- Node.js (v18+) and npm
- MySQL Server installed and running locally

---

### **1. Backend Setup**
1. Navigate to the backend directory:
   ```bash
   cd healthmate-backend
   ```
2. Configure your MySQL database credentials in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/wellnest?createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   ```
3. Build and run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
   The backend server will run on `http://localhost:8080`.

---

### **2. Frontend Setup**
1. Navigate to the frontend directory:
   ```bash
   cd healthmate-frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the Vite development server:
   ```bash
   npm run dev
   ```
   The frontend application will run on `http://localhost:5173`.

---

## 📜 License
This project is licensed under the MIT License.
