INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_RESPONSIBLE');

-- CREATE OR REPLACE FUNCTION test()
--     RETURNS trigger AS
-- '
-- BEGIN
--     INSERT INTO  health(uuid) VALUES (users.uuid);
--
--     RETURN NEW;
-- END;
--
--     ' LANGUAGE plpgsql;
--
-- CREATE TRIGGER test_trigger
--     BEFORE INSERT
--     ON users
--     FOR EACH ROW
-- EXECUTE PROCEDURE test(users.uuid);
