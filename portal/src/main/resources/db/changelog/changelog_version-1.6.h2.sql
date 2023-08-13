--liquibase formatted sql

--changeset yakup_jr:changelog_version-1.6.h2.sql
create table if not exists rounds_schema.rounds
(
    id   bigserial primary key,
    name varchar(255) not null
);

create table if not exists rounds_schema.rounds_matches
(
    round_id bigserial references rounds_schema.rounds (id),
    match_id bigserial references matches_schema.matches (id),
    primary key (round_id, match_id)
);

create table if not exists rounds_schema.rounds_teams
(
    round_id bigserial references rounds_schema.rounds (id),
    team_id  bigserial references teams_schema.teams (id),
    primary key (round_id, team_id)
);

create table if not exists rounds_schema.rounds_games
(
    round_id bigserial references rounds_schema.rounds (id),
    game_id  bigserial references games_schema.games (id),
    primary key (round_id, game_id)
);

create table if not exists rounds_schema.rounds_round_results
(
    round_id        bigserial references rounds_schema.rounds (id),
    round_result_id bigserial references rounds_schema.round_results (id),
    primary key (round_id, round_result_id)
);
