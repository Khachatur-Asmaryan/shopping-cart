create table IF NOT EXISTS authority
(
    id   SERIAL       not null,
    role varchar(255) not null,
    primary key (id)
);

create table IF NOT EXISTS orders
(
    id           SERIAL not null,
    products_id  INT,
    status       SMALLINT check (status between 0 and 3),
    user_id      INT,
    order_data   TIMESTAMP(6),
    order_number varchar(255),
    primary key (id)
);

create table IF NOT EXISTS product
(
    count_in_stock integer      not null,
    created_date   date,
    id             SERIAL       not null,
    price          integer      not null,
    updated_date   date,
    description    varchar(255) not null,
    name           varchar(255) not null,
    type           varchar(255) not null,
    primary key (id)
);

create table IF NOT EXISTS "user"
(
    authority_id integer,
    id           SERIAL       not null,
    birthday     TIMESTAMP(6) not null,
    email        varchar(255) not null,
    name         varchar(255) not null,
    password     varchar(255) not null,
    surname      varchar(255) not null,
    primary key (id)
);

alter table orders
    add constraint fk_order_product foreign key (products_id) references product (id);

alter table orders
    add constraint fk_order_user foreign key (user_id) references "user" (id);

alter table "user"
    add constraint fk_user_authority foreign key (authority_id) references authority (id);


alter table orders
    drop constraint if exists fk_order_product;

alter table orders
    drop constraint if exists fk_order_user;

alter table "user"
    drop constraint if exists fk_user_authority;

INSERT INTO authority (id, role)
VALUES (1, 'ADMIN')
ON CONFLICT (id) DO NOTHING;

INSERT INTO authority (id, role)
VALUES (2, 'CUSTOMER')
ON CONFLICT (id) DO NOTHING;