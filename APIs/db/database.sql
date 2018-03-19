
create database zalego_interview_db 
 
use zalego_interview_db 
 
create table students(
   id int(11) primary key auto_increment,
   unique_id varchar(23) not null unique,
   f_name varchar(50) not null,
   l_name varchar(50) not null,
   id varchar(50) not null,
   course varchar(50) not null,
   email varchar(100) not null unique,
   encrypted_password varchar(80) not null,
   salt varchar(10) not null,
   created_at datetime,
   updated_at datetime null
);

   
