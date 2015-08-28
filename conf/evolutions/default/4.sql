# Environments schema
 
# --- !Ups
DROP TABLE IF EXISTS environments CASCADE;
CREATE TABLE environments (
	id serial not null primary key,
	name varchar(100) not null,
	
	-- replacement for (lat, lon, alt)
	location geometry(PointZ,4326) not null,
	
	-- replacement for environment_points, uses a POLYGON
	shape geometry(Polygon, 4326),
	
	operating_range double precision not null default 1.0,
	version integer not null default 0,
	
	parent_environment_id integer,
	environment_type_id integer not null,
	localization_type_id integer NOT NULL,
	
	foreign key (parent_environment_id) references environments (id),
	foreign key (environment_type_id) references environment_types (id),
	foreign key (localization_type_id) references localization_types (id)
);

CREATE INDEX environment_location_idx ON environments USING GIST (location);
CREATE INDEX environment_shape_idx ON environments USING GIST (shape);
 
# --- !Downs
 
DROP INDEX IF EXISTS environment_location_idx;
DROP INDEX IF EXISTS environment_shape_idx;
DROP TABLE environments;