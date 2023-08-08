--liquibase formatted sql

--changeset yakup_jr:changelog_version-1.4.h2.sql
CREATE TABLE IF NOT EXISTS matches_schema.matches
(
    ID    BIGSERIAL PRIMARY KEY,
    NAME  VARCHAR(255) NOT NULL UNIQUE,
    SCORE VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS matches_schema.matches_teams
(
    MATCH_ID BIGSERIAL REFERENCES matches_schema.matches (ID),
    TEAM_ID  BIGSERIAL REFERENCES teams_schema.teams (ID),
    PRIMARY KEY (MATCH_ID, TEAM_ID)
);

CREATE TABLE IF NOT EXISTS matches_schema.matches_games
(
    MATCH_ID BIGSERIAL REFERENCES matches_schema.matches (ID),
    GAME_ID  BIGSERIAL REFERENCES games_schema.games (ID),
    PRIMARY KEY (MATCH_ID, GAME_ID)
);

CREATE TABLE IF NOT EXISTS matches_schema.matches_match_results
(
    MATCH_ID        BIGSERIAL REFERENCES matches_schema.matches (ID),
    MATCH_RESULT_ID BIGSERIAL REFERENCES matches_schema.match_results (ID),
        PRIMARY KEY (MATCH_ID, MATCH_RESULT_ID)
);