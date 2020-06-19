---tables
CREATE TABLE tag
(
    	id NUMBER(10) GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
    	tag VARCHAR2(30) UNIQUE,
    	PRIMARY KEY  (id)
);

create table author
(
    	id NUMBER(10) GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
    	name varchar2(30) not null,
    	surname varchar2(30) not null,
	PRIMARY KEY  (id)
);

create table news
(
	id NUMBER(10) GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
	title varchar2(30) not null,
	PRIMARY KEY  (id)
);

create table news_tag
(
	news_id NUMBER(10) not null,
	tag_id NUMBER(10) not null,
	foreign key(news_id) references news(id) on update cascade on delete cascade,
	foreign key(tag_id) references tag(id) on update cascade on delete cascade
);

create table news_author
(
	news_id NUMBER(10) not null,
	author_id NUMBER(10) not null,
	foreign key(news_id) references news(id) on update cascade on delete cascade,
	foreign key(author_id) references author(id) on update cascade on delete cascade
);