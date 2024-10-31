/* ramsevents.groups */
INSERT INTO ramsevents.groups (
    id,
    name,
    description,
    access_level,
    create_date,
    update_date
)
VALUES (
    NEXTVAL('ramsevents.group_sequence'),
    'student',
    'Students have basic access',
    'low',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP
);

INSERT INTO ramsevents.groups (
    id,
    name,
    description,
    access_level,
    create_date,
    update_date
)
VALUES (
    NEXTVAL('ramsevents.group_sequence'),
    'club leader',
    'Club leaders have medium access',
    'medium',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP
);

INSERT INTO ramsevents.groups (
    id,
    name,
    description,
    access_level,
    create_date,
    update_date
)
VALUES (
    NEXTVAL('ramsevents.group_sequence'),
    'admin',
    'Admins have max access',
    'high',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP
);

select * from ramsevents.groups;

/* ramsevents.students */
INSERT INTO ramsevents.students (
    id,
    uid,
    student_id,
    first_name,
    middle_name,
    last_name,
    email,
    grade,
    homeroom,
    create_date,
    update_date,

    fk_group_id
)
VALUES (
    NEXTVAL('ramsevents.student_sequence'),
    'e6yga43gyhtyg0mnl33nuo66sjhr',
    1434293,
    'John',
    'H',
    'Hua',
    'john.hua26@ycdsbk12.ca',
    9,
    'K',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

    (SELECT id FROM ramsevents.groups WHERE access_level = 'low')
);

INSERT INTO ramsevents.students (
    id,
    uid,
    student_id,
    first_name,
    middle_name,
    last_name,
    email,
    grade,
    homeroom,
    create_date,
    update_date,

    fk_group_id
)
VALUES (
    NEXTVAL('ramsevents.student_sequence'),
    'pasd6htcf8r7p3uzxlcxex5wj2il',
    1038475,
    'Leo',
    'L.',
    'Ning',
    'leo.ning25@ycdsbk12.ca',
    10,
    'J',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

    (SELECT id FROM ramsevents.groups WHERE access_level = 'medium')
);

INSERT INTO ramsevents.students (
    id,
    uid,
    student_id,
    first_name,
    middle_name,
    last_name,
    email,
    grade,
    homeroom,
    create_date,
    update_date,

    fk_group_id
)
VALUES (
    NEXTVAL('ramsevents.student_sequence'),
    'mbhx82walrqxec6k09xgvs7tdh79',
    1183574,
    'Javier',
    '',
    'Huang',
    'javier.h25@ycdsbk12.ca',
    10,
    'J',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

    (SELECT id FROM ramsevents.groups WHERE access_level = 'high')
);

INSERT INTO ramsevents.students (
    id,
    uid,
    student_id,
    first_name,
    middle_name,
    last_name,
    email,
    grade,
    homeroom,
    create_date,
    update_date,

    fk_group_id
)
VALUES (
    NEXTVAL('ramsevents.student_sequence'),
    'mjd93ex0upvvjxdc0jtehfp4bvfl',
    1295879,
    'Andrew',
    'M.',
    'Long',
    'andrew.long25@ycdsbk12.ca',
    11,
    'M',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

    (SELECT id FROM ramsevents.groups WHERE access_level = 'low')
);


INSERT INTO ramsevents.students (
    id,
    uid,
    student_id,
    first_name,
    middle_name,
    last_name,
    email,
    grade,
    homeroom,
    create_date,
    update_date,

    fk_group_id
)
VALUES (
    NEXTVAL('ramsevents.student_sequence'),
    'l6dj5rwca3h08t5xi2ngnllltttx',
    1147634,
    'Albert',
    '',
    'Lonard',
    'albert.lonard23@ycdsbk12.ca',
    12,
    'A',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

    (SELECT id FROM ramsevents.groups WHERE access_level = 'medium')
);

INSERT INTO ramsevents.students (
    id,
    uid,
    student_id,
    first_name,
    middle_name,
    last_name,
    email,
    grade,
    homeroom,
    create_date,
    update_date,

    fk_group_id
)
VALUES (
    NEXTVAL('ramsevents.student_sequence'),
    'v57r1c1ccmbjtr3w5fj2rs6rq7rf',
    1954834,
    'David',
    '',
    'Ming',
    'david.ming22@ycdsbk12.ca',
    12,
    'L',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

    (SELECT id FROM ramsevents.groups WHERE access_level = 'low')
);

INSERT INTO ramsevents.students (
    id,
    uid,
    student_id,
    first_name,
    middle_name,
    last_name,
    email,
    grade,
    homeroom,
    create_date,
    update_date,

    fk_group_id
)
VALUES (
    NEXTVAL('ramsevents.student_sequence'),
    'hxj7n7i1nhyyk000c6s6rubgtqr1',
    1384858,
    'Ryan',
    '',
    'Kong',
    'ryan.kong26@ycdsbk12.ca',
    9,
    'F',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

    (SELECT id FROM ramsevents.groups WHERE access_level = 'low')
);

INSERT INTO ramsevents.students (
    id,
    uid,
    student_id,
    first_name,
    middle_name,
    last_name,
    email,
    grade,
    homeroom,
    create_date,
    update_date,

    fk_group_id
)
VALUES (
    NEXTVAL('ramsevents.student_sequence'),
    'qv4elz1mkfy0ln96bip8669dnl7s',
    1843748,
    'Ray',
    '',
    'Stone',
    'javredstone123@gmail.com',
    9,
    'F',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

    (SELECT id FROM ramsevents.groups WHERE access_level = 'low')
);

select * from ramsevents.students;

/* ramsevents.quarters */
INSERT INTO ramsevents.quarters (
    id,
    school_year,
    quarter,
	start_date,
	end_date,
    create_date,
    update_date
)
VALUES (
    NEXTVAL('ramsevents.quarter_sequence'),
    '2022-2023',
    1,
	TO_TIMESTAMP('2022-09-01 05:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_TIMESTAMP('2022-11-15 05:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP
);

INSERT INTO ramsevents.quarters (
    id,
    school_year,
    quarter,
	start_date,
	end_date,
    create_date,
    update_date
)
VALUES (
    NEXTVAL('ramsevents.quarter_sequence'),
    '2022-2023',
    2,
	TO_TIMESTAMP('2022-11-16 05:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_TIMESTAMP('2023-01-31 05:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP
);

INSERT INTO ramsevents.quarters (
    id,
    school_year,
    quarter,
	start_date,
	end_date,
    create_date,
    update_date
)
VALUES (
    NEXTVAL('ramsevents.quarter_sequence'),
    '2022-2023',
    3,
	TO_TIMESTAMP('2022-02-01 05:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_TIMESTAMP('2023-04-15 05:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP
);

INSERT INTO ramsevents.quarters (
    id,
    school_year,
    quarter,
	start_date,
	end_date,
    create_date,
    update_date
)
VALUES (
    NEXTVAL('ramsevents.quarter_sequence'),
    '2022-2023',
    4,
	TO_TIMESTAMP('2022-04-16 05:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_TIMESTAMP('2023-06-30 05:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP
);

select * from ramsevents.quarters;

/* ramsevents.prizes */
INSERT INTO ramsevents.prizes (
    id,
    name,
    description,
    type,
	min_points,
    create_date,
    update_date,

    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.prize_sequence'),
    'Host a Schoolwide Event!',
    'Get the chance to host your own imagined schoolwide event!',
    'School',
	40,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.prizes (
    id,
    name,
    description,
    type,
	min_points,
    create_date,
    update_date,

    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.prize_sequence'),
    'Free Cafeteria Meal for One Week!',
    'Get one free week of any 5 cafeteria items!',
    'Food',
	30,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.prizes (
    id,
    name,
    description,
    type,
	min_points,
    create_date,
    update_date,

    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.prize_sequence'),
    'Free Rams Spirit!',
    'Get any school or club spirit item for free! You get to keep it too!',
    'Spirit',
	20,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.prizes (
    id,
    name,
    description,
    type,
	min_points,
    create_date,
    update_date,

    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.prize_sequence'),
    'Free caf fries',
    'Free cafeteria fries for lunch!',
    'Miscellaneous',
	0,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.prizes (
    id,
    name,
    description,
    type,
	min_points,
    create_date,
    update_date,

    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.prize_sequence'),
    'Throw a schoolwide festival',
    'Get the chance to host your own imagined schoolwide festival!',
    'School',
	40,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 2)
);

INSERT INTO ramsevents.prizes (
    id,
    name,
    description,
    type,
	min_points,
    create_date,
    update_date,

    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.prize_sequence'),
    'One dollar Cafeteria Meal for One Month!',
    'Get one month of any 5 cafeteria items, with only one dollar!',
    'Food',
	30,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 2)
);

INSERT INTO ramsevents.prizes (
    id,
    name,
    description,
    type,
	min_points,
    create_date,
    update_date,

    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.prize_sequence'),
    'Free club spirit!',
    'Get your favourite club spiritwear!',
    'Spirit',
	20,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 2)
);

INSERT INTO ramsevents.prizes (
    id,
    name,
    description,
    type,
	min_points,
    create_date,
    update_date,

    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.prize_sequence'),
    'Free caf chicken nuggets',
    'Free cafeteria chicken nuggets for lunch!',
    'Miscellaneous',
	0,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 2)
);

INSERT INTO ramsevents.prizes (
    id,
    name,
    description,
    type,
	min_points,
    create_date,
    update_date,

    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.prize_sequence'),
    'Become the school mayor for a week',
    'Get the chance to be the very mayor of the school for a week!',
    'School',
	40,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 1)
);

INSERT INTO ramsevents.prizes (
    id,
    name,
    description,
    type,
	min_points,
    create_date,
    update_date,

    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.prize_sequence'),
    'Free timbits and meal',
    'Free timbits and cafeteria meal for three days',
    'Food',
	30,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 1)
);

INSERT INTO ramsevents.prizes (
    id,
    name,
    description,
    type,
	min_points,
    create_date,
    update_date,

    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.prize_sequence'),
    'Make your own spiritwear',
    'Make your favourite school spiritwear!',
    'Spirit',
	20,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 1)
);

INSERT INTO ramsevents.prizes (
    id,
    name,
    description,
    type,
	min_points,
    create_date,
    update_date,

    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.prize_sequence'),
    'Free caf cookies',
    'Free cafeteria cookies (5) for lunch!',
    'Miscellaneous',
	0,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 1)
);

select * from ramsevents.prizes;

/* ramsevents.clubs */
INSERT INTO ramsevents.clubs (
    id,
    name,
    description,
    create_date,
    update_date,
    fk_leader_id
)
VALUES (
    NEXTVAL('ramsevents.club_sequence'),
    'Coding Club',
    'A club for coders of any skill!',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
     (SELECT id FROM ramsevents.students WHERE first_name = 'Leo')
);

INSERT INTO ramsevents.clubs (
    id,
    name,
    description,
    create_date,
    update_date,
    fk_leader_id
)
VALUES (
    NEXTVAL('ramsevents.club_sequence'),
    'Mural Club',
    'A club for art people! You can paint the murals in our school!',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	(SELECT id FROM ramsevents.students WHERE first_name = 'Albert')
);

INSERT INTO ramsevents.clubs (
    id,
    name,
    description,
    create_date,
    update_date,
    fk_leader_id
)
VALUES (
    NEXTVAL('ramsevents.club_sequence'),
    'STUCO',
    'Your finest student council!',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	(SELECT id FROM ramsevents.students WHERE first_name = 'Javier')
);

INSERT INTO ramsevents.clubs (
    id,
    name,
    description,
    create_date,
    update_date,
    fk_leader_id
)
VALUES (
    NEXTVAL('ramsevents.club_sequence'),
    'Environmental Council',
    'Your most eco-friendly council!',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	(SELECT id FROM ramsevents.students WHERE first_name = 'Leo')
);

INSERT INTO ramsevents.clubs (
    id,
    name,
    description,
    create_date,
    update_date,
    fk_leader_id
)
VALUES (
    NEXTVAL('ramsevents.club_sequence'),
    'Student Athletic Council',
    'Your best sports council!',
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	(SELECT id FROM ramsevents.students WHERE first_name = 'Javier')
);

select * from ramsevents.clubs;

/* ramsevents.events */
INSERT INTO ramsevents.events (
    id,
    name,
    description,
    min_points,
    max_points,
    start_date,
    end_date,
    create_date,
    update_date,
    
    fk_club_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.event_sequence'),
    'Door Decorating Contest',
    'A door decorating contest for everyone',
    2,
    10,
    TO_TIMESTAMP('2022-12-10 20:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('2022-12-22 21:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

    (SELECT id FROM ramsevents.clubs WHERE name = 'STUCO'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 2)
);


INSERT INTO ramsevents.events (
    id,
    name,
    description,
    min_points,
    max_points,
    start_date,
    end_date,
    create_date,
    update_date,
    
    fk_club_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.event_sequence'),
    'St. Robert Coding Competition',
    'A coding competition hosted by coding club! Once a year! Meet in room 225 to join us!',
    1,
    5,
    TO_TIMESTAMP('2022-12-15 20:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('2022-12-15 21:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

    (SELECT id FROM ramsevents.clubs WHERE name = 'Coding Club'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 2)
);

INSERT INTO ramsevents.events (
    id,
    name,
    description,
    min_points,
    max_points,
    start_date,
    end_date,
    create_date,
    update_date,
    
    fk_club_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.event_sequence'),
    'Mural Painting Contest',
    'A painting competition hosted by mural club! Meet in the art room for more info.',
    1,
    5,
    TO_TIMESTAMP('2022-11-12 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('2022-12-12 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.clubs WHERE name = 'Mural Club'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.events (
    id,
    name,
    description,
    min_points,
    max_points,
    start_date,
    end_date,
    create_date,
    update_date,

    fk_club_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.event_sequence'),
    'Christmas Card Contest',
    'Make a Christmas card for your friends! Join us in the library for submissions.',
    2,
    10,
    TO_TIMESTAMP('2022-12-01 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('2022-12-22 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.clubs WHERE name = 'STUCO'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.events (
    id,
    name,
    description,
    min_points,
    max_points,
    start_date,
    end_date,
    create_date,
    update_date,

    fk_club_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.event_sequence'),
    'Cross Country Practice',
    'Attention all Rams Runners! Cross country is back! The first cross country practice will be on Monday September 12th at 2:45. Please meet in the small gym and be ready for the first run!',
    1,
    5,
    TO_TIMESTAMP('2022-09-08 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('2022-09-12 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.clubs WHERE name = 'Student Athletic Council'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 1)
);

INSERT INTO ramsevents.events (
    id,
    name,
    description,
    min_points,
    max_points,
    start_date,
    end_date,
    create_date,
    update_date,

    fk_club_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.event_sequence'),
    'Golf Team Tryouts',
    'Attention all grades, boys and girls interested in trying out for the St. Robert golf teams. There will be a meeting held this coming Monday September 12 at 8:00am in the small gym',
	1,
    4,
    TO_TIMESTAMP('2022-09-08 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('2022-09-12 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.clubs WHERE name = 'Student Athletic Council'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 1)
);

INSERT INTO ramsevents.events (
    id,
    name,
    description,
    min_points,
    max_points,
    start_date,
    end_date,
    create_date,
    update_date,

    fk_club_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.event_sequence'),
    'Golf Team Tourney',
    'Attention all students, there will be a schoolwide play against many schools in the large gym!',
	3,
    9,
    TO_TIMESTAMP('2023-02-08 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('2023-02-09 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.clubs WHERE name = 'Student Athletic Council'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.events (
    id,
    name,
    description,
    min_points,
    max_points,
    start_date,
    end_date,
    create_date,
    update_date,

    fk_club_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.event_sequence'),
    'Senior Tennis Tryouts',
    'There will be Tryouts for Senior Tennis on Thursday and Friday. The Tryout for Senior Boys Tennis will be after school on Thursday. The tryouts for Senior Girls Tennis will be after school on Friday.',
	1,
    3,
    TO_TIMESTAMP('2023-01-16 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('2023-01-17 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.clubs WHERE name = 'Student Athletic Council'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.events (
    id,
    name,
    description,
    min_points,
    max_points,
    start_date,
    end_date,
    create_date,
    update_date,

    fk_club_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.event_sequence'),
    'Schoolwide Skating Trip',
    'Hey Rams! Our yearly schoolwide skating trip is back! Attend to go to the community center to skate with your friends!',
	1,
    2,
    TO_TIMESTAMP('2023-01-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('2023-01-22 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.clubs WHERE name = 'STUCO'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.events (
    id,
    name,
    description,
    min_points,
    max_points,
    start_date,
    end_date,
    create_date,
    update_date,

    fk_club_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.event_sequence'),
    'STUCO Applications',
    'Who’s excited for the school year? Well, if you are in grade 9, join STUCO and contribute to the school community. Represent your grade, voice your ideas, and plan events to make the kind of memories you want.',
    2,
    10,
    TO_TIMESTAMP('2022-12-01 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('2022-12-22 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.clubs WHERE name = 'STUCO'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 2)
);

INSERT INTO ramsevents.events (
    id,
    name,
    description,
    min_points,
    max_points,
    start_date,
    end_date,
    create_date,
    update_date,

    fk_club_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.event_sequence'),
    'Garbage Picking',
    'Good morning St. Robs! Welcome to day 1 of 12 days of eco, hosted by the Environmental Council! Participate to pick up garbage around the school!',
	2,
    2,
    TO_TIMESTAMP('2023-01-08 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('2022-01-20 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
    
    (SELECT id FROM ramsevents.clubs WHERE name = 'Environmental Council'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

select * from ramsevents.events;

/* ramsevents.event_trackings */
INSERT INTO ramsevents.event_trackings (
    id,
    points_earned,
    participation_date,
    create_date,
    update_date,
    
    fk_event_id,
    fk_student_id
)
VALUES (
    NEXTVAL('ramsevents.event_tracking_sequence'),
    5,
    TO_TIMESTAMP('2022-12-15 20:30:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

    (SELECT id FROM ramsevents.events WHERE name = 'St. Robert Coding Competition'),
    (SELECT id FROM ramsevents.students WHERE first_name = 'Javier')
);

INSERT INTO ramsevents.event_trackings (
    id,
    points_earned,
    participation_date,
    create_date,
    update_date,
    
    fk_event_id,
    fk_student_id
)
VALUES (
    NEXTVAL('ramsevents.event_tracking_sequence'),
    1,
    TO_TIMESTAMP('2022-12-15 20:15:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

    (SELECT id FROM ramsevents.events WHERE name = 'St. Robert Coding Competition'),
    (SELECT id FROM ramsevents.students WHERE first_name = 'David')
);

INSERT INTO ramsevents.event_trackings (
    id,
    points_earned,
    participation_date,
    create_date,
    update_date,
    
    fk_event_id,
    fk_student_id
)
VALUES (
    NEXTVAL('ramsevents.event_tracking_sequence'),
    8,
    TO_TIMESTAMP('2022-12-05 18:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

    (SELECT id FROM ramsevents.events WHERE name = 'Christmas Card Contest'),
    (SELECT id FROM ramsevents.students WHERE first_name = 'Andrew')
);

select * from ramsevents.event_trackings;

/* ramsevents.point_trackings */
INSERT INTO ramsevents.point_trackings (
    id,
    points_earned,
    is_winner,
    is_random,
    create_date,
    update_date,

    fk_prize_id,
    fk_student_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    55,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

	NULL,
    (SELECT id FROM ramsevents.students WHERE first_name = 'Leo'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.point_trackings (
    id,
    points_earned,
    is_winner,
    is_random,
    create_date,
    update_date,

    fk_prize_id,
    fk_student_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    60,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

	NULL,
    (SELECT id FROM ramsevents.students WHERE first_name = 'David'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.point_trackings (
    id,
    points_earned,
    is_winner,
    is_random,
    create_date,
    update_date,

    fk_prize_id,
    fk_student_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    40,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

	NULL,
    (SELECT id FROM ramsevents.students WHERE first_name = 'John'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.point_trackings (
    id,
    points_earned,
    is_winner,
    is_random,
    create_date,
    update_date,

    fk_prize_id,
    fk_student_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    37,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	
	null,
    (SELECT id FROM ramsevents.students WHERE first_name = 'Ray'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.point_trackings (
    id,
    points_earned,
    is_winner,
    is_random,
    create_date,
    update_date,

    fk_prize_id,
    fk_student_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    36,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	
	NULL,
    (SELECT id FROM ramsevents.students WHERE first_name = 'Ryan'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.point_trackings (
    id,
    points_earned,
    is_winner,
    is_random,
    create_date,
    update_date,

    fk_prize_id,
    fk_student_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    37,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	
	NULL,
    (SELECT id FROM ramsevents.students WHERE first_name = 'Andrew'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 3)
);

INSERT INTO ramsevents.point_trackings (
    id,
    points_earned,
    is_winner,
    is_random,
    create_date,
    update_date,

    fk_prize_id,
    fk_student_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    30,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	
	NULL,
    (SELECT id FROM ramsevents.students WHERE first_name = 'David'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 2)
);

INSERT INTO ramsevents.point_trackings (
    id,
    points_earned,
    is_winner,
    is_random,
    create_date,
    update_date,

    fk_prize_id,
    fk_student_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    41,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	
	NULL,
    (SELECT id FROM ramsevents.students WHERE first_name = 'Andrew'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 2)
);

INSERT INTO ramsevents.point_trackings VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    19,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	
    (SELECT id FROM ramsevents.students WHERE first_name = 'John'),
	NULL,
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 2)
);

INSERT INTO ramsevents.point_trackings VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    24,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	
    (SELECT id FROM ramsevents.students WHERE first_name = 'Ray'),
	NULL,
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 2)
);

INSERT INTO ramsevents.point_trackings VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    34,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	
    (SELECT id FROM ramsevents.students WHERE first_name = 'Ryan'),
	NULL,
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 2)
);

INSERT INTO ramsevents.point_trackings VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    3,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	
    (SELECT id FROM ramsevents.students WHERE first_name = 'Leo'),
	NULL,
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 2)
);

INSERT INTO ramsevents.point_trackings (
    id,
    points_earned,
    is_winner,
    is_random,
    create_date,
    update_date,

    fk_prize_id,
    fk_student_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    43,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

	NULL,
    (SELECT id FROM ramsevents.students WHERE first_name = 'Leo'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 1)
);

INSERT INTO ramsevents.point_trackings (
    id,
    points_earned,
    is_winner,
    is_random,
    create_date,
    update_date,

    fk_prize_id,
    fk_student_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    32,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

	NULL,
    (SELECT id FROM ramsevents.students WHERE first_name = 'David'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 1)
);

INSERT INTO ramsevents.point_trackings (
    id,
    points_earned,
    is_winner,
    is_random,
    create_date,
    update_date,

    fk_prize_id,
    fk_student_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    18,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,

	NULL,
    (SELECT id FROM ramsevents.students WHERE first_name = 'John'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 1)
);

INSERT INTO ramsevents.point_trackings (
    id,
    points_earned,
    is_winner,
    is_random,
    create_date,
    update_date,

    fk_prize_id,
    fk_student_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    25,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	
	null,
    (SELECT id FROM ramsevents.students WHERE first_name = 'Ray'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 1)
);

INSERT INTO ramsevents.point_trackings (
    id,
    points_earned,
    is_winner,
    is_random,
    create_date,
    update_date,

    fk_prize_id,
    fk_student_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    3,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	
	NULL,
    (SELECT id FROM ramsevents.students WHERE first_name = 'Ryan'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 1)
);

INSERT INTO ramsevents.point_trackings (
    id,
    points_earned,
    is_winner,
    is_random,
    create_date,
    update_date,

    fk_prize_id,
    fk_student_id,
    fk_quarter_id
)
VALUES (
    NEXTVAL('ramsevents.point_tracking_sequence'),
    15,
    FALSE,
    FALSE,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	TO_CHAR(NOW(), 'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP,
	
	NULL,
    (SELECT id FROM ramsevents.students WHERE first_name = 'Andrew'),
    (SELECT id FROM ramsevents.quarters WHERE school_year = '2022-2023' AND quarter = 1)
);

select * from ramsevents.point_trackings;

COMMIT;