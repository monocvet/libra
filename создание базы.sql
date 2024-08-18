create table person(
                       id bigint generated by default as identity primary key,
                       name varchar(50) unique not null,
                       age int not null,
                       email varchar(50) not null,
                       phone_number varchar(50) not null,
                       password varchar(100),
                       role varchar(50) not null,
                       removed boolean default false,
                       created_at timestamp,
                       updated_at timestamp,
                       removed_at timestamp,
                       created_person varchar(100) ,
                       updated_person varchar(100) ,
                       removed_person varchar(100)
);
insert into person(name,age,email,phone_number,password,role,removed,created_at,updated_at,removed_at,created_person,updated_person,removed_person)
VALUES('Иванов В.П.',1970,'test@gmail.com','+79272345768','qwerty','ADMIN',false,'2023-03-22 11:31:26.000000',null,null,null,null,null);
insert into person(name,age,email,phone_number,password,role,removed,created_at,updated_at,removed_at,created_person,updated_person,removed_person)
VALUES('Петров А.М.',1980,'test1@gmail.com','+79272555768','qwerty','USER',false,'2023-03-22 11:31:26.000000',null,null,null,null,null);
insert into person(name,age,email,phone_number,password,role,removed,created_at,updated_at,removed_at,created_person,updated_person,removed_person)
VALUES('Арбузова Л.М.',1975,'test2@gmail.com','+79272558868','qwerty','USER',false,'2023-03-22 11:31:26.000000',null,null,null,null,null);
insert into person(name,age,email,phone_number,password,role,removed,created_at,updated_at,removed_at,created_person,updated_person,removed_person)
VALUES('Иванченко Ю.В.',1972,'test3@gmail.com','+7927565768','qwerty','USER',false,'2023-03-22 11:31:26.000000',null,null,null,null,null);
insert into person(name,age,email,phone_number,password,role,removed,created_at,updated_at,removed_at,created_person,updated_person,removed_person)
VALUES('Серова Т.В.',1982,'test4@gmail.com','+79272555799','qwerty','USER',false,'2023-03-22 11:31:26.000000',null,null,null,null,null);

create table book(
                     id bigint generated by default as identity primary key,
                     person_id int references person (id) on delete set null ,
                     image_id int references image (id) on delete set null ,
                     name varchar(50) not null,
                     year_of_production int not null,
                     author varchar(100) not null,
                     annotation  varchar(200) not null,
                     removed boolean default false,
                     created_at timestamp,
                     updated_at timestamp,
                     removed_at timestamp,
                     created_person varchar(100) ,
                     updated_person varchar(100) ,
                     removed_person varchar(100)
);

insert into book(person_id,image_id,name,year_of_production,author,annotation,removed,created_at,updated_at,removed_at,created_person,updated_person,removed_person)
VALUES (3,null,'Бегущий по лезвию',2019,'Грин Джонсон','annotation',false,'2023-03-22 11:31:26.000000',null,null,null,null,null);
insert into book(person_id,image_id,name,year_of_production,author,annotation,removed,created_at,updated_at,removed_at,created_person,updated_person,removed_person)
VALUES (1,null,'Красная тетрадь',2022,'Беляева Дарья Андреевна','annotation1',false,'2023-03-22 11:31:26.000000',null,null,null,null,null);
insert into book(person_id,image_id,name,year_of_production,author,annotation,removed,created_at,updated_at,removed_at,created_person,updated_person,removed_person)
VALUES (4,null,'Маленькая косатка',2022,'Михаил Нагайлик','annotation2',false,'2023-03-22 11:31:26.000000',null,null,null,null,null);
insert into book(person_id,image_id,name,year_of_production,author,annotation,removed,created_at,updated_at,removed_at,created_person,updated_person,removed_person)
VALUES (2,null,'Вечные любовники',2022,'Уильям Тревор','annotation3',false,'2023-03-22 11:31:26.000000',null,null,null,null,null);
insert into book(person_id,image_id,name,year_of_production,author,annotation,removed,created_at,updated_at,removed_at,created_person,updated_person,removed_person)
VALUES (1,null,'Мальчик Мотл',2018,'Шолом-Алейхем','annotation4',false,'2023-03-22 11:31:26.000000',null,null,null,null,null);
insert into book(person_id,image_id,name,year_of_production,author,annotation,removed,created_at,updated_at,removed_at,created_person,updated_person,removed_person)
VALUES (5,null,'Катрин Блюм',2023,'Александр Дюма','annotation5',false,'2023-03-22 11:31:26.000000',null,null,null,null,null);


select * from person;
select * from book;

drop table if exists person;
drop table if exists book;