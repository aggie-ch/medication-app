create table templates(
    id int primary key auto_increment,
    description varchar(100) not null
);
create table template_steps(
    id int primary key auto_increment,
    description varchar(100) not null,
    days_to_deadline int not null,
    template_id int not null,
    foreign key (template_id) references templates(id)
);
alter table medication_groups add column template_id int null;

alter table medication_groups
    add foreign key (template_id) references templates(id);