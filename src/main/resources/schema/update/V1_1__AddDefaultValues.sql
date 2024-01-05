insert into actor(id, family_name, given_name, jpa_version) values(nextval('seq_actory'), 'Hanks', 'Tom', 0);
insert into movie(id, title, jpa_version) values(nextval('seq_movie'), 'Apollo 13', 0);
insert into role(id, name, jpa_version, actor_id, movie_id) values(nextval('seq_role'), 'Jim Lovell', 0, currval('seq_actory'), currval('seq_movie'));