drop table if exists medication_events;
create table medication_events(
    id int primary key auto_increment,
    medication_id int,
    occurrence datetime,
    name varchar(30)
);