insert into patient(first_name, last_name, middle_name, phone_number, date_of_birth, identification_number, create_date
, update_date)
values ('Yauheni', 'Filipovich', 'Aleksandrovich', 3221114, '2000-08-27', '1234567890qwer', current_date, current_date);

insert into illness(name, description, chance_to_die, create_date, update_date)
values ('сифилис', 'Lues secundaria recens', 80, current_date, current_date),
('аппендицит', 'appendicitis', 14, current_date, current_date),
('артрит', 'arthritis', 24, current_date, current_date),
('бронхит', 'bronchitis', 38, current_date, current_date),
('грипп', 'grippus', 27, current_date, current_date),
('язва желудка', 'ulcus ventriculi', 31, current_date, current_date),
('чесотка', 'scabies', 45, current_date, current_date),
('менингит', 'meningitis', 54, current_date, current_date);

insert into patient_illness(patient_id, illness_id)
values (1, 2), (1, 1), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8);