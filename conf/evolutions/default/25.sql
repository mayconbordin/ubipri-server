# add column to access_type_classifier schema
 
# --- !Ups
ALTER TABLE access_type_classifier ADD COLUMN frequency_level_id integer NOT NULL;

ALTER TABLE access_type_classifier ADD CONSTRAINT access_type_classifier_frequency_level_id_fkey FOREIGN KEY (frequency_level_id)
REFERENCES frequency_levels (id);

# --- !Downs

ALTER TABLE access_type_classifier DROP CONSTRAINT access_type_classifier_frequency_level_id_fkey;
ALTER TABLE access_type_classifier DROP COLUMN frequency_level_id;