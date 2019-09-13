-- Drop tables created via this schema.sql
drop table if exists persistent_logins;


-- Table used by remember-me feature of spring security
create table if not exists persistent_logins (
	username varchar(64) not null,
	series varchar(64) primary key,
	token varchar(64) not null,
	last_used timestamp not null
);


