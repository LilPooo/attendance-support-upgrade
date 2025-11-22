-- attendance_support_upgrade.classroom definition

CREATE TABLE `classroom` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `course_id` varchar(255) DEFAULT NULL,
  `end_roll_call_time` time(6) DEFAULT NULL,
  `end_time_learn` time(6) DEFAULT NULL,
  `end_week` int NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start_roll_call_time` time(6) DEFAULT NULL,
  `start_time_learn` time(6) DEFAULT NULL,
  `start_week` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Save classroom Information';


-- attendance_support_upgrade.conversation definition

CREATE TABLE `conversation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `is_group` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- attendance_support_upgrade.permission definition

CREATE TABLE `permission` (
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- attendance_support_upgrade.`role` definition

CREATE TABLE `role` (
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- attendance_support_upgrade.`user` definition

CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `device_id` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` int NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- attendance_support_upgrade.check_in definition

CREATE TABLE `check_in` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `latitude_user` double DEFAULT NULL,
  `longitude_user` double DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `time` datetime(6) DEFAULT NULL,
  `classroom_id` bigint DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc22yg4tq5qyxsxjllqfuue7hb` (`classroom_id`),
  KEY `idx_user_classroom` (`user_id`,`classroom_id`),
  CONSTRAINT `FKc22yg4tq5qyxsxjllqfuue7hb` FOREIGN KEY (`classroom_id`) REFERENCES `classroom` (`id`),
  CONSTRAINT `FKev5gubilot6y4q9w7kc4cl2ln` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- attendance_support_upgrade.classroom_days_of_week definition

CREATE TABLE `classroom_days_of_week` (
  `classroom_id` bigint NOT NULL,
  `days_of_week` enum('FRIDAY','MONDAY','SATURDAY','SUNDAY','THURSDAY','TUESDAY','WEDNESDAY') DEFAULT NULL,
  KEY `FK3oh48v73cml48wuwxp22fpfjk` (`classroom_id`),
  CONSTRAINT `FK3oh48v73cml48wuwxp22fpfjk` FOREIGN KEY (`classroom_id`) REFERENCES `classroom` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- attendance_support_upgrade.conversation_participant definition

CREATE TABLE `conversation_participant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `joined_at` datetime(6) NOT NULL,
  `role` enum('MEMBER','ADMIN') DEFAULT NULL,
  `conversation_id` bigint DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK93dv599s56uqs8xslhdp3arya` (`conversation_id`),
  KEY `FK2cawnm9pjaohdn11jay39vpub` (`user_id`),
  CONSTRAINT `FK2cawnm9pjaohdn11jay39vpub` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK93dv599s56uqs8xslhdp3arya` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- attendance_support_upgrade.message definition

CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `read_at` datetime(6) DEFAULT NULL,
  `status` enum('SENT','DELIVERED','SEEN','FAILED') DEFAULT NULL,
  `timestamp` datetime(6) NOT NULL,
  `type` enum('TEXT','IMAGE','FILE') DEFAULT NULL,
  `conversation_id` bigint DEFAULT NULL,
  `sender_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6yskk3hxw5sklwgi25y6d5u1l` (`conversation_id`),
  KEY `FKcnj2qaf5yc36v2f90jw2ipl9b` (`sender_id`),
  CONSTRAINT `FK6yskk3hxw5sklwgi25y6d5u1l` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`id`),
  CONSTRAINT `FKcnj2qaf5yc36v2f90jw2ipl9b` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- attendance_support_upgrade.role_permissions definition

CREATE TABLE `role_permissions` (
  `role_name` varchar(255) NOT NULL,
  `permissions_name` varchar(255) NOT NULL,
  PRIMARY KEY (`role_name`,`permissions_name`),
  KEY `FKf5aljih4mxtdgalvr7xvngfn1` (`permissions_name`),
  CONSTRAINT `FKcppvu8fk24eqqn6q4hws7ajux` FOREIGN KEY (`role_name`) REFERENCES `role` (`name`),
  CONSTRAINT `FKf5aljih4mxtdgalvr7xvngfn1` FOREIGN KEY (`permissions_name`) REFERENCES `permission` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- attendance_support_upgrade.user_classroom definition

CREATE TABLE `user_classroom` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `classroom_id` bigint DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjn07s48mvyyiayhfd7ina9q7j` (`classroom_id`),
  KEY `FKkkk8ahbo86fmlc5p30bmbaf7p` (`user_id`),
  CONSTRAINT `FKjn07s48mvyyiayhfd7ina9q7j` FOREIGN KEY (`classroom_id`) REFERENCES `classroom` (`id`),
  CONSTRAINT `FKkkk8ahbo86fmlc5p30bmbaf7p` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- attendance_support_upgrade.user_roles definition

CREATE TABLE `user_roles` (
  `user_id` varchar(255) NOT NULL,
  `roles_name` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`roles_name`),
  KEY `FK6pmbiap985ue1c0qjic44pxlc` (`roles_name`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK6pmbiap985ue1c0qjic44pxlc` FOREIGN KEY (`roles_name`) REFERENCES `role` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;