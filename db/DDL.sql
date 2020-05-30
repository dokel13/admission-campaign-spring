CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `idroles_UNIQUE` (`role_id`),
  UNIQUE KEY `role_UNIQUE` (`role`)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `subjects` (
  `subject_id` int NOT NULL AUTO_INCREMENT,
  `subject` varchar(45) NOT NULL,
  PRIMARY KEY (`subject_id`),
  UNIQUE KEY `idsubjects_UNIQUE` (`subject_id`),
  UNIQUE KEY `subject_UNIQUE` (`subject`)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `specialties` (
  `specialty_id` int NOT NULL AUTO_INCREMENT,
  `specialty` varchar(45) NOT NULL,
  `max_student_amount` int NOT NULL,
  `open` bit(1) NOT NULL,
  PRIMARY KEY (`specialty_id`),
  UNIQUE KEY `idspecialties_UNIQUE` (`specialty_id`),
  UNIQUE KEY `specialtiescol_UNIQUE` (`specialty`)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `role` int NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `idusers_UNIQUE` (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `user_role_key_idx` (`role`),
  CONSTRAINT `user_role_key` FOREIGN KEY (`role`) REFERENCES `roles` (`role_id`)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `requirements` (
  `requirement_id` int NOT NULL AUTO_INCREMENT,
  `specialty` int NOT NULL,
  `subject` int NOT NULL,
  `mark` int NOT NULL,
  PRIMARY KEY (`requirement_id`),
  UNIQUE KEY `idrequirements_UNIQUE` (`requirement_id`),
  KEY `requirement_specialty_key_idx` (`specialty`),
  KEY `requirement_subject_key_idx` (`subject`),
  CONSTRAINT `requirement_specialty_key` FOREIGN KEY (`specialty`) REFERENCES `specialties` (`specialty_id`),
  CONSTRAINT `requirement_subject_key` FOREIGN KEY (`subject`) REFERENCES `subjects` (`subject_id`)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `exams` (
  `exam_id` int NOT NULL AUTO_INCREMENT,
  `user` int NOT NULL,
  `subject` int NOT NULL,
  `mark` int DEFAULT NULL,
  PRIMARY KEY (`exam_id`),
  UNIQUE KEY `id_result_UNIQUE` (`exam_id`),
  KEY `result_user_key_idx` (`user`),
  KEY `result_subject_key_idx` (`subject`),
  CONSTRAINT `exam_subject_key` FOREIGN KEY (`subject`) REFERENCES `subjects` (`subject_id`),
  CONSTRAINT `exam_user_key` FOREIGN KEY (`user`) REFERENCES `users` (`user_id`)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `applications` (
  `application_id` int NOT NULL AUTO_INCREMENT,
  `user` int NOT NULL,
  `specialty` int NOT NULL,
  `enrollment` bit(1) NOT NULL,
  `mark_sum` int DEFAULT NULL,
  PRIMARY KEY (`application_id`),
  UNIQUE KEY `application_id_UNIQUE` (`application_id`),
  UNIQUE KEY `user_UNIQUE` (`user`),
  KEY `application_specialty_key_idx` (`specialty`),
  CONSTRAINT `application_specialty_key` FOREIGN KEY (`specialty`) REFERENCES `specialties` (`specialty_id`),
  CONSTRAINT `application_user_key` FOREIGN KEY (`user`) REFERENCES `users` (`user_id`)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
