# actions schema
 
# --- !Ups
DROP TABLE IF EXISTS actions CASCADE;
CREATE TABLE actions (
	id serial not null primary key,
	
	access_level_id integer not null,
	functionality_id integer not null,
	custom_environment_id integer,
	
	action_name varchar(100) not null,
	
	start_date timestamp,
	end_date timestamp,
	
	start_daily_interval integer, -- tempo em segundos 60 = 1 min, 3600 = 1 hora etc. - até a hora 0
	interval_duration integer, -- tempo em segundos 60 = 1 min, 3600 = 1 hora etc.
	
	foreign key (access_level_id) references access_levels (id),
	foreign key (functionality_id) references functionalities (id),
	foreign key (custom_environment_id) references environments (id)
);

# --- !Downs
 
DROP TABLE actions;