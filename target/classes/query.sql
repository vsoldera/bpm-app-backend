drop table if exists role;
drop table if exists user_entity;
drop table if exists user_roles;
create table role (id SERIAL not null, description varchar(255), name varchar(255), primary key (id));
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id));


INSERT INTO role (id, description, name) VALUES (4, 'Admin role', 'ROLE_ADMIN');
INSERT INTO role (id, description, name) VALUES (5, 'User role', 'ROLE_USER');

CREATE TABLE phone_code(
    pc_id SERIAL NOT NULL,
    pc_phone varchar(11),
    pc_code varchar(6) ,
    pc_create timestamp NULL,
    PRIMARY KEY (pc_id)
    ) ;
--Initialization captcha data
INSERT INTO phone_code VALUES (1,'17111111111','123123','2019-12-04 03:01:05');