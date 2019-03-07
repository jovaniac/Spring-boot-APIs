/* Creating app_role table */ 
CREATE TABLE `app_role` (
  `id` bigint(20) NOT NULL,
  `role_name` varchar(45) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `updated_on` timestamp NULL DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `created_on` timestamp NULL DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `active` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/* Creating app_user table */ 
CREATE TABLE `app_user` (
  `id` bigint(20) NOT NULL,
  `user_name` varchar(25) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `active` tinyint(4) DEFAULT '0',
  `created_on` timestamp NULL DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `updated_on` timestamp NULL DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `decoded_password` varchar(45) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idapp_user_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/* Creating user_role table */ 
CREATE TABLE `user_role` (
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  KEY `user_id_idx` (`user_id`),
  KEY `role_id_idx` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `app_role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


/* Creating contact_us table */ 
CREATE TABLE `contact_us` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone_number` varchar(12) DEFAULT NULL,
  `subject` varchar(50) DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  `created_on` timestamp NULL DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `updated_on` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` bigint(20) DEFAULT NULL,
  `resolved` tinyint(4) DEFAULT NULL,
  `resolved_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

/* Creating project_status table */ 
CREATE TABLE `project_status` (
  `status_id` int(11) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/* Creating projects table */ 
CREATE TABLE `projects` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(100) DEFAULT NULL,
  `project_summary` varchar(500) DEFAULT NULL,
  `due_date` timestamp NULL DEFAULT NULL,
  `estimated_cost` decimal(11,2) DEFAULT NULL,
  `status_id` int(2) NOT NULL DEFAULT '1',
  `created_on` timestamp NULL DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `updated_on` timestamp NULL DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `lastupdated_by` varchar(45) DEFAULT NULL,
  `required_skills` varchar(500) DEFAULT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`project_id`),
  KEY `FKj8mq95eti9ejbcnroqkpq2qph` (`status_id`),
  CONSTRAINT `FKj8mq95eti9ejbcnroqkpq2qph` FOREIGN KEY (`status_id`) REFERENCES `project_status` (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;




/* Below Section insert data into tables  */
INSERT INTO `my_database`.`app_role` 
(`id`, `role_name`, `description`, `updated_on`, `updated_by`, `created_on`, `created_by`, `active`)
VALUES
('1','ROLE_ADMIN','Administrator','2018-04-21 00:00:00','1','2018-04-21 00:00:00',NULL,'1'),
('2','ROLE_GUEST','View only User','2018-04-21 00:00:00','1','2018-04-21 00:00:00',NULL,'1');


INSERT INTO `my_database`.`app_user`
(`id`, `user_name`, `password`, `first_name`, `last_name`, `active`, `created_on`, `created_by`, `updated_on`, `updated_by`, `decoded_password`, `email`, `provider`, `image_url`, `provider_id`)
VALUES
('1','test','$2a$04$ez0RVaTwx22x.50ZwX8Pw.BeuaKknuYfzE4bxSJf7.Wa2h8C2XbHa','Kai','He','1','2018-04-21 00:00:00',NULL,'2018-04-21 00:00:00','1','12345','kaihe517@gmail.com','google','https://lh4.googleusercontent.com/-ceBL7o1rNnA/AAAAAAAAAAI/AAAAAAAAABU/bxUkBm80KVA/photo.jpg',''),
('2','test1','$2a$04$ez0RVaTwx22x.50ZwX8Pw.BeuaKknuYfzE4bxSJf7.Wa2h8C2XbHa','Smith','John','1','2018-04-21 00:00:00',NULL,'2018-04-21 00:00:00','1',NULL,'smithjhon@gmail.com','local',NULL,'');


INSERT INTO `my_database`.`user_role`
(`user_id`, `role_id`)
VALUES
('1','1'),
('2','2');


INSERT INTO `my_database`.`project_status`
(`status_id`, `description`)
VALUES
('1','Pending'),
('2','Accepted'),
('3','Started'),
('4','Completed'),
('5','Returned'),
('6','Deleted');


INSERT INTO `my_database`.`projects`
(`project_id`, `project_name`, `project_summary`, `due_date`, `estimated_cost`, `status_id`, `created_on`, `created_by`, `updated_on`, `updated_by`, `lastupdated_by`, `required_skills`, `isactive`)
VALUES
('1','Web Design Project','Front end work','2017-12-29 00:00:00','1000.00','1','2017-12-12 12:01:45',NULL,'2017-12-12 12:01:45',NULL,NULL,'HMTL5, CSS3, Javascript','1'),
('2','Restfull API implementation','Java back-end project','2017-12-20 00:00:00','1000.00','1','2017-12-11 19:00:00',NULL,'2018-04-24 00:00:00',NULL,'1','Java/, Spring, Hibernate','1'),
('3','Angular Project #3','Implement an cellphone app for online shopping site','2017-12-01 00:00:00','10000.00','1','2017-12-12 16:54:25',NULL,'2017-12-20 00:00:00',NULL,'testUser1','Angular, Spring','1');





