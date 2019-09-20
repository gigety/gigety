-- Using import.sql instead of data.sql as data.sql loads before JPA entities are created due to presence of schema.sql
insert into privilege(name, id) values ('READ_PRIVILEGE', 1)
insert into privilege(name, id) values ('WRITE_PRIVILEGE', 2)

insert into role(name, id) values('ROLE_ADMIN', 1)
insert into role(name, id) values('ROLE_USER', 2)

insert into roles_privileges(role_id, privilege_id) values (1 ,1)
insert into roles_privileges(role_id, privilege_id) values (1 ,2)
insert into roles_privileges(role_id, privilege_id) values (2 ,1)


insert into gig_user(id, email, password, enabled) values(1, 'test@test.com', '$2a$10$VIyLrb5SbIjPqvbCzomsHOStNBildCAGdffMamVW1QeW.9KV1HgAC', true)

insert into users_roles (user_id, role_id) values (1,1)
insert into users_roles (user_id, role_id) values (1,2)