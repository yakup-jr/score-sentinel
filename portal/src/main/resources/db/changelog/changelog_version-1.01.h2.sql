--liquibase formatted sql

--changeset yakup_jr:changelog_version-1.01.h2.sql
ALTER TABLE games_schema.games ALTER COLUMN name SET NOT NULL;
