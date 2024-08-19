-- Create the database
CREATE DATABASE "reminder_manager";

-- Connect to the database
\c "reminder_manager";

-- Create the user
CREATE USER "manager_user" WITH PASSWORD 'manager_password';

-- Grant usage on the public schema to the user
GRANT USAGE ON SCHEMA public TO "manager_user";

-- Grant all privileges on all tables in the public schema to the user
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO "manager_user";

-- Alter default privileges to ensure future tables in the public schema get the same privileges
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TABLES TO "manager_user";

-- Create the table in the public schema
CREATE TABLE IF NOT EXISTS "public"."test_table" (
                                                     "id" serial PRIMARY KEY,
                                                     "name" varchar(255) NOT NULL
);

-- Grant usage and select privileges on the sequence to the user
GRANT USAGE, SELECT ON SEQUENCE public.test_table_id_seq TO "manager_user";

-- Insert a row into the table
INSERT INTO "public"."test_table" ("name") VALUES ('test_name');

CREATE TABLE reminders (
                                 id VARCHAR NOT NULL PRIMARY KEY,
                                 event_date DATE NOT NULL,
                                 event_time TIMESTAMPTZ,
                                 user_time_zone VARCHAR,
                                 target_user_email VARCHAR NOT NULL,
                                 repeatable BOOLEAN,
                                 frequency VARCHAR(10) CHECK (frequency IN ('DAY', 'WEEK', 'MONTH', 'YEAR')),
                                 interval INTEGER,
                                 created_on TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_user_email ON reminders (target_user_email);