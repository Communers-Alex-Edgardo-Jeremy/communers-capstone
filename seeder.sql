-- Adminer 4.8.1 MySQL 5.5.5-10.6.12-MariaDB-0ubuntu0.22.04.1 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

USE `community`;

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
                            `id` int(20) NOT NULL AUTO_INCREMENT,
                            `body` varchar(255) NOT NULL,
                            `date` varchar(255) NOT NULL,
                            `post_id` int(11) NOT NULL,
                            `user_id` int(20) NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY `post_id` (`post_id`),
                            KEY `user_id` (`user_id`),
                            CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
                            CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `comments` (`id`, `body`, `date`, `post_id`, `user_id`) VALUES
                                                                        (1,	'Depends on what your interested in. Theres alot of communities out there, explore to find out on the discover panel.',	'4/10/2023@12:21',	2,	1),
                                                                        (2,	'Don\'t panic! For me, it helps to picture I\'m someplace else. Like chilling on the beach and maybe a crab came up and gave me a pinch in the arm!',	'4/3/2023@11:55',	5,	10);

DROP TABLE IF EXISTS `communities`;
CREATE TABLE `communities` (
                               `id` int(20) NOT NULL AUTO_INCREMENT,
                               `name` varchar(100) NOT NULL,
                               `bio` varchar(255) NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `communities` (`id`, `name`, `bio`) VALUES
                                                    (1,	'arachnophobia',	'No Spiders Allowed!!'),
                                                    (2,	'Trypanophobia',	'Discussions every Tuesday at 6pm!');

DROP TABLE IF EXISTS `follows`;
CREATE TABLE `follows` (
                           `follower` int(20) NOT NULL,
                           `followee` int(20) NOT NULL,
                           KEY `follower` (`follower`),
                           KEY `followee` (`followee`),
                           CONSTRAINT `follows_ibfk_1` FOREIGN KEY (`follower`) REFERENCES `users` (`id`),
                           CONSTRAINT `follows_ibfk_2` FOREIGN KEY (`followee`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `follows` (`follower`, `followee`) VALUES
                                                   (1,	8),
                                                   (8,	1),
                                                   (9,	8),
                                                   (8,	9),
                                                   (1,	11),
                                                   (10,	1);

DROP TABLE IF EXISTS `journals`;
CREATE TABLE `journals` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `title` varchar(100) NOT NULL,
                            `body` varchar(255) NOT NULL,
                            `date` varchar(25) NOT NULL,
                            `user_id` int(20) NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY `user_id` (`user_id`),
                            CONSTRAINT `journals_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `journals` (`id`, `title`, `body`, `date`, `user_id`) VALUES
                                                                      (1,	'GROCERIES',	'Whenever it comes to that time of the week, I start dreading everything!',	'4/10/2023@11:02',	1),
                                                                      (2,	'Diary log #1',	'Happy New Year! Today is the start of something new. I\'m going to finally start prioritizing myself everyone is being very supportive and i can\'t wait to start this journey and document it here!',	'1/1/2023@16:53',	11),
                                                                      (3,	'Diary log #3',	'I deleted my last entry because I thought I was a tad bit negative in writing it so I\'m starting again anew today!',	'1/3/2023@11:06',	11),
                                                                      (4,	'I lost my phone!!',	'Coming back after all this time because I finally found my phone again!',	'4/6/2023@15:22',	11);

DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `title` varchar(50) NOT NULL,
                         `body` varchar(255) NOT NULL,
                         `time` varchar(50) NOT NULL,
                         `user_id` int(20) NOT NULL,
                         PRIMARY KEY (`id`),
                         KEY `user_id` (`user_id`),
                         CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `posts` (`id`, `title`, `body`, `time`, `user_id`) VALUES
                                                                   (2,	'Whats up',	'Just got here and looking for communities to join, anyone got any recommendations?',	'4/10/2023@11:21',	9),
                                                                   (3,	'Day 3 of new job',	'Finally done with orientation, gradually feeling less imposter syndrome. More updates to follow!',	'4/8/2023@8:21',	10),
                                                                   (4,	'Spider Attack',	'It happened this morning. I was grabbing my cup of coffee (like any other morning) when it happened. It attacked swiftly and made a speedy exit. I only had enough time to drop my coffee in shock. Now I got a spider problem and a coffee on pants problem.',	'4/3/2023@10:21',	1),
                                                                   (5,	'Doctors Appointment Tomorrow',	'I have to get my blood drawn tomorrow... HELP!!',	'4/3/2023@10:55',	11);

DROP TABLE IF EXISTS `post_community`;
CREATE TABLE `post_community` (
                                  `post_id` int(11) NOT NULL,
                                  `community_id` int(20) NOT NULL,
                                  KEY `post_id` (`post_id`),
                                  KEY `community_id` (`community_id`),
                                  CONSTRAINT `post_community_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
                                  CONSTRAINT `post_community_ibfk_2` FOREIGN KEY (`community_id`) REFERENCES `communities` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `post_community` (`post_id`, `community_id`) VALUES
                                                             (4,	1),
                                                             (5,	2);

DROP TABLE IF EXISTS `questionnaires`;
CREATE TABLE `questionnaires` (
                                  `id` int(11) NOT NULL AUTO_INCREMENT,
                                  `question` varchar(100) NOT NULL,
                                  `answer` int(1) NOT NULL,
                                  `user_id` int(20) NOT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `user_id` (`user_id`),
                                  CONSTRAINT `questionnaires_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `questionnaires` (`id`, `question`, `answer`, `user_id`) VALUES
                                                                         (1,	'1',	0,	1),
                                                                         (2,	'2',	1,	1),
                                                                         (3,	'3',	0,	1),
                                                                         (4,	'1',	1,	11),
                                                                         (5,	'2',	1,	11),
                                                                         (6,	'3',	0,	11);

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `id` int(20) NOT NULL AUTO_INCREMENT,
                         `first_name` varchar(255) NOT NULL,
                         `last_name` varchar(255) NOT NULL,
                         `username` varchar(255) NOT NULL,
                         `email` varchar(255) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `users` (`id`, `first_name`, `last_name`, `username`, `email`, `password`) VALUES
                                                                                           (1,	'alex',	'schmerber',	'lex',	'alex@gmail.com',	'pw123'),
                                                                                           (8,	'edgardo',	'ortiz',	'eddy',	'ed@yahoo.com',	'password'),
                                                                                           (9,	'jeremy',	'wood',	'jerma',	'jwood@gmail.com',	'p@ssw0rd'),
                                                                                           (10,	'shawn',	'hardin',	'shardin',	'shardin@yahoo.com',	'shard123'),
                                                                                           (11,	'bob',	'bobert',	'bobby',	'bbobert@gmail.com',	'bob4life');

DROP TABLE IF EXISTS `user_community`;
CREATE TABLE `user_community` (
                                  `user_id` int(20) NOT NULL,
                                  `community_id` int(20) NOT NULL,
                                  KEY `user_id` (`user_id`),
                                  KEY `community_id` (`community_id`),
                                  CONSTRAINT `user_community_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                  CONSTRAINT `user_community_ibfk_2` FOREIGN KEY (`community_id`) REFERENCES `communities` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `user_community` (`user_id`, `community_id`) VALUES
                                                             (1,	1),
                                                             (11,	2);

-- 2023-04-10 16:41:54