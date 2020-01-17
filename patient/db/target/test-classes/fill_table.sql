insert into patient(first_name, last_name, middle_name, phone_number, date_of_birth, identification_number, create_date
, update_date)
values ('Yauheni', 'Filipovich', 'Aleksandrovich', 3221114, '2000-08-27', '1234567890qwer', '2020-01-17', '2020-01-17');

insert into illness(name, description, chance_to_die, create_date, update_date)
values ('сифилис', 'desc1', 80, '2020-01-17', '2020-01-17'),
('аппендицит', 'desc2', 14, '2020-01-17', '2020-01-17');

insert into patient_illness(patient_id, illness_id)
values (1, 2), (1, 1);
