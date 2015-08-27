# change log_events schema
 
# --- !Ups
ALTER TABLE log_events DROP COLUMN event;
ALTER TABLE log_events DROP COLUMN code;
ALTER TABLE log_events ADD COLUMN exiting BOOLEAN NOT NULL DEFAULT FALSE;

# --- !Downs
ALTER TABLE log_events ADD COLUMN event CHARACTER VARYING(100);
ALTER TABLE log_events ADD COLUMN code CHARACTER VARYING(100);
ALTER TABLE log_events DROP COLUMN exiting;