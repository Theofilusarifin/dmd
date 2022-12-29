-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 29, 2022 at 07:31 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dmd`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `meme_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`id`, `user_id`, `meme_id`, `content`, `created_at`) VALUES
(1, 1, 1, 'Ini commentku dari theo untuk meme 1', '2022-12-20 08:05:15'),
(2, 3, 2, 'Ini commentku dari alvin untuk meme 2', '2022-12-20 08:05:15'),
(3, 2, 2, 'Ini commentku dari hans untuk meme 2', '2022-12-20 08:05:15'),
(4, 3, 1, 'Ini commentku dari alvin untuk meme 1', '2022-12-20 08:05:15'),
(5, 3, 1, 'Test comment', '2022-12-20 08:50:53');

-- --------------------------------------------------------

--
-- Table structure for table `likes`
--

CREATE TABLE `likes` (
  `user_id` int(11) NOT NULL,
  `meme_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `likes`
--

INSERT INTO `likes` (`user_id`, `meme_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 1),
(2, 2),
(3, 3),
(3, 4),
(5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `like_comments`
--

CREATE TABLE `like_comments` (
  `user_id` int(11) NOT NULL,
  `comment_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `like_comments`
--

INSERT INTO `like_comments` (`user_id`, `comment_id`) VALUES
(1, 2),
(1, 3),
(2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `memes`
--

CREATE TABLE `memes` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `url_img` varchar(45) NOT NULL,
  `top_text` varchar(45) NOT NULL,
  `bottom_text` varchar(45) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `memes`
--

INSERT INTO `memes` (`id`, `user_id`, `url_img`, `top_text`, `bottom_text`, `created_at`) VALUES
(1, 1, 'https://picsum.photos/200/300', 'Top Text', 'Bottom Text', '2022-12-29 08:20:19'),
(2, 2, 'https://picsum.photos/200/300', 'Top Text 2', 'Bottom Text 2', '2022-12-29 08:20:19'),
(3, 3, 'https://picsum.photos/200/300', 'Top Text 3', 'Bottom Text 3', '2022-12-29 08:20:19'),
(4, 1, 'https://picsum.photos/200/300', 'Top Text', 'Bottom Text', '2022-12-29 08:20:19'),
(5, 1, 'https://picsum.photos/200/300', 'Ini Top Text', 'Ini Bottom Text', '2022-12-29 08:20:19');

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE `reports` (
  `user_id` int(11) NOT NULL,
  `meme_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `registration_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `url_img` varchar(255) DEFAULT NULL,
  `privacy_setting` tinyint(4) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `first_name`, `last_name`, `password`, `registration_date`, `url_img`, `privacy_setting`) VALUES
(1, 'theo', 'Theofilus', 'Arifin', 'password', '2022-12-29 16:19:43', 'https://picsum.photos/200/300', 1),
(2, 'hans', 'Hans', 'Wirjawan', 'password', '2022-12-20 07:57:53', 'https://picsum.photos/200/300', 0),
(3, 'Alvin', 'Gregorius', 'Alvin', 'password', '2022-12-20 10:05:22', 'https://picsum.photos/200/300', 0),
(4, 'user', 'user', NULL, 'password', '2022-12-20 10:05:22', 'https://picsum.photos/200/300', 0),
(5, 'finz', 'theo', 'theo', 'password', '2022-12-28 17:03:33', NULL, 0),
(6, '', '', '', '', '2022-12-28 17:05:27', NULL, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_comments_users1_idx` (`user_id`),
  ADD KEY `fk_comments_memes1_idx` (`meme_id`);

--
-- Indexes for table `likes`
--
ALTER TABLE `likes`
  ADD PRIMARY KEY (`user_id`,`meme_id`),
  ADD KEY `fk_users_has_memes_memes2_idx` (`meme_id`),
  ADD KEY `fk_users_has_memes_users2_idx` (`user_id`);

--
-- Indexes for table `like_comments`
--
ALTER TABLE `like_comments`
  ADD PRIMARY KEY (`user_id`,`comment_id`),
  ADD KEY `fk_users_has_comments_comments1_idx` (`comment_id`),
  ADD KEY `fk_users_has_comments_users1_idx` (`user_id`);

--
-- Indexes for table `memes`
--
ALTER TABLE `memes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_memes_users_idx` (`user_id`);

--
-- Indexes for table `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`user_id`,`meme_id`),
  ADD KEY `fk_users_has_memes_memes1_idx` (`meme_id`),
  ADD KEY `fk_users_has_memes_users1_idx` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `memes`
--
ALTER TABLE `memes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `fk_comments_memes1` FOREIGN KEY (`meme_id`) REFERENCES `memes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_comments_users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `likes`
--
ALTER TABLE `likes`
  ADD CONSTRAINT `fk_users_has_memes_memes2` FOREIGN KEY (`meme_id`) REFERENCES `memes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_users_has_memes_users2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `like_comments`
--
ALTER TABLE `like_comments`
  ADD CONSTRAINT `fk_users_has_comments_comments1` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_users_has_comments_users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `memes`
--
ALTER TABLE `memes`
  ADD CONSTRAINT `fk_memes_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `reports`
--
ALTER TABLE `reports`
  ADD CONSTRAINT `fk_users_has_memes_memes1` FOREIGN KEY (`meme_id`) REFERENCES `memes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_users_has_memes_users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
