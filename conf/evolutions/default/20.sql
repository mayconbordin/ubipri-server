# access_type_classifier schema
 
# --- !Ups
DROP TABLE IF EXISTS access_type_classifier CASCADE;
CREATE TABLE access_type_classifier (
	id serial not null primary key,
	
	user_profile_environment_id integer not null,
	environment_type_id integer not null,
	weekday int not null,
	shift char(1) not null,
	workday char(1) not null,
	
	access_type_id integer not null,
	
	foreign key (user_profile_environment_id) references user_profile_environments (id),
	foreign key (environment_type_id) references environment_types (id),
	foreign key (access_type_id) references access_types (id)
);

# --- !Downs
 
DROP TABLE access_type_classifier;