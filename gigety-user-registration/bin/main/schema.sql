-- Drop tables created via this schema.sql
drop table if exists persistent_logins;
DROP TABLE IF EXISTS oauth_code;
drop table if exists oauth_refresh_token;
drop table if exists ClientDetails;
drop table if exists oauth_approvals;
drop table if exists oauth_code;
drop table if exists oauth_refresh_token;
drop table if exists oauth_access_token;
drop table if exists oauth_client_token;
drop table if exists oauth_client_details; 

-- Table used by remember-me feature of spring security
create table if not exists persistent_logins (
	username varchar(64) not null,
	series varchar(64) primary key,
	token varchar(64) not null,
	last_used timestamp not null
);

-- used in tests that use HSQL
create table oauth_client_details (
  client_id VARCHAR(256),
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256),
  PRIMARY KEY (client_id)
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token VARBINARY(256),
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256),
   PRIMARY KEY(authentication_id)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token VARBINARY(256),
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication VARBINARY(256),
  refresh_token VARCHAR(256),
   PRIMARY KEY(authentication_id)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token VARBINARY(256),
  authentication VARBINARY(256)
);

create table oauth_code (
  code VARCHAR(256), authentication VARBINARY(256)
);

create table oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);


-- customized oauth_client_details table
create table ClientDetails (
  appId VARCHAR(256),
  resourceIds VARCHAR(256),
  appSecret VARCHAR(256),
  scope VARCHAR(256),
  grantTypes VARCHAR(256),
  redirectUrl VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(256),
   PRIMARY KEY(appId)
);
CREATE TABLE if not exists oauth_refresh_token (
  token_id       VARCHAR(255),
  token          BLOB,
  authentication BLOB
);

CREATE TABLE if not exists oauth_code (
  code           VARCHAR(255),
  authentication BLOB
);
