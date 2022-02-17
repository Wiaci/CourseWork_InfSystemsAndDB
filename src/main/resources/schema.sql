create table if not exists house (
    id int primary key,
    name varchar(50) not null
);

create table if not exists tool_type (
    id int primary key,
    name varchar(15) not null
);

create table if not exists task_type (
    id int primary key,
    name varchar(50) not null,
    rate float not null check (rate >= 0),
    tool_type_id int references tool_type
        on delete cascade on update cascade
);

create table if not exists citizen (
    id int primary key,
    name varchar(30) not null,
    short_name varchar(10) not null,
    house_id int not null references house
        on delete cascade on update cascade,
    money float not null
);

create table if not exists status (
    id int primary key,
    name varchar(20) not null
);

create table if not exists task (
    id serial primary key,
    task_type_id int not null  references task_type
        on delete cascade on update cascade,
    giver_id int not null references citizen
        on delete cascade on update cascade,
    house_id int not null references house
        on delete cascade on update cascade,
    status_id int not null references status
        on delete cascade on update cascade,
    wage float not null,
    comment text
);

create table if not exists statuette (
    id int primary key,
    citizen_id int not null references citizen
        on delete cascade on update cascade,
    cost float not null check (cost > 0),
    is_bought boolean not null
);

create table if not exists garden (
    id int primary key,
    house_id int not null references house
        on delete cascade on update cascade
);

create table if not exists garden_workers (
    garden_id int not null references garden
        on delete cascade on update cascade,
    worker_id int not null references citizen
        on delete cascade on update cascade,
    primary key(worker_id, garden_id)
);

create table if not exists tool (
    id int primary key,
    type_id int not null references tool_type
        on delete cascade on update cascade,
    is_busy boolean not null,
    holder_id int references citizen
);


insert into house values (1, 'Olivia and June House'),
                         (2, 'Thomas Edison House'),
                         (3, 'House of Jeremiah'),
                         (4, 'Chuck and Vera House'),
                         (5, 'The Hensons House'),
                         (6, 'Ma Gingers shop')
                         on conflict do nothing;
insert into tool_type values (1, 'Hoe'),
                             (2, 'Shovel'),
                             (3, 'Cleaning set'),
                             (4, 'Basket')
                             on conflict do nothing;
insert into task_type values (1, 'Look after children', 1.3, null),
                             (2, 'Clean in the house', 0.4, 3),
                             (3, 'Dig up the garden', 0.9, 2),
                             (4, 'Cultivate the garden', 0.7, 1),
                             (5, 'Help citizen working on the garden', 1.0, null),
                             (6, 'Get the crop in the garden', 0.4, 4)
                             on conflict do nothing;
insert into citizen values (1, 'Thomas Edison Jr.', 'tom', 2, 15),
                           (2, 'Gloria', 'gloria', 3, 17),
                           (3, 'Vera', 'vera', 4, 20),
                           (4, 'Thomas Edison', 'edison', 2, 30),
                           (5, 'Bill Henson', 'bill', 5, 5),
                           (6, 'Liz Henson', 'liz', 5, 7),
                           (7, 'Chuck', 'chuck', 4, 25),
                           (8, 'Ma Ginger', 'ginger', 6, 30),
                           (9, 'Mrs. Henson', 'mrshenson', 5, 19),
                           (10, 'Martha', 'martha', 3, 15),
                           (11, 'Olivia', 'olivia', 1, 12),
                           (12, 'Mr. Henson', 'mrhenson', 5, 24),
                           (13, 'June', 'june', 1, 8),
                           (14, 'Grace', 'grace', 3, 0)
                           on conflict do nothing;
insert into status values (1, 'new'),
                          (2, 'executing'),
                          (3, 'waits for check')
                          on conflict do nothing;
insert into statuette values (1, 1, 17, false), (2, 2, 6, false), (3, 3, 4, false),
                             (4, 4, 13, false), (5, 5, 11, false), (6, 6, 10, false),
                             (7, 7, 5, false), (8, 8, 9, false), (9, 9, 11, false),
                             (10, 10, 6, false), (11, 11, 6, false), (12, 12, 10, false),
                             (13, 13, 4, false)
                             on conflict do nothing;
insert into garden values (1, 5), (2, 6), (3, 1) on conflict do nothing;
insert into garden_workers values (1, 2), (1, 5), (1, 6), (1, 9), (1, 12),
                                  (1, 10), (2, 8), (2, 11), (2, 9), (3, 11),
                                  (3, 13), (3, 2), (3, 10)
                                  on conflict do nothing;
insert into tool values (1, 3, false, null), (2, 3, false, null), (3, 3, false, null),
                        (4, 2, false, null), (5, 2, false, null), (6, 2, false, null),
                        (7, 2, false, null), (8, 1, false, null), (9, 1, false, null),
                        (10, 4, false, null), (11, 4, false, null), (12, 4, false, null)
                        on conflict do nothing;

