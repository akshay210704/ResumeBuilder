# Resume Builder Application

A JavaFX-based Resume Builder that allows users to input their details and generate a PDF resume.

## Features
- User-friendly UI with JavaFX
- Stores resume data in MySQL
- Exports resume as a PDF using iText

## Technologies Used
- Java, JavaFX
- MySQL (Database)
- iText (PDF generation)

## How to Run
1. Clone the repository
2. Set up MySQL database with the provided schema
3. Run the JavaFX application in Eclipse/IntelliJ


## Database Schema

Create a MySQL database and table using the following SQL commands:

CREATE DATABASE resume_db;

USE resume_db;

CREATE TABLE resumes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    introduction TEXT,
    education TEXT,
    skills TEXT,
    experience TEXT,
    projects TEXT
);
