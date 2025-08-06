# trackwise-docker-compose-spring-boot-mysql-ec2-github-actions-s3
Full-stack deployment of a Spring Boot application using Docker Compose and MySQL on AWS EC2, with CI/CD via GitHub Actions and mandatory Docker image delivery through Amazon S3.

===============================================================================================================================

# 🚀 Spring Boot + Docker Compose + MySQL Deployment on AWS EC2 with S3 Integration

This project demonstrates a complete deployment pipeline for a Spring Boot application using Docker Compose and MySQL on AWS EC2. It includes CI/CD integration via GitHub Actions and **mandatory Docker image delivery via Amazon S3**.

---

## 📦 Tech Stack

- **Spring Boot** (Java 17)
- **MySQL** (Dockerized)
- **Docker & Docker Compose**
- **GitHub Actions** (CI/CD)
- **AWS EC2** (Ubuntu/Amazon Linux)
- **Amazon S3** (for Docker image delivery)

---

## 🧑‍💻 Local Development

1. Clone the repository:
   ```bash
   git clone https://github.com/<your-username>/springboot-ec2-docker-mysql.git
   cd springboot-ec2-docker-mysql

## Build the Spring Boot app
mvn clean package

## Run locally with docker compose
docker-compose up --build

## testing pipeline
