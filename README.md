# todos
spring boot application



## Queries that were run in Postgres db

CREATE SEQUENCE TODO_SEQ START 1000;

CREATE TABLE TODO (
ID integer primary key default nextval('TODO_SEQ'),
USER_ID integer NOT NULL,
TITLE varchar(255) NOT NULL,
COMPLETED varchar(1) NOT NULL,
CONSTRAINT TITLE UNIQUE (TITLE)
);

GRANT ALL PRIVILEGES ON TODO TO TEST_USER;

GRANT ALL PRIVILEGES ON SEQUENCE TODO_SEQ TO TEST_USER;

## Curl commands:

GET
All - curl http://localhost:8081/todos
By Id - curl http://localhost:8081/todos/198