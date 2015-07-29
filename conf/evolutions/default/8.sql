# Communication types schema
 
# --- !Ups
DROP TABLE IF EXISTS communication_types CASCADE;
CREATE TABLE communication_types (
	id serial not null primary key,
	name varchar(100) not null
);

# --- !Downs
 
DROP TABLE communication_types;