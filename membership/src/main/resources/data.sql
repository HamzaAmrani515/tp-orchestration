DELETE FROM members;

INSERT INTO members (id, first_name, last_name, email, password_hash, roles, active)
VALUES (
           1,
           'Demo',
           'Member',
           'demo.member@test.com',
           '$2a$10$Zj8MRyfNLSt.Jpl/5eIdUuBog9FpXM7BnDL.ybe58JsfnOu/dZqM6',
           'ROLE_USER',
           TRUE
       );
