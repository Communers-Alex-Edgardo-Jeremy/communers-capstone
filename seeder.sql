-- Adminer 4.8.1 MySQL 5.5.5-10.6.12-MariaDB-0ubuntu0.22.04.1 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
                            `id` int(20) NOT NULL AUTO_INCREMENT,
                            `body` varchar(255) NOT NULL,
                            `date` varchar(50) NOT NULL,
                            `post_id` int(11) NOT NULL,
                            `user_id` int(20) NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY `post_id` (`post_id`),
                            KEY `user_id` (`user_id`),
                            CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                            CONSTRAINT `FKh4c7lvsc298whoyd4w9ta25cr` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `comments` (`id`, `body`, `date`, `post_id`, `user_id`) VALUES
                                                                        (69,	'Thank you for sharing your story. It takes a lot of courage to open up about these struggles, and I\'m glad to hear that you\'ve been able to find some peace through therapy and medication',	'04/27/23, 01:52 AM',	81,	148),
                                                                        (70,	'Support from those around u is invaluable!',	'04/27/23, 02:02 PM',	73,	148),
                                                                        (71,	'Substituting activities was helpful for me as well! I might try gardening too',	'04/27/23, 03:03 AM',	80,	148),
                                                                        (72,	'Wow! You go!',	'04/27/23, 05:08 PM',	80,	142),
                                                                        (73,	'It takes a lot of courage to consider coming out to friends and co-workers. Remember that you deserve to be true to yourself, and anyone who truly cares about you will accept you for who you are.',	'04/27/23, 07:19 AM',	75,	143),
                                                                        (75,	'That\'s amazing! Running can be such a powerful tool for managing mental health symptoms. Keep up the great work and good luck with your half marathon training',	'04/27/23, 08:24 AM',	72,	143),
                                                                        (76,	'I love hearing stories like this. It\'s so inspiring to see how running can positively impact mental health. Congrats on your progress and good luck with your training!',	'04/27/23, 11:25 PM',	72,	145),
                                                                        (79,	'The power of peer support should not be underestimated. It\'s so important to have a space where you can talk openly and honestly with others who can relate. Thank you for sharing your story and inspiring others to seek out support',	'04/27/23, 12:28 AM',	82,	143),
                                                                        (80,	'Have you tried painting while on hikes?? The best!',	'04/27/23, 05:30 AM',	84,	143),
                                                                        (108,	'That is great ! I have recently started running as well and hope it helps me like it has you. ',	'04/30/23, 09:32 PM',	72,	58);

DROP TABLE IF EXISTS `communities`;
CREATE TABLE `communities` (
                               `id` int(20) NOT NULL AUTO_INCREMENT,
                               `name` varchar(100) NOT NULL,
                               `bio` varchar(255) NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `communities` (`id`, `name`, `bio`) VALUES
                                                    (4,	'Depression Support Group',	'A safe space for individuals who are struggling with depression to share their experiences, provide support, and receive encouragement from others who understand what they\'re going through'),
                                                    (5,	'Anxiety Relievers',	' A community focused on providing resources, tips, and support for people living with anxiety disorders, helping them manage their symptoms and achieve a more balanced and fulfilling life'),
                                                    (6,	'PTSD (Post-Traumatic Stress Disorder) Awareness Circle',	'A group focused on raising awareness about PTSD and providing support to individuals who have been affected by trauma, offering a space for individuals to share their experiences and seek guidance'),
                                                    (7,	'Eating Disorder Recovery Community',	'A community focused on supporting individuals recovering from eating disorders, offering resources and support for those in recovery, and sharing experiences and strategies for achieving and maintaining recovery'),
                                                    (8,	'Addiction Recovery Support Group',	'A community focused on providing support and guidance for individuals in recovery from addiction, offering resources, encouragement, and accountability.'),
                                                    (9,	'LGBTQ+ Mental Health Advocacy Group',	'A community focused on advocating for the mental health needs of the LGBTQ+ community, providing resources, education, and support for individuals who may face unique mental health challenges'),
                                                    (10,	'Senior Citizens\' Mental Health Support Group',	'A community focused on supporting the mental health needs of senior citizens, offering resources, education, and support for individuals who may face unique mental health challenges related to aging'),
                                                    (11,	'Student Mental Health Coalition',	'A community focused on advocating for and supporting the mental health needs of students, providing resources, support, and education on how to maintain mental health and wellness during the stresses of academic life.'),
                                                    (12,	'Mindfulness and Meditation Practice Group',	'A group focused on practicing mindfulness and meditation, providing resources and support for individuals who want to improve their mental well-being through mindfulness practices');

DROP TABLE IF EXISTS `entries`;
CREATE TABLE `entries` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `title` varchar(100) NOT NULL,
                           `body` varchar(255) NOT NULL,
                           `date` varchar(100) NOT NULL,
                           `user_id` int(20) NOT NULL,
                           PRIMARY KEY (`id`),
                           KEY `user_id` (`user_id`),
                           CONSTRAINT `FKoia5s1p9sk4x5fld87yjqpjg9` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                           CONSTRAINT `entries_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `follows`;
CREATE TABLE `follows` (
                           `follower` int(20) NOT NULL,
                           `followee` int(20) NOT NULL,
                           KEY `follower` (`follower`),
                           KEY `followee` (`followee`),
                           CONSTRAINT `FKa58cq5w7xon5bn0m7e7n6lbwt` FOREIGN KEY (`followee`) REFERENCES `users` (`id`),
                           CONSTRAINT `FKjnqt4f5bti6niw7afunse4de7` FOREIGN KEY (`follower`) REFERENCES `users` (`id`),
                           CONSTRAINT `follows_ibfk_1` FOREIGN KEY (`follower`) REFERENCES `users` (`id`),
                           CONSTRAINT `follows_ibfk_2` FOREIGN KEY (`followee`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `follows` (`follower`, `followee`) VALUES
                                                   (148,	145),
                                                   (148,	144),
                                                   (144,	58),
                                                   (145,	58),
                                                   (160,	141),
                                                   (58,	145),
                                                   (58,	144),
                                                   (58,	142),
                                                   (58,	12),
                                                   (140,	12),
                                                   (12,	143),
                                                   (12,	141),
                                                   (12,	140),
                                                   (12,	58);

DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `title` varchar(50) NOT NULL,
                         `body` varchar(255) NOT NULL,
                         `time` varchar(50) NOT NULL,
                         `user_id` int(20) NOT NULL,
                         PRIMARY KEY (`id`),
                         KEY `user_id` (`user_id`),
                         CONSTRAINT `FK5lidm6cqbc7u4xhqpxm898qme` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                         CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `posts` (`id`, `title`, `body`, `time`, `user_id`) VALUES
                                                                   (71,	'Dealing with my Social Anxiety Part 1',	'I started by going to a local coffee shop and gradually worked my way up to larger social events. Short consistent steps constantly heading for that goal.',	'04/23/23, 08:47 AM',	140),
                                                                   (72,	'Running Helped My Depression',	'Running gave me a sense of accomplishment and helped me manage my symptoms. Now I\'m training for a half marathon!',	'04/27/23, 11:21 PM',	140),
                                                                   (73,	'My Journey to Sobriety',	'I hit rock bottom and realized I needed to make a change. With the support of friends and family, I got sober and haven\'t looked back.',	'04/19/23, 05:45 AM',	141),
                                                                   (74,	'Journaling Accountability',	'My journal has saved me so many trips to the store for paper or pencils. I prefer typing anyways and its helpful to check-in on myself daily.',	'04/08/23, 08:33 PM',	141),
                                                                   (75,	'Could use some support',	'Not in the best mindset right now... I haven\'t come out of the closet to a few friends and co-workers that I feel like I can trust and are my friends. Would they still be my friends if I did?',	'04/22/23, 11:10 AM',	142),
                                                                   (78,	'Finals are next week!',	'I\'ve done all the study guides my teacher has given us and I still don\'t feel prepared enough. HELPPPP',	'03/03/23, 11:14 PM',	142),
                                                                   (79,	'Tracking my activity on my Phone',	'I started tracking my screen time (I know IOS does it for you) and was shocked by how much I was browsing instagram. Whenever I see so many likes on unrealistic looking bodies it becomes harder for me to eat. I think I\'m gonna delete insta.',	'04/10/23, 03:37 PM',	144),
                                                                   (80,	'Week after deleting Insta',	'It\'s been hard because it was a big hobby (time consumer) for me so I\'ve been bored here and there. However, I\'ve started growing a garden and found myself eating more greens. I feel better tending to my garden than I did browsing insta.',	'04/24/23, 01:41 AM',	144),
                                                                   (81,	'The Struggle to Adjust to Civilian Life',	'After serving in combat, it was hard to adjust to civilian life. I struggled with depression and PTSD symptoms, but with the help of therapy and medication, I\'ve been able to manage my symptoms and find some peace.',	'04/20/23, 06:47 PM',	145),
                                                                   (82,	'Finding Support in My Fellow Veterans',	'Connecting with other veterans who have similar experiences has been invaluable. It\'s comforting to know that I\'m not alone and that there are others who understand what I\'ve been through',	'04/25/23, 07:47 PM',	145),
                                                                   (83,	'How My Service Dog Has Changed My Life',	'My service dog has been a lifesaver. She helps me with tasks and provides emotional support when I\'m feeling overwhelmed. She\'s given me a new sense of purpose and has brought so much joy to my life',	'04/20/23, 03:12 AM',	145),
                                                                   (84,	'Finding New Hobbies in Sobriety',	'After getting sober, I had to find new ways to fill my time. I\'ve discovered a love for hiking and painting, and these hobbies have been instrumental in my recovery',	'04/23/23, 03:56 AM',	148),
                                                                   (85,	'Dealing with Relapse: How I Bounced Back',	'Relapse can be discouraging, but it doesn\'t have to be the end of your recovery journey. I found strength in my support system and re-dedicated myself to my sobriety',	'04/12/23, 07:57 PM',	148);

DROP TABLE IF EXISTS `post_community`;
CREATE TABLE `post_community` (
                                  `post_id` int(11) NOT NULL,
                                  `community_id` int(20) NOT NULL,
                                  KEY `post_id` (`post_id`),
                                  KEY `community_id` (`community_id`),
                                  CONSTRAINT `FK4psf21ouy7t05k9j2sx47etwg` FOREIGN KEY (`community_id`) REFERENCES `communities` (`id`),
                                  CONSTRAINT `FK5txvvr3dntrc0u4anjthoavlm` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
                                  CONSTRAINT `post_community_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
                                  CONSTRAINT `post_community_ibfk_2` FOREIGN KEY (`community_id`) REFERENCES `communities` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `post_community` (`post_id`, `community_id`) VALUES
                                                             (71,	5),
                                                             (72,	4),
                                                             (73,	6),
                                                             (73,	8),
                                                             (74,	6),
                                                             (74,	10),
                                                             (75,	9),
                                                             (75,	11),
                                                             (78,	4),
                                                             (78,	9),
                                                             (78,	11),
                                                             (78,	5),
                                                             (79,	12),
                                                             (79,	5),
                                                             (79,	4),
                                                             (79,	7),
                                                             (80,	12),
                                                             (80,	7),
                                                             (80,	4),
                                                             (80,	5),
                                                             (81,	6),
                                                             (81,	4),
                                                             (81,	10),
                                                             (82,	6),
                                                             (82,	10),
                                                             (83,	6),
                                                             (83,	10),
                                                             (84,	8),
                                                             (84,	5),
                                                             (84,	4),
                                                             (85,	8),
                                                             (85,	5),
                                                             (85,	4);

DROP TABLE IF EXISTS `questionnaires`;
CREATE TABLE `questionnaires` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `notifications` char(1) NOT NULL,
                                  `answer_1` char(1) NOT NULL,
                                  `answer_2` char(1) NOT NULL,
                                  `answer_3` char(1) NOT NULL,
                                  `user_id` int(20) NOT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `user_id` (`user_id`),
                                  CONSTRAINT `FK2n5xferv8ajlb4hwiuxifq6p4` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                  CONSTRAINT `questionnaires_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `questionnaires` (`id`, `notifications`, `answer_1`, `answer_2`, `answer_3`, `user_id`) VALUES
                                                                                                        (17,	'Y',	'Y',	'Y',	'N',	12),
                                                                                                        (112,	'Y',	'N',	'N',	'N',	58),
                                                                                                        (124,	'Y',	'N',	'N',	'N',	140),
                                                                                                        (125,	'Y',	'Y',	'Y',	'Y',	141),
                                                                                                        (126,	'Y',	'Y',	'Y',	'N',	142),
                                                                                                        (127,	'Y',	'N',	'N',	'N',	143),
                                                                                                        (128,	'N',	'N',	'Y',	'N',	144),
                                                                                                        (130,	'Y',	'Y',	'N',	'N',	145),
                                                                                                        (131,	'Y',	'N',	'Y',	'N',	148),
                                                                                                        (138,	'Y',	'Y',	'N',	'Y',	160);

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `id` int(20) NOT NULL AUTO_INCREMENT,
                         `first_name` varchar(255) NOT NULL,
                         `last_name` varchar(255) NOT NULL,
                         `username` varchar(255) NOT NULL,
                         `email` varchar(255) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `questionnaire_id` bigint(20) DEFAULT NULL,
                         `reset_password_token` varchar(255) DEFAULT NULL,
                         `verification_code` varchar(64) DEFAULT NULL,
                         `image` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `users` (`id`, `first_name`, `last_name`, `username`, `email`, `password`, `questionnaire_id`, `reset_password_token`, `verification_code`, `image`) VALUES
                                                                                                                                                                     (12,	'jeremy',	'wood',	'jeremy',	'jeremy@jeremy.com',	'$2a$10$p2WegnvGUTMyTUx5sttA..SsCdV0Pgsgz2h9enMSsZlqGc26xLmSm',	17,	NULL,	NULL,	'https://cdn.filestackcontent.com/FXhtHjLvQ5uzJg9lglzL'),
                                                                                                                                                                     (58,	'Alex',	'Schmerber',	'Alexander',	'schmerbs123@gmail.com',	'$2a$10$cTG/Q5TuXEKzNUM.QDsv0e602U4WmcbaT37L.P6CBeItI3uud48DS',	112,	NULL,	NULL,	'https://cdn.filestackcontent.com/ZM6YfeLBSCWvRuU3wLFW'),
                                                                                                                                                                     (140,	'Joe',	'Schmoe',	'Anon',	'jschmoe@gmail.com',	'$2a$10$GjxfTWzdDFNwFPNwJpsG/eYaQ23CARZJJLV7NTIkFGPZutaUQD2q6',	NULL,	NULL,	NULL,	'https://cdn.filestackcontent.com/ZM6YfeLBSCWvRuU3wLFW'),
                                                                                                                                                                     (141,	'Andrew',	'Jonas',	'Andy12',	'drew42@yahoo.com',	'$2a$10$vm3H1LBOS8iAjMxXDrJeIOcOKdRLJGKQKjScIx6lEm8i7Ymdremz6',	NULL,	NULL,	NULL,	'https://cdn.filestackcontent.com/LVcVfRHgQLYuqLMlnC3j'),
                                                                                                                                                                     (142,	'Gregory',	'Timbleton',	'Greg77',	'gregtim@gmail.com',	'$2a$10$w9ftxIi2D.5kLKw2/ZmrmeHiAkNKgitZ/Y4P6K/dNgLzHYJ6/w.Gm',	NULL,	NULL,	NULL,	'https://cdn.filestackcontent.com/Ir7A9refRfedPoMHGtBV'),
                                                                                                                                                                     (143,	'Rachel',	'Barnette',	'rachel30',	'barnrachel@gmail.com',	'$2a$10$XydEReZ59HICJSj2LNm1neIECW8YFWMDyfuLztkoXYRGkOTbqCoMi',	NULL,	NULL,	NULL,	'https://cdn.filestackcontent.com/CaOTXon1QSuqV7XOzd4t'),
                                                                                                                                                                     (144,	'Tiffany',	'Greene',	'Tiffany23',	'tiffgren@gmail.com',	'$2a$10$TN7YdtgrLOscLSaRP52L0e1nxDqUQHII1dkfVTKZJvSc1vrk6Efae',	NULL,	NULL,	NULL,	'https://cdn.filestackcontent.com/oWo6IXzTTRaq85BTpjm1'),
                                                                                                                                                                     (145,	'Jordan',	'Willows',	'Jordie',	'jwillows@gmail.com',	'$2a$10$mHJhPqaDX7YtmPC0x/5pcedB3iaVre2axQ0Ebql47ghpygx3/776i',	NULL,	NULL,	NULL,	'https://cdn.filestackcontent.com/mjxwk2qTxai4G9BZP7VI'),
                                                                                                                                                                     (148,	'Emily',	'Neutania',	'Emma',	'emneut22@gmail.com',	'$2a$10$aKBgSYCkmaZQjlIfsp5FPuVoZxB4/v9ew3t3.mgYOSjeq9sMUU4x6',	NULL,	NULL,	NULL,	'https://cdn.filestackcontent.com/YwOcnbPHQwibkppWG5Id'),
                                                                                                                                                                     (160,	'Rori',	'Rori',	'Rori',	'schmerbs123@gmail.com',	'$2a$10$Yn/RMQ7cMOAWn6t1SabBLefjOwOkpu40j8V1yDPBo4uXNIuXkzUpm',	NULL,	NULL,	NULL,	'https://cdn.filestackcontent.com/ZM6YfeLBSCWvRuU3wLFW');

DROP TABLE IF EXISTS `user_community`;
CREATE TABLE `user_community` (
                                  `user_id` int(20) NOT NULL,
                                  `community_id` int(20) NOT NULL,
                                  KEY `user_id` (`user_id`),
                                  KEY `community_id` (`community_id`),
                                  CONSTRAINT `FK2ixu6r37ergto35lrtxstsf4` FOREIGN KEY (`community_id`) REFERENCES `communities` (`id`),
                                  CONSTRAINT `FKrx2p3vob0yqc39jpj416vkvqr` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                  CONSTRAINT `user_community_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                  CONSTRAINT `user_community_ibfk_2` FOREIGN KEY (`community_id`) REFERENCES `communities` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `user_community` (`user_id`, `community_id`) VALUES
                                                             (142,	11),
                                                             (144,	12),
                                                             (142,	9),
                                                             (144,	7),
                                                             (141,	6),
                                                             (145,	6),
                                                             (148,	6),
                                                             (141,	8),
                                                             (148,	8),
                                                             (141,	10),
                                                             (145,	10),
                                                             (140,	4),
                                                             (142,	4),
                                                             (144,	4),
                                                             (145,	4),
                                                             (148,	4),
                                                             (58,	4),
                                                             (12,	4),
                                                             (140,	5),
                                                             (142,	5),
                                                             (144,	5),
                                                             (148,	5),
                                                             (160,	5),
                                                             (12,	5);

-- 2023-05-04 17:40:38