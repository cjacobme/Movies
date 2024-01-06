-- Kevin-Bacon-Number 1:
select * from actor a1
join "role" r1 on a1.id = r1.actor_id
join "movie" m1 on r1.movie_id  = m1.id
join "role" r0 on r0.movie_id = m1.id
join "actor" a0 on r0.actor_id  = a0.id
where a0.given_name = 'Kevin' and a0.family_name = 'Bacon'
and a1.given_name != 'Kevin'