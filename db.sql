create schema labmpppb6;

create table labmpppb6.trips
(
    name        varchar(255),
    source      varchar(255),
    destination varchar(255),
    "when"      timestamp,
    spaces      integer,
    id          serial
        constraint trip_pk
            primary key
);

create table labmpppb6.users
(
    id       serial
        constraint users_pk
            primary key,
    username varchar(255)
);

create table labmpppb6.reservations
(
    id                      serial
        constraint reservations_pk
            primary key,
    person_name             varchar(255) not null,
    user_id                 integer      not null
        constraint reservations_users_id_fk
            references labmpppb6.users,
    personal_identification varchar(255) not null,
    reserved_spots          integer      not null
);

