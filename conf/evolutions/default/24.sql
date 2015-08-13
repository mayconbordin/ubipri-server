# add column to user_in_environment schema
 
# --- !Ups
ALTER TABLE user_in_environment ADD COLUMN current_frequency_level_id integer NULL;

ALTER TABLE user_in_environment ADD CONSTRAINT user_in_environment_current_freq_lev_fkey FOREIGN KEY (current_frequency_level_id)
REFERENCES frequency_levels (id);

# --- !Downs

ALTER TABLE user_in_environment DROP CONSTRAINT user_in_environment_current_freq_lev_fkey;
ALTER TABLE user_in_environment DROP COLUMN current_frequency_level_id;