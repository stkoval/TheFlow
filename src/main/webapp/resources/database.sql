DROP DATABASE IF EXISTS flowdb;
create database flowdb;
use flowdb;

DROP TABLE IF EXISTS companies;
create table companies (
company_id int not null auto_increment,
company_name varchar(100) not null,
key(company_name),
primary key (company_id)
);

insert into companies (company_name)
values('DemoCompany1');

DROP TABLE IF EXISTS users;
create table users (
company_id int(11) not null,
user_id int(11) not null auto_increment,
firstname varchar(40) not null,
lastname varchar(40) not null,
email varchar(255) not null UNIQUE,
password varchar(60) not null,
enabled TINYINT NOT NULL DEFAULT 1,
PRIMARY KEY (user_id),
FOREIGN KEY (company_id) REFERENCES companies(company_id),
KEY (email)
);

insert into users(company_id, firstname, lastname, email, password) 
values(1, 'Kurt', 'Cobain', 'KCobain@test.com', '111111');
insert into users(company_id, firstname, lastname, email, password) 
values(1, 'Egor', 'Letov', 'Egorka@test.com', '111111');
insert into users(company_id, firstname, lastname, email, password) 
values(1, 'Victor', 'Coy', 'Coy@test.com', '111111');

DROP TABLE IF EXISTS user_roles;
CREATE TABLE user_roles (
user_role_id int(11) NOT NULL AUTO_INCREMENT,
username varchar(45) NOT NULL,
user_role varchar(45) NOT NULL,
PRIMARY KEY (user_role_id),
UNIQUE KEY uni_username_role (user_role,username),
KEY fk_username_idx (username),
CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (email));

DROP TABLE IF EXISTS projects;
create table projects (
project_id int not null auto_increment,
name varchar(100) not null,
company_id int(11) not null,
description text,
start_date datetime null,
release_date datetime null,
active TINYINT NOT NULL DEFAULT 1,
PRIMARY KEY (project_id)
);

insert into projects(name, company_id, description, start_date, release_date) 
values('TestProject1', 1, 'This is a test project', '11.11.15', '12.12.16');
insert into projects(name, company_id, description, start_date, release_date) 
values('TestProject2', 1, 'This is a test project', '11.11.15', '12.12.16');

DROP TABLE IF EXISTS issues;
create table issues (
issue_id int not null auto_increment,
title varchar(40) not null UNIQUE,
description text,
type varchar(15),
status varchar(15),
priority varchar(15),
assignee_id int null,
creator_id int not null,
project_id int not null,
estimated_time varchar(10),
logged_time varchar(10),
creation_date datetime null,
modification_date datetime null,
PRIMARY KEY (issue_id),
FOREIGN KEY (creator_id) REFERENCES users(user_id),
FOREIGN KEY (project_id) REFERENCES projects(project_id)
);

drop trigger if exists issues_insert;
delimiter//
CREATE TRIGGER `issues_insert` BEFORE INSERT ON `issues`
FOR EACH ROW BEGIN
        -- Set the creation date
    SET new.creation_date = now();

        -- Set the udpate date
    Set new.modification_date = now();
END//
delimiter;

drop trigger if exists issues_update;
delimiter//
CREATE TRIGGER `issues_update` BEFORE UPDATE ON `issues`
FOR EACH ROW BEGIN
        -- Set the udpate date
    Set new.modification_date = now();
END//
delimiter;

insert into user_roles(username, user_role)
values('KCobain@test.com', 'ROLE_USER');
insert into user_roles(username, user_role)
values('Egorka@test.com', 'ROLE_ADMIN');
insert into user_roles(username, user_role)
values('Coy@test.com', 'ROLE_USER');

insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id)
values('issue1', 'description', 'TASK', 'NEW', 'HIGH', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id)
values('issue2', 'description', 'TASK', 'NEW', 'MEDIUM', '2', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id)
values('issue3', 'description', 'TASK', 'NEW', 'LOW', '3', '1', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id)
values('issue4', 'description', 'TASK', 'RESOLVED', 'LOW', '2', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id)
values('issue5', 'description', 'TASK', 'INPROGRESS', 'MEDIUM', '3', '2', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id)
values('issue6', 'description', 'TASK', 'REVIEW', 'LOW', '2', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id)
values('issue7', 'description', 'BUG', 'NEW', 'HIGH', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id)
values('issue8', 'description', 'BUG', 'NEW', 'MEDIUM', '1', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id)
values('issue9', 'description', 'BUG', 'REVIEW', 'LOW', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id)
values('issue10', 'description', 'BUG', 'INPROGRESS', 'MEDIUM', '1', '1', '1');


