--liquibase formatted sql

--changeset yakup_jr:changelog_version-1.01.h2.sql
CREATE SCHEMA IF NOT EXISTS games_schema;
CREATE TABLE IF NOT EXISTS games_schema.games (
                       ID BIGSERIAL PRIMARY KEY,
                       NAME VARCHAR(255)
)