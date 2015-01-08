create database flowdb;
use flowdb;

create table users (
user_id int not null auto_increment,
firstname varchar(40) not null,
lastname varchar(40) not null,
email varchar(255) not null UNIQUE,
login varchar(20) not null UNIQUE,
password varchar(20) not null,
PRIMARY KEY (user_id),
KEY (email)
);

CREATE TABLE user_roles (
user_role_id int(11) NOT NULL AUTO_INCREMENT,
username varchar(45) NOT NULL,
user_role varchar(45) NOT NULL,
PRIMARY KEY (user_role_id),
UNIQUE KEY uni_username_role (user_role,username),
KEY fk_username_idx (username),
CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (email));

create table projects (
project_id int not null auto_increment,
name varchar(100) not null,
description text,
PRIMARY KEY (project_id)
);

create table projects_users (
    `project_id` int NOT NULL,
    `user_id` int NOT NULL,
    PRIMARY KEY (`project_id`, `user_id`),
    INDEX `FK_user` (`user_id`),
    CONSTRAINT `fk_project` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`),
    CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

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

create table issues_users (
    `issue_id` int NOT NULL,
    `user_id` int NOT NULL,
    PRIMARY KEY (`issue_id`, `user_id`),
    INDEX `fk_user` (`user_id`),
    CONSTRAINT `fk_issue` FOREIGN KEY (`issue_id`) REFERENCES `issues` (`issue_id`),
    CONSTRAINT `fk_userx` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

insert into issues_users (issue_id, user_id)
values(1, 1);
insert into issues_users (issue_id, user_id)
values(2, 2);
insert into issues_users (issue_id, user_id)
values(3, 3);
insert into issues_users (issue_id, user_id)
values(4, 2);
insert into issues_users (issue_id, user_id)
values(5, 3);
insert into issues_users (issue_id, user_id)
values(6, 2);
insert into issues_users (issue_id, user_id)
values(7, 1);
insert into issues_users (issue_id, user_id)
values(8, 1);
insert into issues_users (issue_id, user_id)
values(9, 1);
insert into issues_users (issue_id, user_id)
values(10, 1);

//triggers for date
delimiter//
CREATE TRIGGER `issues_insert` BEFORE INSERT ON `issues`
FOR EACH ROW BEGIN
        -- Set the creation date
    SET new.creation_date = now();

        -- Set the udpate date
    Set new.modification_date = now();
END//
delimiter;

delimiter//
CREATE TRIGGER `issues_update` BEFORE UPDATE ON `issues`
FOR EACH ROW BEGIN
        -- Set the udpate date
    Set new.modification_date = now();
END//
delimiter;

insert into projects(name, description) 
values('TestProject1', 'This is a test project');
insert into projects(name, description) 
values('TestProject2', 'This is a test project');

insert into users(firstname, lastname, email, login, password) 
values('Kurt', 'Cobain', 'KCobain@test.com', 'kurt', '111111');
insert into users(firstname, lastname, email, login, password) 
values('Egor', 'Letov', 'Egorka@test.com', 'grob', '111111');
insert into users(firstname, lastname, email, login, password) 
values('Victor', 'Coy', 'Coy@test.com', 'coy', '111111');

insert into projects_users (project_id, user_id)
values(1, 2);
insert into projects_users (project_id, user_id)
values(2, 2);
insert into projects_users (project_id, user_id)
values(1, 1);
insert into projects_users (project_id, user_id)
values(2, 3);

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
values('issue4', 'description', 'TASK', 'CLOSED', 'LOW', '2', '2', '1');
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


