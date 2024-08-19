-- Create the database
CREATE DATABASE "notification_scheduler";

-- Connect to the database
\c "notification_scheduler";

-- Create the user
CREATE USER "scheduler_user" WITH PASSWORD 'scheduler_password';

-- Grant usage on the public schema to the user
GRANT USAGE ON SCHEMA public TO "scheduler_user";

-- Grant all privileges on all tables in the public schema to the user
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO "scheduler_user";

-- Alter default privileges to ensure future tables in the public schema get the same privileges
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TABLES TO "scheduler_user";

-- Create the table in the public schema
CREATE TABLE IF NOT EXISTS "public"."test_table" (
                                                     "id" serial PRIMARY KEY,
                                                     "name" varchar(255) NOT NULL
);

-- Grant usage and select privileges on the sequence to the user
GRANT USAGE, SELECT ON SEQUENCE public.test_table_id_seq TO "scheduler_user";

-- Insert a row into the table
INSERT INTO "public"."test_table" ("name") VALUES ('test_name');
