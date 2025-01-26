CREATE DATABASE springjdbc;
use springjdbc;

CREATE TABLE `contact` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(25) DEFAULT NULL,
  `contact` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `details` (
  `id` bigint NOT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` int NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `guest_details` (
  `id` bigint NOT NULL,
  `master_id` bigint DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `masterclinic` (
  `id` bigint NOT NULL,
  `patient_id` bigint DEFAULT NULL,
  `patient_name` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `appointment_seq` int DEFAULT NULL,
  `isdone` int DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `masterpatient` (
  `patient_id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `isverified` int DEFAULT NULL,
  `otp` varchar(255) DEFAULT NULL,
  `otpverifyattempt` int DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `patient_details` (
  `patient_id` int NOT NULL,
  `patient_name` varchar(45) DEFAULT NULL,
  `contact` varchar(10) DEFAULT NULL,
  `location` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `profile_image` blob,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `patient_prescription` (
  `id` bigint NOT NULL,
  `patient_id` bigint NOT NULL,
  `prescription_id` bigint DEFAULT NULL,
  `patient_prescription` longblob,
  `date_added` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
