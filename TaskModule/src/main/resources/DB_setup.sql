DROP TABLE IF EXISTS TYPE CASCADE;
DROP TABLE IF EXISTS STATUS CASCADE;
DROP TABLE IF EXISTS CATEGORY CASCADE;
DROP TABLE IF EXISTS RECURRENCE CASCADE;
DROP TABLE IF EXISTS TASK CASCADE;

CREATE TABLE TYPE (id integer PRIMARY KEY generated always as identity,
                   name varchar(256));

CREATE TABLE STATUS (id integer PRIMARY KEY generated always as identity,
                     name varchar(256));

CREATE TABLE CATEGORY (id integer PRIMARY KEY generated always as identity,
                       name varchar(256));

CREATE TABLE RECURRENCE (id integer PRIMARY KEY generated always as identity,
                         category_id integer REFERENCES CATEGORY,
                         recurrence varchar(256),
                         last_date Date DEFAULT CURRENT_DATE);

CREATE TABLE TASK (id integer PRIMARY KEY generated always as identity,
                   name varchar(256),
                   type_id integer REFERENCES TYPE,
                   status_id integer REFERENCES STATUS,
                   recurr_id integer REFERENCES RECURRENCE,
                   date_initiated Date,
                   date_completed Date,
                   is_complete boolean);

INSERT INTO TYPE (name)
VALUES ('CLEANING');
INSERT INTO TYPE (name)
VALUES ('APPOINTMENT');
INSERT INTO TYPE (name)
VALUES ('TRAVEL');
INSERT INTO TYPE (name)
VALUES ('VISITOR');
INSERT INTO TYPE (name)
VALUES ('MEDICINE');
INSERT INTO TYPE (name)
VALUES ('TODO');

INSERT INTO STATUS (name)
VALUES ('NOT STARTED');
INSERT INTO STATUS (name)
VALUES ('IN PROGRESS');
INSERT INTO STATUS (name)
VALUES ('COMPLETED');
INSERT INTO STATUS (name)
VALUES ('MIGRATED');
INSERT INTO STATUS (name)
VALUES ('CANCELED');

INSERT INTO CATEGORY (name)
VALUES ('WEEKLY');
INSERT INTO CATEGORY (name)
VALUES ('MONTHLY');
INSERT INTO CATEGORY (name)
VALUES ('BIWEEKLY');
INSERT INTO CATEGORY (name)
VALUES ('EVERYDAY');

INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (1, 'MONDAY', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (1, 'TUESDAY', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (1, 'WEDNESDAY', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (1, 'THURSDAY', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (1, 'FRIDAY', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (1, 'SATURDAY', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (1, 'SUNDAY', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (3, 'MONDAY', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (3, 'TUESDAY', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (3, 'WEDNESDAY', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (3, 'THURSDAY', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (3, 'FRIDAY', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (3, 'SATURDAY', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (3, 'SUNDAY', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (2, 'FIRST', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (2, 'FIFTEENTH', '2023-12-25');
INSERT INTO RECURRENCE (category_id, recurrence, last_date)
VALUES (4, 'ALL', '2023-12-25');

SELECT * FROM CATEGORY;
SELECT * FROM RECURRENCE;
SELECT * FROM STATUS;
SELECT * FROM TYPE;
SELECT * FROM TASK;