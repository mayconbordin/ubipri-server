# Environment types schema
 
# --- !Ups
DROP TABLE IF EXISTS environment_types CASCADE;
CREATE TABLE environment_types (
	id serial not null primary key,
	name varchar(100) not null
);
 
# --- !Downs
 
DROP TABLE environment_types;