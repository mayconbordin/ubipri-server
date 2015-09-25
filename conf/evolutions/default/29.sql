# add oauth tables schema
 
# --- !Ups
create table oauth_clients
(
  id serial not null primary key,
  owner_id bigint not null,
  grant_type varchar(20) not null,
  client_id varchar(100) not null,
  client_secret varchar(100) not null,
  redirect_uri varchar(2000),
  created_at timestamp not null default current_timestamp,

  constraint oauth_client_owner_id_fkey foreign key (owner_id)
    references users (id) on delete cascade,
  constraint oauth_client_client_id_key unique (client_id)
);
create index oauth_client_owner_id_idx on oauth_clients(owner_id);


create table oauth_authorization_codes
(
  id serial not null primary key,
  account_id bigint not null,
  oauth_client_id bigint not null,
  code varchar(100) not null,
  redirect_uri varchar(2000) not null,
  created_at timestamp not null default current_timestamp,

  constraint oauth_authorization_codes_account_id_fkey foreign key (account_id)
    references users (id) on delete cascade,
  constraint oauth_authorization_codes_oauth_client_id_fkey foreign key (oauth_client_id)
    references oauth_clients (id) on delete cascade
);

create index oauth_authorization_codes_account_id_idx on oauth_authorization_codes(account_id);
create unique index oauth_authorizations_code_code_idx on oauth_authorization_codes(code);
create index oauth_authorization_codes_oauth_client_id_idx on oauth_authorization_codes(oauth_client_id);


create table oauth_access_tokens
(
  id serial not null primary key,
  account_id bigint not null,
  oauth_client_id bigint not null,
  access_token varchar(100) not null,
  refresh_token varchar(100) not null,
  created_at timestamp not null default current_timestamp,

  constraint oauth_access_tokens_account_id_fkey foreign key (account_id)
    references users (id) on delete cascade,
  constraint oauth_access_tokens_oauth_client_id_fkey foreign key (oauth_client_id)
    references oauth_clients (id) on delete cascade
);
create index oauth_access_tokens_account_id_idx on oauth_access_tokens(account_id);
create index oauth_access_tokens_oauth_client_id_idx on oauth_access_tokens(oauth_client_id);
create unique index oauth_access_tokens_access_token_idx on oauth_access_tokens(access_token);
create unique index oauth_access_tokens_refresh_token_idx on oauth_access_tokens(refresh_token);


# --- !Downs
drop index oauth_access_tokens_account_id_idx;
drop index oauth_access_tokens_oauth_client_id_idx;
drop index oauth_access_tokens_access_token_idx;
drop index oauth_access_tokens_refresh_token_idx;
drop table oauth_access_tokens;

drop index oauth_authorization_codes_account_id_idx;
drop index oauth_authorizations_code_code_idx;
drop index oauth_authorization_codes_oauth_client_id_idx;
drop table oauth_authorization_codes;

drop index oauth_client_owner_id_idx;
drop table oauth_clients;