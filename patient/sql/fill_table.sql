insert into patient( first_name, last_name, middle_name, phone_number
                   , date_of_birth, identification_number, create_date
                   , update_date)
values ('Yauheni', 'Filipovich', 'Aleksandrovich', 3221114, '2000-08-27',
        '1234567890QWER', '2020-01-26', '2020-01-26'),
       ('Vladislav', 'Korotkevich', 'Ruslanovich', 1234567, '2000-03-04',
        '123ABVC890qwer', '2020-01-26', '2020-01-26'),
       ('Vlad', 'Kondrashkov', 'Olegovich', 9876543, '2000-01-03',
        'HGF4567890qwer', '2020-01-26', '2020-01-26');

insert into illness(name, description, chance_to_die, create_date, update_date)
values ('сифилис',
        'Хроническое системное венерическое инфекционное заболевание с ' ||
        'поражением кожи, слизистых оболочек, внутренних органов, костей, ' ||
        'нервной системы с последовательной сменой стадий болезни. Передаётся' ||
        ' половым путём.', 80, '2020-01-26', '2020-01-26'),
       ('энурез',
        'Заболевание, характеризующееся недержанием мочи у человека. В
 большинстве случаев носителями энуреза являются дети, часть подростков,
 небольшое количество взрослых. В основном проявляется во время сна, менее
 распространён вне сна.', 14, '2020-01-26', '2020-01-26'),
       ('простатит',
        'Одна из самых страшных и неловких болезней в мужском понимании, так
 как напрямую связана с репродуктивной системой. Недуг представляет собой
 воспаление предстательной железы, которая обвивает шейку мочевого пузыря.',
        31, '2020-01-26', '2020-01-26'),
       ('синдром туретта',
        'Генетически обусловленное расстройство центральной нервной системы,
 которое проявляется в любом возрасте и характеризуется множественными
 двигательными тиками и как минимум одним голосовым, появляющимися много раз в
 течение дня.', 32, '2020-01-26', '2020-01-26'),
       ('импотенция',
        'Объём полового члена мужчины, его твёрдость и прямота недостаточны
 для совершения полового акта. Описывает неспособность мужчины ввести половой
 член и завершить совокупление по  причине отсутствия достаточной
 напряжённости полового члена.', 8, '2020-01-26',
        '2020-01-26'),
       ('синдром щелкунчика',
        'В более грубом варианте перевода - "синдром щипцов для орехов". ' ||
        'Иногда левая почечная вена сдавливается между аортой и другой ' ||
        'артерией в своеобразной "вилке" или "пинцете" (nutcracker syndrome).' ||
        ' Синдром развивается у женщин возрасте от 20 до 40 лет.', 30,
        '2020-01-26', '2020-01-26'),
       ('синдром беспокойных гениталий',
        'Определяется как спонтанное, навязчивое и нежелательное половое ' ||
        'возбуждение, сопровождающееся покалыванием или пульсацией, которое ' ||
        'происходит в отсутствие какого-либо сексуального желания или прямой ' ||
        'причины для полового возбуждения.', 13, '2020-01-26', '2020-01-26'),
       ('синдром Рапунцель',
        'Синдромом Рапунцель называют редкое состояние человека с ' ||
        'непроходимостью кишечника, которая возникает в результате ' ||
        'неконтролируемого жевания собственных волос.', 34, '2020-01-26',
        '2020-01-26'),
       ('Синдром Дориана Грея',
        'Люди характеризуются чрезмерной озабоченностью своими физическими ' ||
        'данными и молодостью. Стремясь оставаться вечно юными и свежими, ' ||
        'часто именно они становятся изуродованными жертвами многочисленных ' ||
        'пластических операций и др. косметических процедур.', 45,
        '2020-01-26', '2020-01-26'),
       ('комлекс "Куда спрятать лысину"',
        'Комплекс имени славного военачальника Красной Армии, сейчас ' ||
        'известного в основном благодаря своей радикальной прическе налысо, ' ||
        '«под Котовского». Проблему лысины Котовский решил по-кавалерийски: ' ||
        'нет волос – нет проблемы.', 7, '2020-01-26', '2020-01-26'),
       ('"Болезнь двадцатого века"',
        'Множественная химическая чувствительность. Характеризуется ' ||
        'негативными реакциями на различные современные химикаты и продукты, ' ||
        'в том числе пластмассу и синтетические волокна. Пациенты не ' ||
        'реагируют, если не знают, что взаимодействуют с химикатами.', 19,
        '2020-01-26', '2020-01-26');

insert into patient_illness(patient_id, illness_id)
values (1, 2),
       (1, 1),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8);