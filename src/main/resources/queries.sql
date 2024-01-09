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

-- Kevin Bacon number 3
select distinct a3 from actor a3
join "role" r3a on a3.id = r3a.actor_id
join "movie" m3 on m3.id = r3a.movie_id
join "role" r3m on r3m.movie_id = m3.id
join "actor" a2 on r3m.actor_id = a2.id
join "role" r2 on a2.id = r2.actor_id
join "movie" m2 on r2.movie_id  = m2.id
join "role" r1 on r1.movie_id = m2.id
join "actor" a1 on r1.actor_id  = a1.id
join "role" r0 on r0.actor_id = a1.id
join "movie" m0 on r0.movie_id = m0.id
join "role" rk on rk.movie_id = m0.id
join "actor" ak on ak.id = rk.id
where ak.given_name = 'Kevin' and ak.family_name = 'Bacon'
and a3.id != ak.id
and a3.id not in (select a2.id from actor a2
                  join "role" r2 on a2.id = r2.actor_id
                  join "movie" m2 on r2.movie_id  = m2.id
                  join "role" r1 on r1.movie_id = m2.id
                  join "actor" a1 on r1.actor_id  = a1.id
                  join "role" r0 on r0.actor_id = a1.id
                  join "movie" m0 on r0.movie_id = m0.id
                  join "role" rk on rk.movie_id = m0.id
                  join "actor" ak on ak.id = rk.id
                  where ak.given_name = 'Kevin' and ak.family_name = 'Bacon')
and a3.id not in (select a1.id from actor a1
                  join "role" r1 on a1.id = r1.actor_id
                  join "movie" m1 on r1.movie_id  = m1.id
                  join "role" r0 on r0.movie_id = m1.id
                  join "actor" a0 on r0.actor_id  = a0.id
                  where a0.given_name = 'Kevin' and a0.family_name = 'Bacon'
                  and a1.given_name != 'Kevin')

-- Kevin Bacon number 4
select distinct a4 from actor a4
join "role" r4a on a4.id = r4a.actor_id
join "movie" m4 on m4.id = r4a.movie_id
join "role" r4m on r4m.movie_id = m4.id
join "actor" a3 on a3.id = r4m.actor_id
join "role" r3a on a3.id = r3a.actor_id
join "movie" m3 on m3.id = r3a.movie_id
join "role" r3m on r3m.movie_id = m3.id
join "actor" a2 on r3m.actor_id = a2.id
join "role" r2 on a2.id = r2.actor_id
join "movie" m2 on r2.movie_id  = m2.id
join "role" r1 on r1.movie_id = m2.id
join "actor" a1 on r1.actor_id  = a1.id
join "role" r0 on r0.actor_id = a1.id
join "movie" m0 on r0.movie_id = m0.id
join "role" rk on rk.movie_id = m0.id
join "actor" ak on ak.id = rk.id
where ak.given_name = 'Kevin' and ak.family_name = 'Bacon'
and a4.id != ak.id
and a4.id not in (select a3.id from actor a3
                  join "role" r3a on a3.id = r3a.actor_id
                  join "movie" m3 on m3.id = r3a.movie_id
                  join "role" r3m on r3m.movie_id = m3.id
                  join "actor" a2 on r3m.actor_id = a2.id
                  join "role" r2 on a2.id = r2.actor_id
                  join "movie" m2 on r2.movie_id  = m2.id
                  join "role" r1 on r1.movie_id = m2.id
                  join "actor" a1 on r1.actor_id  = a1.id
                  join "role" r0 on r0.actor_id = a1.id
                  join "movie" m0 on r0.movie_id = m0.id
                  join "role" rk on rk.movie_id = m0.id
                  join "actor" ak on ak.id = rk.id
                  where ak.given_name = 'Kevin' and ak.family_name = 'Bacon')
and a4.id not in (select a2.id from actor a2
                  join "role" r2 on a2.id = r2.actor_id
                  join "movie" m2 on r2.movie_id  = m2.id
                  join "role" r1 on r1.movie_id = m2.id
                  join "actor" a1 on r1.actor_id  = a1.id
                  join "role" r0 on r0.actor_id = a1.id
                  join "movie" m0 on r0.movie_id = m0.id
                  join "role" rk on rk.movie_id = m0.id
                  join "actor" ak on ak.id = rk.id
                  where ak.given_name = 'Kevin' and ak.family_name = 'Bacon')
and a4.id not in (select a1.id from actor a1
                  join "role" r1 on a1.id = r1.actor_id
                  join "movie" m1 on r1.movie_id  = m1.id
                  join "role" r0 on r0.movie_id = m1.id
                  join "actor" a0 on r0.actor_id  = a0.id
                  where a0.given_name = 'Kevin' and a0.family_name = 'Bacon'
                  and a1.given_name != 'Kevin')

-- Kevin Bacon number 5
select distinct a5 from actor a5
join "role" r5a on a5.id = r5a.actor_id
join "movie" m5 on m5.id = r5a.movie_id
join "role" r5m on r5m.movie_id = m5.id
join "actor" a4 on a4.id = r5m.actor_id
join "role" r4a on a4.id = r4a.actor_id
join "movie" m4 on m4.id = r4a.movie_id
join "role" r4m on r4m.movie_id = m4.id
join "actor" a3 on a3.id = r4m.actor_id
join "role" r3a on a3.id = r3a.actor_id
join "movie" m3 on m3.id = r3a.movie_id
join "role" r3m on r3m.movie_id = m3.id
join "actor" a2 on r3m.actor_id = a2.id
join "role" r2 on a2.id = r2.actor_id
join "movie" m2 on r2.movie_id  = m2.id
join "role" r1 on r1.movie_id = m2.id
join "actor" a1 on r1.actor_id  = a1.id
join "role" r0 on r0.actor_id = a1.id
join "movie" m0 on r0.movie_id = m0.id
join "role" rk on rk.movie_id = m0.id
join "actor" ak on ak.id = rk.id
where ak.given_name = 'Kevin' and ak.family_name = 'Bacon'
and a5.id != ak.id
and a5.id not in (select distinct a4.id from actor a4
                  join "role" r4a on a4.id = r4a.actor_id
                  join "movie" m4 on m4.id = r4a.movie_id
                  join "role" r4m on r4m.movie_id = m4.id
                  join "actor" a3 on a3.id = r4m.actor_id
                  join "role" r3a on a3.id = r3a.actor_id
                  join "movie" m3 on m3.id = r3a.movie_id
                  join "role" r3m on r3m.movie_id = m3.id
                  join "actor" a2 on r3m.actor_id = a2.id
                  join "role" r2 on a2.id = r2.actor_id
                  join "movie" m2 on r2.movie_id  = m2.id
                  join "role" r1 on r1.movie_id = m2.id
                  join "actor" a1 on r1.actor_id  = a1.id
                  join "role" r0 on r0.actor_id = a1.id
                  join "movie" m0 on r0.movie_id = m0.id
                  join "role" rk on rk.movie_id = m0.id
                  join "actor" ak on ak.id = rk.id
                  where ak.given_name = 'Kevin' and ak.family_name = 'Bacon')
and a5.id not in (select a3.id from actor a3
                  join "role" r3a on a3.id = r3a.actor_id
                  join "movie" m3 on m3.id = r3a.movie_id
                  join "role" r3m on r3m.movie_id = m3.id
                  join "actor" a2 on r3m.actor_id = a2.id
                  join "role" r2 on a2.id = r2.actor_id
                  join "movie" m2 on r2.movie_id  = m2.id
                  join "role" r1 on r1.movie_id = m2.id
                  join "actor" a1 on r1.actor_id  = a1.id
                  join "role" r0 on r0.actor_id = a1.id
                  join "movie" m0 on r0.movie_id = m0.id
                  join "role" rk on rk.movie_id = m0.id
                  join "actor" ak on ak.id = rk.id
                  where ak.given_name = 'Kevin' and ak.family_name = 'Bacon')
and a5.id not in (select a2.id from actor a2
                  join "role" r2 on a2.id = r2.actor_id
                  join "movie" m2 on r2.movie_id  = m2.id
                  join "role" r1 on r1.movie_id = m2.id
                  join "actor" a1 on r1.actor_id  = a1.id
                  join "role" r0 on r0.actor_id = a1.id
                  join "movie" m0 on r0.movie_id = m0.id
                  join "role" rk on rk.movie_id = m0.id
                  join "actor" ak on ak.id = rk.id
                  where ak.given_name = 'Kevin' and ak.family_name = 'Bacon')
and a5.id not in (select a1.id from actor a1
                  join "role" r1 on a1.id = r1.actor_id
                  join "movie" m1 on r1.movie_id  = m1.id
                  join "role" r0 on r0.movie_id = m1.id
                  join "actor" a0 on r0.actor_id  = a0.id
                  where a0.given_name = 'Kevin' and a0.family_name = 'Bacon'
                  and a1.given_name != 'Kevin')


-- Kevin Bacon number 6
select distinct a6 from actor a6
join "role" r6a on a6.id = r6a.actor_id
join "movie" m6 on m6.id = r6a.movie_id
join "role" r6m on r6m.movie_id = m6.id
join "actor" a5 on a5.id = r6m.actor_id
join "role" r5a on a5.id = r5a.actor_id
join "movie" m5 on m5.id = r5a.movie_id
join "role" r5m on r5m.movie_id = m5.id
join "actor" a4 on a4.id = r5m.actor_id
join "role" r4a on a4.id = r4a.actor_id
join "movie" m4 on m4.id = r4a.movie_id
join "role" r4m on r4m.movie_id = m4.id
join "actor" a3 on a3.id = r4m.actor_id
join "role" r3a on a3.id = r3a.actor_id
join "movie" m3 on m3.id = r3a.movie_id
join "role" r3m on r3m.movie_id = m3.id
join "actor" a2 on r3m.actor_id = a2.id
join "role" r2 on a2.id = r2.actor_id
join "movie" m2 on r2.movie_id  = m2.id
join "role" r1 on r1.movie_id = m2.id
join "actor" a1 on r1.actor_id  = a1.id
join "role" r0 on r0.actor_id = a1.id
join "movie" m0 on r0.movie_id = m0.id
join "role" rk on rk.movie_id = m0.id
join "actor" ak on ak.id = rk.id
where ak.given_name = 'Kevin' and ak.family_name = 'Bacon'
and a6.id != ak.id
and a6.id not in (select distinct a5.id from actor a5
                  join "role" r5a on a5.id = r5a.actor_id
                  join "movie" m5 on m5.id = r5a.movie_id
                  join "role" r5m on r5m.movie_id = m5.id
                  join "actor" a4 on a4.id = r5m.actor_id
                  join "role" r4a on a4.id = r4a.actor_id
                  join "movie" m4 on m4.id = r4a.movie_id
                  join "role" r4m on r4m.movie_id = m4.id
                  join "actor" a3 on a3.id = r4m.actor_id
                  join "role" r3a on a3.id = r3a.actor_id
                  join "movie" m3 on m3.id = r3a.movie_id
                  join "role" r3m on r3m.movie_id = m3.id
                  join "actor" a2 on r3m.actor_id = a2.id
                  join "role" r2 on a2.id = r2.actor_id
                  join "movie" m2 on r2.movie_id  = m2.id
                  join "role" r1 on r1.movie_id = m2.id
                  join "actor" a1 on r1.actor_id  = a1.id
                  join "role" r0 on r0.actor_id = a1.id
                  join "movie" m0 on r0.movie_id = m0.id
                  join "role" rk on rk.movie_id = m0.id
                  join "actor" ak on ak.id = rk.id
                  where ak.given_name = 'Kevin' and ak.family_name = 'Bacon')
and a6.id not in (select distinct a4.id from actor a4
                  join "role" r4a on a4.id = r4a.actor_id
                  join "movie" m4 on m4.id = r4a.movie_id
                  join "role" r4m on r4m.movie_id = m4.id
                  join "actor" a3 on a3.id = r4m.actor_id
                  join "role" r3a on a3.id = r3a.actor_id
                  join "movie" m3 on m3.id = r3a.movie_id
                  join "role" r3m on r3m.movie_id = m3.id
                  join "actor" a2 on r3m.actor_id = a2.id
                  join "role" r2 on a2.id = r2.actor_id
                  join "movie" m2 on r2.movie_id  = m2.id
                  join "role" r1 on r1.movie_id = m2.id
                  join "actor" a1 on r1.actor_id  = a1.id
                  join "role" r0 on r0.actor_id = a1.id
                  join "movie" m0 on r0.movie_id = m0.id
                  join "role" rk on rk.movie_id = m0.id
                  join "actor" ak on ak.id = rk.id
                  where ak.given_name = 'Kevin' and ak.family_name = 'Bacon')
and a6.id not in (select a3.id from actor a3
                  join "role" r3a on a3.id = r3a.actor_id
                  join "movie" m3 on m3.id = r3a.movie_id
                  join "role" r3m on r3m.movie_id = m3.id
                  join "actor" a2 on r3m.actor_id = a2.id
                  join "role" r2 on a2.id = r2.actor_id
                  join "movie" m2 on r2.movie_id  = m2.id
                  join "role" r1 on r1.movie_id = m2.id
                  join "actor" a1 on r1.actor_id  = a1.id
                  join "role" r0 on r0.actor_id = a1.id
                  join "movie" m0 on r0.movie_id = m0.id
                  join "role" rk on rk.movie_id = m0.id
                  join "actor" ak on ak.id = rk.id
                  where ak.given_name = 'Kevin' and ak.family_name = 'Bacon')
and a6.id not in (select a2.id from actor a2
                  join "role" r2 on a2.id = r2.actor_id
                  join "movie" m2 on r2.movie_id  = m2.id
                  join "role" r1 on r1.movie_id = m2.id
                  join "actor" a1 on r1.actor_id  = a1.id
                  join "role" r0 on r0.actor_id = a1.id
                  join "movie" m0 on r0.movie_id = m0.id
                  join "role" rk on rk.movie_id = m0.id
                  join "actor" ak on ak.id = rk.id
                  where ak.given_name = 'Kevin' and ak.family_name = 'Bacon')
and a6.id not in (select a1.id from actor a1
                  join "role" r1 on a1.id = r1.actor_id
                  join "movie" m1 on r1.movie_id  = m1.id
                  join "role" r0 on r0.movie_id = m1.id
                  join "actor" a0 on r0.actor_id  = a0.id
                  where a0.given_name = 'Kevin' and a0.family_name = 'Bacon'
                  and a1.given_name != 'Kevin')

