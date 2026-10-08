-- =============================================================
--  Experiment 5 - Database setup for JSP Login (MySQL 8.x)
--  Run with:  mysql -u root -p < db_setup.sql
-- =============================================================

CREATE DATABASE IF NOT EXISTS labdb;
USE labdb;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(50)  NOT NULL UNIQUE,
    password_hash CHAR(64)     NOT NULL,          -- SHA-256 hex digest
    full_name     VARCHAR(100) NOT NULL,
    created_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- Passwords are stored as SHA-256 hashes, never as plain text.
-- SHA2(text, 256) produces the same hex string as LoginServlet.sha256().
INSERT INTO users (username, password_hash, full_name) VALUES
    ('admin',   SHA2('admin123',   256), 'Administrator'),
    ('student', SHA2('student123', 256), 'Test Student'),
    ('ravi',    SHA2('ravi@2024',  256), 'Ravi Kumar');

SELECT id, username, full_name, created_at FROM users;
