-- Kevin-Bacon-Number 1:
select distinct a1 from actor a1
join "role" r1 on a1.id = r1.actor_id
join "movie" m1 on r1.movie_id  = m1.id
join "role" r0 on r0.movie_id = m1.id
join "actor" a0 on r0.actor_id  = a0.id
where a0.given_name = 'Kevin' and a0.family_name = 'Bacon'
and a1.given_name != 'Kevin'

-- Kevin-Bacon-Number 2:
select distinct a2 from actor a2
join "role" r2 on a2.id = r2.actor_id
join "movie" m2 on r2.movie_id  = m2.id
join "role" r1 on r1.movie_id = m2.id
join "actor" a1 on r1.actor_id  = a1.id
join "role" r0 on r0.actor_id = a1.id
join "movie" m0 on r0.movie_id = m0.id
join "role" rk on rk.movie_id = m0.id
join "actor" ak on ak.id = rk.id
where ak.given_name = 'Kevin' and ak.family_name = 'Bacon'
and a2.id != ak.id
and a2.id not in (
select a1.id from actor a1
join "role" r1 on a1.id = r1.actor_id
join "movie" m1 on r1.movie_id  = m1.id
join "role" r0 on r0.movie_id = m1.id
join "actor" a0 on r0.actor_id  = a0.id
where a0.given_name = 'Kevin' and a0.family_name = 'Bacon'
and a1.given_name != 'Kevin')