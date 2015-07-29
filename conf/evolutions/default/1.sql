# User types schema
 
# --- !Ups
DROP TABLE IF EXISTS user_types CASCADE;
CREATE TABLE user_types (
	id serial not null primary key,
	name varchar(100) not null
);
 
# --- !Downs
 
DROP TABLE user_types;