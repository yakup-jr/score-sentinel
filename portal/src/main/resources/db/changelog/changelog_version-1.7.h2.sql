--liquibase formatted sql

--changeset yakup_jr:changelog_version-1.7.h2.sql
create schema if not exists tournaments_schema;

create table if not exists tournaments_schema.tournament_results
(
    id     bigserial primary key,
    score  integer,
    points float
);

create table if not exists tournaments_schema.tournament_results_teams
(
    tournament_result_id bigserial references tournaments_schema.tournament_results (id),
    team_id              bigserial references teams_schema.teams (id),
    primary key (tournament_result_id, team_id)
);

