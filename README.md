# Projet_Microservice

## 🚀 Présentation

**Projet_Microservice** est une application basée sur une architecture **microservices** utilisant **Spring Boot**, **Spring Cloud**, **Eureka**, **Config Server** et potentiellement **Keycloak** pour la sécurité. Le projet vise à démontrer la modularité, la scalabilité et la maintenabilité d’un système distribué.


---

## ⚙️ Technologies utilisées

| Composant               | Technologie         |
|------------------------|---------------------|
| Backend Services       | Spring Boot 3       |
| Configuration          | Spring Cloud Config |
| Découverte de services | Eureka              |
| Sécurité               | Keycloak / Spring Security |
| Gateway API            | Spring Cloud Gateway |
| Base de données        | MySQL  / H2            |
| Conteneurisation       | Docker, Docker Compose |


---

## 📁 Structure du projet


---

## 🛠️ Étapes pour démarrer le projet

### ✅ Prérequis

Assure-toi d’avoir les outils suivants installés sur ta machine :

- [Java 17+](https://adoptium.net/)
- [Maven 3+](https://maven.apache.org/)
- [Docker & Docker Compose](https://www.docker.com/products/docker-desktop/)
- [Git](https://git-scm.com/)


---

### 🧪 1. Cloner le projet

```bash
git clone https://github.com/TuniCoder/Projet_Microservice.git
cd Projet_Microservice
docker-compose up --build
