create index idxActorNames on actor (given_name, family_name);
create index idxMovieTitle on movie (title);
create index idxRole on role (name, movie_id, actor_id);
