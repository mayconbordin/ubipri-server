# access_types schema
 
# --- !Ups
DROP TABLE IF EXISTS access_types CASCADE;
CREATE TABLE access_types (
	id serial not null primary key,
	name varchar(100) not null
);

# --- !Downs
 
DROP TABLE access_types;