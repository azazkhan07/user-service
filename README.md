# user-service

User Management Microservice for the PayFlow Fintech Platform.

## Overview

The User Service is responsible for managing user accounts, profile information, KYC verification, and user-related operations within the PayFlow microservices ecosystem.

## Features

* User Registration
* Get User By ID
* Get User By Email
* Update User Details
* Delete User
* KYC Submission
* KYC Approval/Rejection
* User Status Management
* Create Wallet For User
* Pagination & Sorting
* Global Exception Handling
* OpenAPI / Swagger Documentation
* Eureka Client Integration
* OpenFeign Communication

## Tech Stack

* Java 21
* Spring Boot 3
* Spring Data JPA
* PostgreSQL
* Spring Cloud OpenFeign
* Netflix Eureka Client
* MapStruct
* Lombok
* Swagger / OpenAPI
* Maven

## API Endpoints

### User Management

| Method | Endpoint                      | Description       |
| ------ | ----------------------------- | ----------------- |
| POST   | `/api/v1/users/register`      | Register user     |
| GET    | `/api/v1/users/{id}`          | Get user by ID    |
| GET    | `/api/v1/users/email/{email}` | Get user by email |
| PUT    | `/api/v1/users/{id}`          | Update user       |
| DELETE | `/api/v1/users/{id}`          | Delete user       |

### KYC APIs

| Method | Endpoint                        | Description    |
| ------ | ------------------------------- | -------------- |
| POST   | `/api/v1/users/{id}/kyc`        | Submit KYC     |
| GET    | `/api/v1/users/{id}/kyc-status` | Get KYC status |

### User Listing

| Method | Endpoint        | Description                   |
| ------ | --------------- | ----------------------------- |
| GET    | `/api/v1/users` | Get all users with pagination |

## Service Discovery

This service registers itself with Eureka Discovery Server.

## Swagger Documentation

After starting the application:

## Project Structure

```
src/main/java
├── controller
├── service
├── repository
├── entity
├── dto
├── mapper
├── client
├── exception
├── config
└── util
```

## Author

Azaz Khan

PayFlow - Fintech Microservices Platform
