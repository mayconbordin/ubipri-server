# change environments schema
 
# --- !Ups
ALTER TABLE environments ADD COLUMN level integer not null default 0;

# --- !Downs
ALTER TABLE environments DROP COLUMN level;