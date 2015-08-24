# environment_frequency_levels schema
 
# --- !Ups
DROP TABLE IF EXISTS environment_frequency_levels CASCADE;

CREATE TABLE environment_frequency_levels (
    environment_id integer NOT NULL,
    frequency_level_id integer NOT NULL,
    min double precision NOT NULL,
    max double precision NOT NULL,
    period integer NOT NULL,
    metric character(1) NOT NULL,
    
    CONSTRAINT environment_frequency_levels_pkey PRIMARY KEY (environment_id, frequency_level_id),
    FOREIGN KEY (environment_id) REFERENCES environments (id),
    FOREIGN KEY (frequency_level_id) REFERENCES frequency_levels (id)
);

# --- !Downs

DROP TABLE environment_frequency_levels;