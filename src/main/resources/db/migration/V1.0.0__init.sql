create table IF NOT EXISTS authority
(
    id   integer      not null auto_increment,
    role varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table IF NOT EXISTS orders
(
    id           integer not null auto_increment,
    products_id  integer,
    status       tinyint check (status between 0 and 3),
    user_id      integer,
    order_data   datetime(6),
    order_number varchar(255),
    primary key (id)
) engine = InnoDB;

create table IF NOT EXISTS product
(
    count_in_stock integer      not null,
    created_date   date,
    id             integer      not null auto_increment,
    price          integer      not null,
    updated_date   date,
    description    varchar(255) not null,
    name           varchar(255) not null,
    type           varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table IF NOT EXISTS user
(
    authority_id integer,
    id           integer      not null auto_increment,
    birthday     datetime(6)  not null,
    email        varchar(255) not null,
    name         varchar(255) not null,
    password     varchar(255) not null,
    surname      varchar(255) not null,
    primary key (id)
) engine = InnoDB;

alter table orders
    add constraint fk_order_product foreign key (products_id) references product (id);

alter table orders
    add constraint fk_order_user foreign key (user_id) references user (id);

alter table user
    add constraint fk_user_authority foreign key (authority_id) references authority (id);

alter table orders
    drop foreign key fk_order_product;

alter table orders
    drop foreign key fk_order_user;

alter table user
    drop foreign key fk_user_authority;

INSERT INTO authority (id, role)
SELECT 1,
       'ADMIN'
WHERE NOT EXISTS(
        SELECT 1 FROM authority WHERE id = 1
    );

INSERT INTO authority (id, role)
SELECT 2,
       'CUSTOMER'
WHERE NOT EXISTS(
        SELECT 1 FROM authority WHERE id = 2
    );