create table patient (
	id serial,
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	middle_name varchar(20) not null,
	phone_number int not null,
	date_of_birth date not null, identification_number nchar(14) UNIQUE not null, create_date date not null, update_date date not null, constraint pk_patient primary key(id)
);
create table illness (
	id serial,
	name varchar(30) UNIQUE not null,
	description varchar(250) not null,
  chance_to_die int CHECK (chance_to_die BETWEEN 0 AND 100) not null,
create_date date not null,
update_date date not null,
constraint pk_illness primary key(id)
);
create table patient_illness (
	id serial,
	patient_id int not null,
	illness_id int not null,
constraint FK_patient foreign key(patient_id) references patient(id) on delete cascade,
constraint FK_illness foreign key(illness_id) references illness(id) on delete cascade,
constraint pk_patient_illness primary key(id)
)