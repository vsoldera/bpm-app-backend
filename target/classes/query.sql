
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_RESPONSIBLE');

CREATE TABLE phone_code(
    pc_id SERIAL NOT NULL,
    pc_phone varchar(11),
    pc_code varchar(6) ,
    pc_create timestamp NULL,
    PRIMARY KEY (pc_id)
    ) ;
--Initialization captcha data
INSERT INTO phone_code VALUES (1,'17111111111','123123','2019-12-04 03:01:05');