# JobSpring – Recruitment Platform

**JobSpring** is a Spring Boot MVC application that connects **Recruiters** and **Seekers**.  
It provides a platform where recruiters can post jobs, manage applications, and interact with candidates, while seekers can apply for jobs and maintain their profiles.

---

## Tech Stack

- **Spring Boot** – Core framework for building the MVC application
- **Spring Security** – Authentication, authorization, and RBAC
- **Spring Web (Spring MVC)** – Controllers, services, and REST endpoints
- **Spring Data JPA & SQL** – Persistent storage of business logic
- **Redis** – Temporary storage for OTP during account activation
- **JDBC Session** – Secure session management in the database

---

## Key Feature

### Authentication & Security
- OTP-based account activation using **Redis**
- Role-Based Access Control (**RBAC**) for `RECRUITER` and `SEEKER` roles
- Secure session management using **JDBC Session**

---

### For Recruiters
- Create, edit, and delete **Job Post Activities**
- View seekers who have applied for their job postings
- Download seeker resumes directly from the dashboard

---

### For Seekers
- Build and update **profile** with key skills and resume
- Apply for available job postings
- Manage and track their applications

---