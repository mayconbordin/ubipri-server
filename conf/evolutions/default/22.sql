# frequency_levels schema
 
# --- !Ups
DROP TABLE IF EXISTS frequency_levels CASCADE;

CREATE TABLE frequency_levels (
    id serial not null primary key,
    name character varying(100) NOT NULL,
    level smallint NOT NULL
);

# --- !Downs

DROP TABLE frequency_levels;