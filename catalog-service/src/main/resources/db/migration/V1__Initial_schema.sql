create table book (
id BIGSERIAL primary key not null,
created_date bigint not null,
last_modified_date bigint not null,
version integer not null,
author varchar(255) not null,
isbn varchar(255) not null,
price decimal(15,4) not null,
publishing_year integer,
title varchar(255) not null
);