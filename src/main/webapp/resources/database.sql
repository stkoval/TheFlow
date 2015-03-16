DROP DATABASE IF EXISTS flowdb;
create database flowdb;
use flowdb;

DROP TABLE IF EXISTS companies;
create table companies (
company_id int not null auto_increment,
company_name varchar(100) not null,
company_alias varchar(16) not null,
key(company_alias),
primary key (company_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into companies (company_name, company_alias)
values('Microsoft', 'microsoft');
insert into companies (company_name, company_alias)
values('Apple', 'apple');

DROP TABLE IF EXISTS users;
create table users (
user_id int(11) not null auto_increment,
firstname varchar(40) not null,
lastname varchar(40) not null,
email varchar(255) not null,
password varchar(60) not null,
enabled TINYINT NOT NULL DEFAULT 1,
PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS users_companies;
create table users_companies (
user_company_id int(11) not null auto_increment,
user_id int(11) not null,
company_id int not null,
user_role varchar(20) not null,
PRIMARY KEY (user_company_id),
KEY `fk_user` (`user_id`),
KEY `fk_group` (`company_id`),
CONSTRAINT `fk_company` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`),
CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into users(firstname, lastname, email, password, enabled) 
values('Stephen', 'Elop', 'Stephen@microsoft.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);
insert into users(firstname, lastname, email, password, enabled) 
values('Bill', 'Gates', 'BGates@microsoft.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);
insert into users(firstname, lastname, email, password, enabled) 
values('Mark', 'Pen', 'Mark@microsoft.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);
insert into users(firstname, lastname, email, password, enabled) 
values('John', 'Smith', 'jsmith@test.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);
insert into users(firstname, lastname, email, password, enabled) 
values('Steve', 'Jobs', 'SJobs@apple.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);
insert into users(firstname, lastname, email, password, enabled) 
values('Steve', 'Voznik', 'SVoznik@apple.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);

insert into users_companies(user_id, company_id, user_role) 
values(1, 1, 'User');
insert into users_companies(user_id, company_id, user_role) 
values(2, 1, 'Admin');
insert into users_companies(user_id, company_id, user_role) 
values(3, 1, 'User');
insert into users_companies(user_id, company_id, user_role) 
values(4, 1, 'User');
insert into users_companies(user_id, company_id, user_role) 
values(5, 2, 'Admin');
insert into users_companies(user_id, company_id, user_role) 
values(6, 2, 'User');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into projects(name, company_id, description, start_date, release_date) 
values('Explorer', '1', 'This is a test project', '11.11.15', '12.12.16');
insert into projects(name, company_id, description, start_date, release_date) 
values('Windows11', '1', 'This is a test project', '11.11.15', '12.12.16');
insert into projects(name, company_id, description, start_date, release_date) 
values('iTunes', '2', 'This is a test project', '11.11.15', '12.12.16');
insert into projects(name, company_id, description, start_date, release_date) 
values('iSearch', '2', 'This is a test project', '11.11.15', '12.12.16');

DROP TABLE IF EXISTS issues;
create table issues (
issue_id int not null auto_increment,
title varchar(40) not null,
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
company_id int not null,
PRIMARY KEY (issue_id),
FOREIGN KEY (creator_id) REFERENCES users(user_id),
FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue1', 'description', 'TASK', 'NEW', 'HIGH', '4', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue2', 'description', 'TASK', 'NEW', 'MEDIUM', '4', '1', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue3', 'description', 'TASK', 'NEW', 'LOW', '4', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue4', 'description', 'TASK', 'RESOLVED', 'LOW', '4', '2', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue5', 'description', 'TASK', 'INPROGRESS', 'MEDIUM', '3', '2', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue6', 'description', 'TASK', 'REVIEW', 'LOW', '2', '1', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue7', 'description', 'BUG', 'NEW', 'HIGH', '4', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue8', 'description', 'BUG', 'NEW', 'MEDIUM', '1', '2', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue9', 'description', 'BUG', 'REVIEW', 'LOW', '4', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue10', 'description', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('music not playing', 'description', 'BUG', 'INPROGRESS', 'MEDIUM', '6', '5', '3', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue10', 'description', 'TASK', 'INPROGRESS', 'MEDIUM', '6', '5', '3', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue10', 'description', 'BUG', 'INPROGRESS', 'MEDIUM', '6', '5', '3', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue10', 'description', 'TASK', 'INPROGRESS', 'MEDIUM', '6', '5', '3', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue10', 'description', 'TASK', 'INPROGRESS', 'MEDIUM', '6', '5', '4', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue10', 'description', 'BUG', 'INPROGRESS', 'MEDIUM', '6', '5', '4', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue10', 'description', 'TASK', 'INPROGRESS', 'MEDIUM', '6', '5', '4', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue10', 'description', 'BUG', 'INPROGRESS', 'MEDIUM', '6', '5', '4', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue10', 'description', 'TASK', 'INPROGRESS', 'MEDIUM', '6', '5', '4', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('issue10', 'description', 'BUG', 'INPROGRESS', 'MEDIUM', '6', '5', '4', '2');


