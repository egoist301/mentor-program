INSERT INTO patients(first_name, last_name, middle_name, phone_number,
                     date_of_birth)
VALUES ('Yauheni', 'Filipovich',
        'Aleksandrovich', 3221114,
        '2000-08-27'),
       ('Oleg', 'Bondarev', 'Dmitrievich', 2345678, '1998-09-01'),
       ('Diana', 'Tynkovan', 'Dmitrievna', 1233445, '2000-03-02'),
       ('Vladislav', 'Korotkevich', 'Ruslanovich', 7654321, '2000-03-04'),
       ('Nastya', 'Tolstikova', 'Eduardovna', 9877665, '1999-11-29'),
       ('Vladislav', 'Kondrashkov', 'Olegovich', 5463728, '2000-01-03');

INSERT INTO illness(name, name_in_latin, chance_to_die)
VALUES ('сифилис', 'Lues secundaria recens', 80),
       ('аппендицит', 'appendicitis', 14),
       ('артрит', 'arthritis', 24),
       ('бронхит', 'bronchitis', 38),
       ('грипп', 'grippus', 27),
       ('язва желудка', 'ulcus ventriculi', 31),
       ('чесотка', 'scabies', 45),
       ('менингит', 'meningitis', 54);