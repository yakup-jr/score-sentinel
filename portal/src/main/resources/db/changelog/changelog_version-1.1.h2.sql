--liquibase formatted sql

--changeset yakup_jr:changelog_version-1.1.h2.sql
CREATE SCHEMA IF NOT EXISTS roles_schema;

--TODO: create type only if it doesn't exist
DROP TYPE IF EXISTS roles_schema.roles_enum;

CREATE TYPE roles_schema.roles_enum AS ENUM ('Admin', 'User');

CREATE TABLE IF NOT EXISTS roles_schema.roles
(
    ID   BIGSERIAL PRIMARY KEY,
    NAME roles_schema.roles_enum NOT NULL UNIQUE
);

CREATE SCHEMA IF NOT EXISTS users_schema;

CREATE TABLE IF NOT EXISTS users_schema.users
(
    ID          BIGSERIAL PRIMARY KEY,
    USERNAME    VARCHAR(64) UNIQUE  NOT NULL,
    EMAIL       VARCHAR(255) UNIQUE NOT NULL,
    PASSWORD    VARCHAR(255)        NOT NULL,
    CREATED_AT  TIMESTAMP           NOT NULL,
    LAST_ONLINE TIMESTAMP           NOT NULL,
    IS_ACTIVE   BOOLEAN             NOT NULL
);

CREATE SCHEMA IF NOT EXISTS users_roles_schema;

CREATE TABLE IF NOT EXISTS users_roles_schema.user_roles
(
    USER_ID BIGINT REFERENCES users_schema.users (ID),
    ROLE_ID BIGINT REFERENCES roles_schema.roles (ID),
    PRIMARY KEY (USER_ID, ROLE_ID)
);

CREATE SCHEMA IF NOT EXISTS users_games_schema;

CREATE TABLE IF NOT EXISTS users_games_schema.users_games
(
    USER_ID BIGINT REFERENCES users_schema.users (ID),
    GAME_ID BIGINT REFERENCES games_schema.games (ID),
    PRIMARY KEY (USER_ID, GAME_ID)
);