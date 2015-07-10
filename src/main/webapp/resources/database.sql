DROP DATABASE IF EXISTS flowdb;
create database flowdb;
use flowdb;

DROP TABLE IF EXISTS users;
create table users (
user_id int(11) not null auto_increment,
firstname varchar(40) not null,
lastname varchar(40) not null,
email varchar(255) not null,
password varchar(60) not null,
enabled TINYINT NOT NULL DEFAULT 1,
picture blob(3145728),
PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS companies;
create table companies (
company_id int not null auto_increment,
company_name varchar(100) not null,
company_alias varchar(16) not null,
creator_id int(11) not null,
key(company_alias),
CONSTRAINT `fk_creator` FOREIGN KEY (`creator_id`) REFERENCES `users` (`user_id`),
primary key (company_id)
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
values('Bill', 'Gates', 'bgates@microsoft.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);
insert into users(firstname, lastname, email, password, enabled) 
values('Patrick', 'Ryder', 'pryder@microsoft.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);
insert into users(firstname, lastname, email, password, enabled) 
values('Mario', 'Mucalo', 'mario@microsoft.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);
insert into users(firstname, lastname, email, password, enabled) 
values('John', 'Smith', 'jsmith@test.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);
insert into users(firstname, lastname, email, password, enabled) 
values('Steve', 'Jobs', 'sjobs@apple.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);
insert into users(firstname, lastname, email, password, enabled) 
values('Lisa', 'Bettany', 'lbettany@apple.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);
insert into users(firstname, lastname, email, password, enabled) 
values('Ted', 'Cohn', 'tcohn@apple.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);
insert into users(firstname, lastname, email, password, enabled) 
values('Adam', 'Johnson', 'johnson@apple.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);
insert into users(firstname, lastname, email, password, enabled) 
values('Larry', 'Page', 'lpage@google.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);
insert into users(firstname, lastname, email, password, enabled) 
values('Sergey', 'Brin', 'brin@google.com', '$2a$10$66SEMN4SxYOiyi9Q4Digi.RnAKeB5thVKG9ZObUpC0E/AejIE4qja', 1);

insert into companies (company_name, company_alias, creator_id)
values('Microsoft', 'microsoft', 1);
insert into companies (company_name, company_alias, creator_id)
values('Apple', 'apple', 5);
insert into companies (company_name, company_alias, creator_id)
values('Google', 'google', 9);
insert into companies (company_name, company_alias, creator_id)
values('Hooli', 'hooli', 4);

insert into users_companies(user_id, company_id, user_role) 
values(1, 1, 'Admin');
insert into users_companies(user_id, company_id, user_role) 
values(2, 1, 'User');
insert into users_companies(user_id, company_id, user_role) 
values(3, 1, 'User');
insert into users_companies(user_id, company_id, user_role) 
values(4, 1, 'User');
insert into users_companies(user_id, company_id, user_role) 
values(5, 2, 'Admin');
insert into users_companies(user_id, company_id, user_role) 
values(6, 2, 'User');
insert into users_companies(user_id, company_id, user_role) 
values(7, 2, 'User');
insert into users_companies(user_id, company_id, user_role) 
values(8, 2, 'User');
insert into users_companies(user_id, company_id, user_role) 
values(9, 3, 'Admin');
insert into users_companies(user_id, company_id, user_role) 
values(10, 3, 'User');
insert into users_companies(user_id, company_id, user_role) 
values(4, 4, 'Admin');
insert into users_companies(user_id, company_id, user_role) 
values(4, 2, 'User');
insert into users_companies(user_id, company_id, user_role) 
values(4, 3, 'User');

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
values('Internet Explorer 12', '1', 'Develop new browser', '11.11.15', '12.12.16');
insert into projects(name, company_id, description, start_date, release_date) 
values('Windows 12', '1', 'Develop new OS', '11.11.15', '12.12.16');
insert into projects(name, company_id, description, start_date, release_date) 
values('iOS 10 kernel', '2', 'Develop new OS', '11.11.15', '12.12.16');
insert into projects(name, company_id, description, start_date, release_date) 
values('Gmail', '3', 'Upgrade for google mail', '11.11.15', '12.12.16');
insert into projects(name, company_id, description, start_date, release_date) 
values('Demo project 1', '4', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '11.11.15', '12.12.16');
insert into projects(name, company_id, description, start_date, release_date) 
values('Demo project 2', '4', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '11.11.15', '12.12.16');
insert into projects(name, company_id, description, start_date, release_date) 
values('Demo project 3', '4', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '11.11.15', '12.12.16');

DROP TABLE IF EXISTS issues;
create table issues (
issue_id int not null auto_increment,
title varchar(50) not null,
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

DROP TABLE IF EXISTS issue_attachment;
CREATE TABLE issue_attachment (
attachment_id int not null auto_increment,
filename varchar(128) DEFAULT NULL,
file_data blob(2097152),
content_type varchar(128) not null,
issue_id int not null,
PRIMARY KEY (attachment_id),
FOREIGN KEY (issue_id) REFERENCES issues(issue_id) ON UPDATE CASCADE ON DELETE CASCADE
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
values('Search engine', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'HIGH', '2', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Skype integration', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'MEDIUM', '3', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Tool bar', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'LOW', '4', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Some extension1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'CLOSED', 'LOW', '2', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Some extension2', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'INPROGRESS', 'MEDIUM', '3', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Some problem1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'REVIEW', 'LOW', '4', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Some extension3', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'HIGH', '2', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Some problem2', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'NEW', 'MEDIUM', '3', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Some problem3', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'REVIEW', 'LOW', '4', '1', '1', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Some extension4', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'INPROGRESS', 'MEDIUM', '3', '1', '1', '1');

insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Windows guide', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'HIGH', '2', '1', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Gadget1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'MEDIUM', '3', '1', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Gadget2', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'LOW', '4', '1', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Task manager', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'CLOSED', 'LOW', '2', '1', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Gadget3', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'INPROGRESS', 'MEDIUM', '3', '1', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Some problem1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'REVIEW', 'LOW', '4', '1', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Some problem2', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'NEW', 'HIGH', '2', '1', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Some problem3', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'NEW', 'MEDIUM', '3', '1', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Some problem4', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'REVIEW', 'LOW', '4', '1', '2', '1');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Some feature', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'INPROGRESS', 'MEDIUM', '3', '1', '2', '1');

insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'HIGH', '6', '5', '3', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 2', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'MEDIUM', '7', '5', '3', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 3', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'LOW', '8', '5', '3', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 4', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'CLOSED', 'LOW', '6', '5', '3', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 5', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'INPROGRESS', 'MEDIUM', '7', '5', '3', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug 1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '7', '5', '3', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug 2', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '7', '5', '3', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug 3', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '7', '5', '3', '2');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug 4', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '7', '5', '3', '2');

insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'HIGH', '10', '9', '4', '3');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 2', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'MEDIUM', '10', '9', '4', '3');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 3', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'LOW', '10', '9', '4', '3');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 4', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'CLOSED', 'LOW', '10', '9', '4', '3');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 5', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'INPROGRESS', 'MEDIUM', '10', '9', '4', '3');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '10', '9', '4', '3');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug2', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '10', '9', '4', '3');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug3', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '10', '9', '4', '3');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug4', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '10', '9', '4', '3');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug5', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '10', '9', '4', '3');

insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'HIGH', '4', '4', '5', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 2', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'MEDIUM', '4', '4', '5', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 3', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'LOW', '4', '4', '5', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 4', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'CLOSED', 'LOW', '4', '4', '5', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 5', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'INPROGRESS', 'MEDIUM', '4', '4', '5', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '5', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug2', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '5', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug3', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '5', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug4', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '5', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug5', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '5', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 6', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'HIGH', '4', '4', '6', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 7', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'MEDIUM', '4', '4', '6', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 8', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'LOW', '4', '4', '6', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 9', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'CLOSED', 'LOW', '4', '4', '6', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 10', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'INPROGRESS', 'MEDIUM', '4', '4', '6', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug10', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '6', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug11', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '6', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug12', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '6', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug13', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '6', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug14', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '6', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 11', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'HIGH', '4', '4', '7', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 12', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'MEDIUM', '4', '4', '7', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 13', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'NEW', 'LOW', '4', '4', '7', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 14', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'CLOSED', 'LOW', '4', '4', '7', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Feature 15', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'TASK', 'INPROGRESS', 'MEDIUM', '4', '4', '7', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug15', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '7', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug16', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '7', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug17', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '7', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug18', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '7', '4');
insert into issues (title, description, type, status, priority, assignee_id, creator_id, project_id, company_id)
values('Bug19', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 'BUG', 'INPROGRESS', 'MEDIUM', '4', '4', '7', '4');


