--liquibase formatted sql

--changeset yakup_jr:changelog_version-1.2.h2.sql
CREATE SCHEMA IF NOT EXISTS teams_schema;

CREATE TABLE IF NOT EXISTS teams_schema.teams
(
    ID   BIGSERIAL PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL UNIQUE
);

CREATE SCHEMA IF NOT EXISTS teams_users_schema;

CREATE TABLE IF NOT EXISTS teams_users_schema.teams_users
(
    USER_ID BIGINT REFERENCES users_schema.users (ID),
    TEAM_ID BIGINT REFERENCES teams_schema.teams (ID),
    PRIMARY KEY (USER_ID, TEAM_ID)
);

CREATE SCHEMA IF NOT EXISTS teams_games_schema;

CREATE TABLE IF NOT EXISTS teams_games_schema.teams_games (
    GAME_ID BIGINT REFERENCES games_schema.games (ID),
    TEAM_ID BIGINT REFERENCES teams_schema.teams (ID),
    PRIMARY KEY (GAME_ID, TEAM_ID)
)
