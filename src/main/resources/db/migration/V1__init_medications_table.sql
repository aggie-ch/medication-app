drop table if exists medications;
create table medications(
    id int primary key auto_increment,
    name varchar(100) not null,
    discount bit
);