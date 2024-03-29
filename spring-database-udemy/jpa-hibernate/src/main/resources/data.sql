insert into course(id, name, created_date, last_updated_date, is_deleted)
values (1000, 'Test1', sysdate, sysdate, false);
insert into course(id, name, created_date, last_updated_date, is_deleted)
values (1001, 'Test2', sysdate, sysdate, false);
insert into course(id, name, created_date, last_updated_date, is_deleted)
values (1002, 'Test3', sysdate, sysdate, false);
insert into course(id, name, created_date, last_updated_date, is_deleted)
values(1003,'Spring Boot in 100 Steps', sysdate, sysdate, false);
insert into course(id, name, created_date, last_updated_date, is_deleted)
values(1004,'Dummy1', sysdate, sysdate, false);
insert into course(id, name, created_date, last_updated_date, is_deleted)
values(1005,'Dummy2', sysdate, sysdate, false);
insert into course(id, name, created_date, last_updated_date, is_deleted)
values(1006,'Dummy3', sysdate, sysdate, false);
insert into course(id, name, created_date, last_updated_date, is_deleted)
values(1007,'Dummy4', sysdate, sysdate, false);
insert into course(id, name, created_date, last_updated_date, is_deleted)
values(1008,'Dummy5', sysdate, sysdate, false);

insert into passport(id,number)
values(4001,'E123456');
insert into passport(id,number)
values(4002,'N123457');
insert into passport(id,number)
values(4003,'L123890');

insert into student(id,name,passport_id)
values(2001,'Ranga',4001);
insert into student(id,name,passport_id)
values(2002,'Adam',4002);
insert into student(id,name,passport_id)
values(2003,'Jane',4003);

insert into review(id,rating,description,course_id)
values(5001,'FIVE', 'Great Course',1000);
insert into review(id,rating,description,course_id)
values(5002,'FOUR', 'Wonderful Course',1000);
insert into review(id,rating,description,course_id)
values(5003,'FIVE', 'Awesome Course',1002);

insert into student_course(student_id,course_id)
values(2001,1001);
insert into student_course(student_id,course_id)
values(2002,1001);
insert into student_course(student_id,course_id)
values(2003,1001);
insert into student_course(student_id,course_id)
values(2001,1002);