---Author
INSERT INTO author (name, surname) VALUES ('andrey','pupkin');
INSERT INTO author (name, surname) VALUES ('vasya','petrov');
INSERT INTO author (name, surname) VALUES ('artiom','guretskij');
INSERT INTO author (name, surname) VALUES ('valeryia','liudchyk');
INSERT INTO author (name, surname) VALUES ('alesya','Glushakova');

---tag
INSERT INTO tag (tag) VALUES ('java');
INSERT INTO tag (tag) VALUES ('python');
INSERT INTO tag (tag) VALUES ('html');
INSERT INTO tag (tag) VALUES ('css');
INSERT INTO tag (tag) VALUES ('sql');

---news
INSERT INTO news (title) VALUES ('oracle');
INSERT INTO news (title) VALUES ('postgree');
INSERT INTO news (title) VALUES ('microsoft server');
INSERT INTO news (title) VALUES ('mysql');
INSERT INTO news (title) VALUES ('mongo');

---news_author
INSERT INTO news_author (news_id, author_id) VALUES (1,2);
INSERT INTO news_author (news_id, author_id) VALUES (3,4);
INSERT INTO news_author (news_id, author_id) VALUES (2,3);
INSERT INTO news_author (news_id, author_id) VALUES (4,5);
INSERT INTO news_author (news_id, author_id) VALUES (5,1);

---news_tag
INSERT INTO news_tag (news_id, tag_id) VALUES (1,2);
INSERT INTO news_tag (news_id, tag_id) VALUES (3,4);
INSERT INTO news_tag (news_id, tag_id) VALUES (2,3);
INSERT INTO news_tag (news_id, tag_id) VALUES (4,5);
INSERT INTO news_tag (news_id, tag_id) VALUES (5,1);