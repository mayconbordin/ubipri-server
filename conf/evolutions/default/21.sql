# access_type_classifier schema
 
# --- !Ups
DROP TABLE IF EXISTS user_credentials CASCADE;
CREATE TABLE user_credentials (
    id serial not null primary key,
    external_id character varying(255) NOT NULL,
    system character varying(50) NOT NULL,
    user_id integer NOT NULL,
    
    foreign key (user_id) references users (id)
);

DROP INDEX IF EXISTS user_credentials_external_id_system_idx CASCADE;
CREATE INDEX user_credentials_external_id_system_idx ON user_credentials
USING btree
(
  external_id ASC NULLS LAST,
  system ASC NULLS LAST
);

# --- !Downs
 
DROP INDEX IF EXISTS user_credentials_external_id_system_idx CASCADE;
DROP TABLE user_credentials;