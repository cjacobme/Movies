create sequence seq_actor start 1 increment 1;
create sequence seq_movie start 1 increment 1;
create sequence seq_role start 1 increment 1;

    create table actor (
        id int8 not null,
        family_name varchar(255) not null,
        given_name varchar(255) not null,
        jpa_version int4 not null,
        primary key (id)
    );

    create table movie (
        id int8 not null,
        title varchar(255) not null,
        jpa_version int4 not null,
        primary key (id)
    );

    create table role (
        id int8 not null,
        name varchar(255) not null,
        jpa_version int4 not null,
        actor_id int8 not null,
        movie_id int8 not null,
        primary key (id)
    );
create index idxActorNames on actor (given_name, family_name);
create index idxMovieTitle on movie (title);

    alter table movie 
        add constraint UK_o6ifx5x4vtwfrpu1a42l8u81w unique (title);
create index idxRole on role (name, movie_id, actor_id);

    alter table role 
        add constraint FKhcvuc3dt3u55owiv7hblb5lfe 
        foreign key (actor_id) 
        references actor;

    alter table role 
        add constraint FKowvo6n9ya7131krugjgxb2ua8 
        foreign key (movie_id) 
        references movie;
