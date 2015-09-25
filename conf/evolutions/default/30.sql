# alter users table schema
 
# --- !Ups
ALTER TABLE users ADD COLUMN password CHARACTER VARYING(60);

# --- !Downs
ALTER TABLE users DROP COLUMN password;