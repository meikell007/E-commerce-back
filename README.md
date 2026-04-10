# E-commerce-back
E-commerce-Back, el backend del E-commerce
# 🛒 Mi Mercado Global — E-Commerce Platform

> Proyecto académico — Bases de Datos No Relacionales  
> **Grupo #1** · Universidad del Magdalena

### 👥 Integrantes

| Nombre |
|--------|
| Joseph Ferrer |
| Gabriel Gomez |
| Alvaro Leiva |
| Meikell Montoya |
| Juan Sebastian Sarmiento |

---

## 📋 Descripción

Este repositorio contiene el código fuente del proyecto de E-Commerce, una plataforma que permite a los usuarios explorar catálogos de productos, gestionar su perfil, realizar pedidos y consultar su historial de compras.

---

## 🎯 Criterios de Selección Tecnológica

| Criterio | Descripción |
|----------|-------------|
| ⚡ **Escalabilidad** | Capacidad de crecer en usuarios y transacciones sin rediseño mayor |
| 🚀 **Velocidad de desarrollo** | Uso de frameworks maduros con amplio soporte de la comunidad |
| 💰 **Costo operativo** | Aprovechamiento de servicios gestionados en AWS para reducir overhead de infraestructura |

---

## 🏗️ Arquitectura

La plataforma sigue un patrón arquitectónico por capas:

- **Frontend SPA** — Desarrollada en React, consume la API REST expuesta por el backend.
- **Backend REST API** — Implementado en Java con Spring Boot, expone endpoints seguros con JWT y se comunica con DynamoDB.
- **Capa de datos y servicios cloud** — Amazon DynamoDB como base de datos NoSQL, Amazon S3 para imágenes y Amazon SES para notificaciones por correo.

### Tabla de Capas

| Capa | Tecnología | Servicio AWS | Responsabilidad |
|------|-----------|--------------|-----------------|
| **Presentación** | React + JavaScript | S3 + CloudFront | Páginas: Home, Catálogo, Detalle producto, Carrito, Checkout, Perfil, Pedidos |
| **API Gateway** | AWS API Gateway | Punto de entrada HTTP único | Ruteo, throttling, CORS, autorización JWT |
| **Aplicación** | Spring Boot 3 (Java 17+) | EC2 / ECS Fargate | Módulos: Usuarios, Productos, Pedidos, Pagos |
| **Persistencia** | Amazon DynamoDB | NoSQL gestionado por AWS | Tablas: Users, Products, Orders, OrderItems |
| **Almacenamiento** | Amazon S3 | Imágenes y assets estáticos | Bucket público con presigned URLs para uploads |
| **Correos** | Amazon SES | Envío de correos transaccionales | Confirmación de pedido, reset de contraseña |
| **Autenticación** | JWT (jjwt) + Spring Security | Gestión de sesión stateless | Tokens de acceso y refresh token |
| **CI/CD** | AWS CodePipeline / GitHub Actions | Despliegue automatizado | Build, test, deploy a EC2 o ECS Fargate |

---

## 🧰 Stack Tecnológico

### Backend

![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![DynamoDB](https://img.shields.io/badge/Amazon_DynamoDB-4053D6?style=for-the-badge&logo=amazondynamodb&logoColor=white)
![AWS](https://img.shields.io/badge/AWS_SDK_v2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)

### Frontend

![React](https://img.shields.io/badge/React_18-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Vite](https://img.shields.io/badge/Vite_5-646CFF?style=for-the-badge&logo=vite&logoColor=white)
![TailwindCSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)



## 📄 Licencia

Proyecto académico — Universidad del Magdalena · 2026
