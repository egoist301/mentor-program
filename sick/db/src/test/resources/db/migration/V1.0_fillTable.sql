insert into doctor( first_name, last_name, middle_name, phone_number
                  , date_of_birth, identification_number, create_date
                  , update_date)
values ('Yauheni', 'Filipovich', 'Aleksandrovich', 3221114, '2000-08-27',
        '1234567890QWER', '2020-01-26', '2020-01-26');

insert into illness(name, description, chance_to_die, create_date, update_date)
values ('name1', 'desc1', 80, '2020-01-26', '2020-01-26'),
       ('name2', 'desc2', 80, '2020-01-26', '2020-01-26'),
       ('name3', 'desc3', 80, '2020-01-26', '2020-01-26');

insert into doctor_illness(doctor_id, illness_id)
values (1, 2),
       (1, 1);