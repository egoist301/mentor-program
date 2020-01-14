create table patients (
	id serial,
	first_name varchar(16),
	last_name varchar(16),
	middle_name varchar(16),
	phone_number int,
	date_of_birth date,
	constraint pk_patient primary key (id)
);
create table illness (
	id serial,
	name varchar(30) UNIQUE,
	name_in_latin varchar(60),
    chance_to_die int CHECK ( chance_to_die BETWEEN 0 AND 100),
	constraint pk_illness primary key (id)
);
create table patients_illness (
	id serial,
	patient_id int,
	illness_id int,
	constraint FK_patient foreign key(patient_id) references patients(id) on delete cascade,
	constraint FK_illness foreign key(illness_id) references illness(id) on delete cascade,
	constraint pk_patient_illness primary key (id)
)