create table doctor
(
    id                     serial,
    first_name             varchar(20)                                  not null,
    last_name              varchar(20)                                  not null,
    middle_name            varchar(20)                                  not null,
    phone_number           int                                          not null
        CHECK ( phone_number BETWEEN 1000000 AND 9999999),
    date_of_birth          date                                         not null,
    price_per_consultation DECIMAL CHECK ( price_per_consultation > 0 ) not null,
    identification_number  nchar(14) UNIQUE                             not null,
    create_date            date                                         not null,
    update_date            date                                         not null,
    constraint pk_doctor primary key (id)
);
create table illness
(
    id            serial,
    name          varchar(30) UNIQUE                          not null,
    description   varchar(250)                                not null,
    chance_to_die int CHECK (chance_to_die BETWEEN 0 AND 100) not null,
    create_date   date                                        not null,
    update_date   date                                        not null,
    constraint pk_illness primary key (id)
);
create table doctor_illness
(
    id         serial,
    doctor_id  int not null,
    illness_id int not null,
    constraint FK_patient foreign key (doctor_id) references doctor (id) on
        delete cascade,
    constraint FK_illness foreign key (illness_id) references illness (id) on delete cascade,
    constraint pk_doctor_illness primary key (id)
);
create table users
(
    id          serial,
    username    VARCHAR(30) UNIQUE               not null,
    password    VARCHAR(60)                      not null,
    role        int CHECK (role BETWEEN 0 AND 1) not null,
    create_date date                             not null,
    update_date date                             not null,
    constraint pk_user primary key (id)
);
create table orders
(
    id          serial,
    user_id     int  not null,
    create_date date not null,
    constraint pk_orders primary key (id),
    constraint fk_order_user foreign key (user_id) references users (id) on
        delete cascade
);
create table order_doctor
(
    id        serial,
    doctor_id int not null,
    order_id  int not null,
    constraint pk_order_doctor primary key (id),
    constraint fk_order_order foreign key (doctor_id) references doctor (id) on
        delete cascade,
    constraint fk_order_doctor foreign key (order_id) references orders (id) on
        delete cascade
)