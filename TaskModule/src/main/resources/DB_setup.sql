DROP TABLE IF EXISTS TYPE CASCADE;
DROP TABLE IF EXISTS STATUS CASCADE;
DROP TABLE IF EXISTS CATEGORY CASCADE;
DROP TABLE IF EXISTS RECURRENCE CASCADE;
DROP TABLE IF EXISTS TASK CASCADE;

CREATE TABLE TYPE (id integer PRIMARY KEY,
                   name varchar(256));

CREATE TABLE STATUS (id integer PRIMARY KEY,
                     name varchar(256));

CREATE TABLE CATEGORY (id integer PRIMARY KEY,
                       name varchar(256));

CREATE TABLE RECURRENCE (id integer PRIMARY KEY,
                         category_id integer REFERENCES CATEGORY,
                         recurrence varchar(256),
                         last_date Date DEFAULT CURRENT_DATE);

CREATE TABLE TASK (id integer PRIMARY KEY,
                   name varchar(256),
                   type_id integer REFERENCES TYPE,
                   status_id integer REFERENCES STATUS,
                   recurr_id integer REFERENCES RECURRENCE,
                   date_initiated Date,
                   date_completed Date,
                   is_complete boolean);

INSERT INTO TYPE (id, name)
VALUES (1, 'CLEANING');
INSERT INTO TYPE (id, name)
VALUES (2, 'APPOINTMENT');
INSERT INTO TYPE (id, name)
VALUES (3, 'TRAVEL');
INSERT INTO TYPE (id, name)
VALUES (4, 'VISITOR');
INSERT INTO TYPE (id, name)
VALUES (5, 'MEDICINE');
INSERT INTO TYPE (id, name)
VALUES (6, 'TODO');

INSERT INTO STATUS (id, name)
VALUES (1, 'NOT STARTED');
INSERT INTO STATUS (id, name)
VALUES (2, 'IN PROGRESS');
INSERT INTO STATUS (id, name)
VALUES (3, 'COMPLETED');
INSERT INTO STATUS (id, name)
VALUES (4, 'MIGRATED');
INSERT INTO STATUS (id, name)
VALUES (5, 'CANCELED');

INSERT INTO CATEGORY (id, name)
VALUES (1, 'WEEKLY');
INSERT INTO CATEGORY (id, name)
VALUES (2, 'MONTHLY');
INSERT INTO CATEGORY (id, name)
VALUES (3, 'BIWEEKLY');
INSERT INTO CATEGORY (id, name)
VALUES (4, 'EVERYDAY');

INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (1, 1, 'MONDAY', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (2, 1, 'TUESDAY', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (3, 1, 'WEDNESDAY', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (4, 1, 'THURSDAY', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (5, 1, 'FRIDAY', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (6, 1, 'SATURDAY', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (7, 1, 'SUNDAY', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (8, 3, 'MONDAY', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (9, 3, 'TUESDAY', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (10, 3, 'WEDNESDAY', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (11, 3, 'THURSDAY', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (12, 3, 'FRIDAY', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (13, 3, 'SATURDAY', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (14, 3, 'SUNDAY', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (15, 2, 'FIRST', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (16, 2, 'FIFTEENTH', '2023-12-25');
INSERT INTO RECURRENCE (id, category_id, recurrence, last_date)
VALUES (17, 4, 'ALL', '2023-12-25');

SELECT * FROM CATEGORY;
SELECT * FROM RECURRENCE;
SELECT * FROM STATUS;
SELECT * FROM TYPE;
SELECT * FROM TASK;