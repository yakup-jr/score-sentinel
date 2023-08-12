--liquibase formatted sql

--changeset yakup_jr:changelog_version-1.5.h2.sql
create schema if not exists rounds_schema;

create table if not exists rounds_schema.round_results
(
    id     bigserial primary key,
    score  integer,
    points float
);

create table if not exists rounds_schema.round_results_teams
(
    round_result_id bigserial references rounds_schema.round_results (id),
    team_id         bigserial references teams_schema.teams (id),
    primary key (round_result_id, team_id)
);
