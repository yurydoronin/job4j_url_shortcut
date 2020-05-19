create table if not exists sites
(
    id       serial primary key not null,
    login    varchar(200) not null,
    password varchar(200) not null
);

create table if not exists links
(
    id      serial primary key               not null,
    site_id int references sites (id)        not null,
    url     varchar(200)                     not null,
    code    varchar(200)                     not null,
    count   int                              not null default 0
);