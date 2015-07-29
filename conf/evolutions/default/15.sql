# user_profile_environments schema
 
# --- !Ups
DROP TABLE IF EXISTS user_profile_environments CASCADE;
CREATE TABLE user_profile_environments (
	id serial not null primary key,
	name varchar(100) not null
);

# --- !Downs
 
DROP TABLE user_profile_environments;