insert into patients(first_name, last_name, middle_name, phone_number,
                     date_of_birth)
values ('Yauheni', 'Filipovich',
        'Aleksandrovich', 3221114,
        '2000-08-27'),
       ('Oleg', 'Bondarev', 'Dmitrievich', 2345678, '1998-09-01'),
       ('Diana', 'Tynkovan', 'Dmitrievna', 1233445, '2000-03-02'),
       ('Vladislav', 'Korotkevich', 'Ruslanovich', 7654321, '2000-03-04'),
       ('Nastya', 'Tolstikova', 'Eduardovna', 9877665, '1999-11-29'),
       ('Vladislav', 'Kondrashkov', 'Olegovich', 5463728, '2000-01-03');

insert into illness(name, name_in_latin, chance_to_die)
values ('сифилис', 'Lues secundaria recens', 80),
       ('аппендицит', 'appendicitis', 14),
       ('артрит', 'arthritis', 24),
       ('бронхит', 'bronchitis', 38),
       ('грипп', 'grippus', 27),
       ('язва желудка', 'ulcus ventriculi', 31),
       ('чесотка', 'scabies', 45),
       ('менингит', 'meningitis', 54);

insert into patients_illness(patient_id, illness_id)
values (1,2),(1,1),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(3,2),(4,5),(5,5),(4,6),(3,8),(6,7),(6,6),(3,1);



