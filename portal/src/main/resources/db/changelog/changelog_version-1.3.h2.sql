--liquibase formatted sql

--changeset yakup_jr:changelog_version-1.3.h2.sql
CREATE SCHEMA IF NOT EXISTS matches_schema;

CREATE TABLE IF NOT EXISTS matches_schema.match_results (
    ID BIGSERIAL PRIMARY KEY,
    SCORE INTEGER NOT NULL,
    POINT FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS matches_schema.match_results_teams (
    MATCH_RESULT_ID BIGSERIAL REFERENCES matches_schema.match_results (ID),
    TEAM_ID BIGSERIAL REFERENCES teams_schema.teams (ID),
    PRIMARY KEY (MATCH_RESULT_ID, TEAM_ID)
);
