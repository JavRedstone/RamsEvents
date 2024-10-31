-- Table of groups to controlling user access levels
CREATE TABLE IF NOT EXISTS ramsevents.groups (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(300),
    description VARCHAR(1000),
    access_level VARCHAR(100) NOT NULL UNIQUE,
    create_date TIMESTAMP WITHOUT TIME ZONE,
    update_date TIMESTAMP WITHOUT TIME ZONE
) WITH (OIDS = FALSE);

-- Table of students
CREATE TABLE IF NOT EXISTS ramsevents.students (
    id BIGINT NOT NULL PRIMARY KEY,
    uid VARCHAR(28) NOT NULL UNIQUE,
    student_id INTEGER NOT NULL UNIQUE,
    first_name VARCHAR(300),
    middle_name VARCHAR(300),
    last_name VARCHAR(300),
    email VARCHAR(500) NOT NULL UNIQUE,
    grade SMALLINT,
    homeroom CHAR,
    create_date TIMESTAMP WITHOUT TIME ZONE,
    update_date TIMESTAMP WITHOUT TIME ZONE,
    
    fk_group_id BIGINT NOT NULL REFERENCES ramsevents.groups(id)
) WITH (OIDS = FALSE);

-- Table of school year quarters
CREATE TABLE IF NOT EXISTS ramsevents.quarters (
    id BIGINT NOT NULL PRIMARY KEY,
    school_year VARCHAR(9),
    quarter SMALLINT,
	start_date TIMESTAMP WITHOUT TIME ZONE,
    end_date TIMESTAMP WITHOUT TIME ZONE,
    create_date TIMESTAMP WITHOUT TIME ZONE,
    update_date TIMESTAMP WITHOUT TIME ZONE
) WITH (OIDS = FALSE);

-- Table of prizes
CREATE TABLE IF NOT EXISTS ramsevents.prizes (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(300),
    description VARCHAR(1000),
    type VARCHAR(300),
	min_points SMALLINT,
    create_date TIMESTAMP WITHOUT TIME ZONE,
    update_date TIMESTAMP WITHOUT TIME ZONE,

    fk_quarter_id BIGINT NOT NULL REFERENCES ramsevents.quarters(id)
) WITH (OIDS = FALSE);

-- Table of school clubs
CREATE TABLE IF NOT EXISTS ramsevents.clubs (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(300) NOT NULL UNIQUE,
    description VARCHAR(1000),
    create_date TIMESTAMP WITHOUT TIME ZONE,
    update_date TIMESTAMP WITHOUT TIME ZONE,

    fk_leader_id BIGINT NOT NULL REFERENCES ramsevents.students(id)
) WITH (OIDS = FALSE);

-- Table of events
CREATE TABLE IF NOT EXISTS ramsevents.events (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(300) NOT NULL,
    description VARCHAR(1000),
    min_points SMALLINT,
    max_points SMALLINT,
    start_date TIMESTAMP WITHOUT TIME ZONE,
    end_date TIMESTAMP WITHOUT TIME ZONE,
    create_date TIMESTAMP WITHOUT TIME ZONE,
    update_date TIMESTAMP WITHOUT TIME ZONE,

    fk_club_id BIGINT NOT NULL REFERENCES ramsevents.clubs(id),
    fk_quarter_id BIGINT NOT NULL REFERENCES ramsevents.quarters(id)
) WITH (OIDS = FALSE);

-- Table of the relationship between events and students; a record of their participation events and points for each event
CREATE TABLE IF NOT EXISTS ramsevents.event_trackings (
    id BIGINT NOT NULL PRIMARY KEY,
    points_earned SMALLINT,
    participation_date TIMESTAMP WITHOUT TIME ZONE,
    create_date TIMESTAMP WITHOUT TIME ZONE,
    update_date TIMESTAMP WITHOUT TIME ZONE,
    
    fk_event_id BIGINT NOT NULL REFERENCES ramsevents.events(id),
    fk_student_id BIGINT NOT NULL REFERENCES ramsevents.students(id),
	CONSTRAINT student_event_constraint UNIQUE(fk_event_id, fk_student_id)
) WITH (OIDS = FALSE);

-- Table of the relationship between students and prizes, as well as the winner type (top winner or random winner)
CREATE TABLE IF NOT EXISTS ramsevents.point_trackings (
    id BIGINT NOT NULL PRIMARY KEY,
    points_earned SMALLINT,
    is_winner BOOLEAN,
    is_random BOOLEAN,
    create_date TIMESTAMP WITHOUT TIME ZONE,
    update_date TIMESTAMP WITHOUT TIME ZONE,
    
    fk_student_id BIGINT NOT NULL REFERENCES ramsevents.students(id),
    fk_prize_id BIGINT REFERENCES ramsevents.prizes(id),
    fk_quarter_id BIGINT NOT NULL REFERENCES ramsevents.quarters(id)
) WITH (OIDS = FALSE);

-- Sequence for tables
CREATE SEQUENCE IF NOT EXISTS ramsevents.student_sequence START 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS ramsevents.event_sequence START 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS ramsevents.prize_sequence START 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS ramsevents.quarter_sequence START 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS ramsevents.club_sequence START 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS ramsevents.group_sequence START 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS ramsevents.event_tracking_sequence START 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS ramsevents.point_tracking_sequence START 1 INCREMENT BY 1;

commit;


