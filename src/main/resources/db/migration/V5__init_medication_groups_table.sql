create table medication_groups(
    id int primary key auto_increment,
    name varchar(100) not null,
    discount bit
);
alter table medications add column medication_group_id int null;
alter table medications
    add foreign key (medication_group_id) references medication_groups(id);