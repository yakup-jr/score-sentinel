--liquibase formatted sql

--changeset yakup_jr:changelog_version-1.8.h2.sql
create table if not exists tournaments_schema.tournaments
(
    id   bigserial primary key,
    name varchar(255) not null
);

create table if not exists tournaments_schema.tournaments_teams
(
    tournament_id bigserial references tournaments_schema.tournaments (id),
    team_id       bigserial references teams_schema.teams (id),
    primary key (tournament_id, team_id)
);

create table if not exists tournaments_schema.tournaments_rounds
(
    tournament_id bigserial references tournaments_schema.tournaments (id),
    round_id      bigserial references rounds_schema.rounds (id),
    primary key (tournament_id, round_id)
);

create table if not exists tournaments_schema.tournaments_games
(
    tournament_id bigserial references tournaments_schema.tournaments (id),
    game_id       bigserial references games_schema.games (id),
    primary key (tournament_id, game_id)
);

create table if not exists tournaments_schema.tournaments_tournament_results
(
    tournament_id        bigserial references tournaments_schema.tournaments (id),
    tournament_result_id bigserial references tournaments_schema.tournament_results (id),
    primary key (tournament_id, tournament_result_id)
);