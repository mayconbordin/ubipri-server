# drop sha_password column from users schema
 
# --- !Ups
ALTER TABLE users DROP COLUMN sha_password;

# --- !Downs
ALTER TABLE users ADD COLUMN sha_password bytea NULL;