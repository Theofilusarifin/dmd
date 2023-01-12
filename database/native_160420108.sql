-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 12, 2023 at 07:50 AM
-- Server version: 10.3.37-MariaDB-0ubuntu0.20.04.1
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `native_160420108`
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`id`, `user_id`, `meme_id`, `content`, `created_at`) VALUES
(1, 1, 1, 'Ini commentku dari theo untuk meme 1', '2022-12-20 08:05:15'),
(2, 3, 2, 'Ini commentku dari alvin untuk meme 2', '2022-12-20 08:05:15'),
(3, 2, 2, 'Ini commentku dari hans untuk meme 2', '2022-12-20 08:05:15'),
(4, 3, 1, 'Ini commentku dari alvin untuk meme 1', '2022-12-20 08:05:15'),
(5, 3, 1, 'Test comment', '2022-12-20 08:50:53'),
(6, 2, 2, 'Ngakak nih!', '2023-01-02 09:14:42'),
(7, 2, 2, 'Lucu banget nih memenya!', '2023-01-02 09:43:29'),
(8, 2, 1, 'Tambah comment', '2023-01-02 10:42:12'),
(9, 2, 6, 'Test', '2023-01-02 12:47:12'),
(10, 2, 2, 'Kurang nih', '2023-01-02 12:49:28'),
(11, 3, 15, '\"Hahaha, ini terlalu lucu sampai aku tidak bisa berhenti tertawa! Betul banget sama kehidupan aku sekarang, terutama dengan semua kewajiban yang harus ditangani. Tapi kadang-kadang kita juga harus bersenang-senang dan bermain-main, ya kan? Terima kasih sudah membagikan postingan yang lucu ini!\"', '2023-01-08 08:24:27'),
(12, 3, 17, 'Ini terlalu menggelitik hatiku. Terima kasih sudah membagikannya.', '2023-01-08 08:24:46'),
(13, 3, 14, 'Ini terlalu relatable. Terima kasih sudah membagikannya', '2023-01-08 08:25:11'),
(14, 3, 3, 'Aku tidak bisa berhenti tertawa setelah melihat postingan ini. Terima kasih sudah membagikan konten yang menghibur dengan kita semua.', '2023-01-08 08:28:30'),
(15, 1, 17, 'Lucu nih tomnya!', '2023-01-08 09:10:09');

-- --------------------------------------------------------

--
-- Table structure for table `likes`
--

CREATE TABLE `likes` (
  `user_id` int(11) NOT NULL,
  `meme_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `likes`
--

INSERT INTO `likes` (`user_id`, `meme_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 11),
(1, 12),
(1, 15),
(1, 16),
(1, 17),
(2, 1),
(2, 4),
(2, 14),
(3, 3),
(3, 4),
(3, 7),
(3, 8),
(3, 9),
(3, 14),
(3, 16),
(3, 17),
(5, 1),
(7, 16),
(7, 17),
(8, 1),
(8, 2),
(8, 3),
(8, 4),
(8, 5),
(9, 1),
(9, 32);

-- --------------------------------------------------------

--
-- Table structure for table `like_comments`
--

CREATE TABLE `like_comments` (
  `user_id` int(11) NOT NULL,
  `comment_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `like_comments`
--

INSERT INTO `like_comments` (`user_id`, `comment_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(8, 9),
(9, 2);

-- --------------------------------------------------------

--
-- Table structure for table `memes`
--

CREATE TABLE `memes` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `url_img` text NOT NULL,
  `top_text` varchar(45) NOT NULL,
  `bottom_text` varchar(45) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `memes`
--

INSERT INTO `memes` (`id`, `user_id`, `url_img`, `top_text`, `bottom_text`, `created_at`) VALUES
(1, 1, 'https://ubaya.fun/native/160420108/meme/Meme-1.jpg', 'When you realize', 'it\'s Monday tomorrow', '2022-12-29 04:26:21'),
(2, 2, 'https://ubaya.fun/native/160420108/meme/Meme-2.jpg', 'When you\'re trying to study', 'but drama keeps distracting you.', '2022-12-30 04:26:21'),
(3, 3, 'https://ubaya.fun/native/160420108/meme/Meme-3.jpg', 'First day of school nerves like...', 'UHH', '2022-12-31 04:26:21'),
(4, 1, 'https://ubaya.fun/native/160420108/meme/Meme-4.jpg', 'When you\'re trying to focus', 'but your crush is in the same class.', '2023-01-01 04:26:21'),
(5, 1, 'https://ubaya.fun/native/160420108/meme/Meme-5.jpg', 'High school love:', 'o intense and so confusing.', '2023-01-02 04:26:21'),
(6, 2, 'https://ubaya.fun/native/160420108/meme/Meme-6.jpg', 'When your crush starts', 'dating someone else.', '2023-01-03 04:26:21'),
(7, 2, 'https://ubaya.fun/native/160420108/meme/Meme-7.jpg', 'When you\'re trying to focus on your studies', 'but all you can think about is your ex.', '2023-01-04 04:26:21'),
(8, 2, 'https://ubaya.fun/native/160420108/meme/Meme-8.jpg', 'School is hard,', 'love is harder.', '2023-01-05 04:26:21'),
(9, 3, 'https://ubaya.fun/native/160420108/meme/Meme-9.jpg', 'The struggle of trying to', 'balance school and love.', '2023-01-06 04:26:21'),
(10, 2, 'https://ubaya.fun/native/160420108/meme/Meme-10.jpg', 'When your crush starts', 'dating someone else.', '2023-01-07 04:26:21'),
(11, 3, 'https://ubaya.fun/native/160420108/meme/Meme-11.jpg', 'Sedang sibuk', 'tapi Netflix menunggu.', '2023-01-08 04:26:21'),
(12, 2, 'https://ubaya.fun/native/160420108/meme/Meme-12.jpg', 'Sibuk menjalani hidup', 'tapi masih suka bermain video game.', '2023-01-09 04:26:21'),
(13, 2, 'https://ubaya.fun/native/160420108/meme/Meme-13.jpg', 'Procrastination:', 'the art of stealing time.', '2023-01-10 04:26:21'),
(14, 2, 'https://ubaya.fun/native/160420108/meme/Meme-14.jpg', 'Love is a rollercoaster,', 'hold on tight.', '2023-01-11 04:26:21'),
(15, 1, 'https://ubaya.fun/native/160420108/meme/Meme-15.jpg', 'Single and loving it,', 'or is it?', '2023-01-11 04:26:21'),
(16, 3, 'https://ubaya.fun/native/160420108/meme/Meme-16.jpg', 'I thought I was ready for love,', 'but maybe not yet.', '2023-01-11 04:26:21'),
(17, 3, 'https://ubaya.fun/native/160420108/meme/Meme-17.jpg', 'When you\'re trying to be productive', 'but memes are life.', '2023-01-11 04:26:21'),
(32, 9, 'https://ubaya.fun/native/160420108/meme/Meme-18.jpg', 'Dua tiga ubur ubur', 'Kabuurr', '2023-01-12 09:01:31'),
(50, 4, 'https://ubaya.fun/native/160420108/meme/Meme-9.jpg', 'Informatics students', 'Love is a bug', '2023-01-12 09:18:08'),
(51, 10, 'https://ubaya.fun/native/160420108/meme/Meme-6.jpg', 'Satu dua tiga', 'love you', '2023-01-12 12:01:24');

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE `reports` (
  `user_id` int(11) NOT NULL,
  `meme_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `reports`
--

INSERT INTO `reports` (`user_id`, `meme_id`) VALUES
(1, 5),
(1, 6),
(1, 13),
(2, 5),
(2, 6),
(2, 13),
(3, 5),
(3, 6),
(3, 13),
(7, 6),
(9, 2),
(9, 5);

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
  `registration_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `url_img` text DEFAULT NULL,
  `privacy_setting` tinyint(4) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `first_name`, `last_name`, `password`, `registration_date`, `url_img`, `privacy_setting`) VALUES
(1, 'theo', 'Theofilus', 'Arifin', 'password', '2023-01-12 02:58:02', 'https://dmdproject02.000webhostapp.com/photo/1.jpg', 1),
(2, 'hans', 'Hans', 'Wirjawan', 'password', '2023-01-12 03:01:06', 'https://dmdproject02.000webhostapp.com/photo/2.jpg', 1),
(3, 'Alvin', 'Gregorius', 'Alvin', 'password', '2023-01-12 03:00:32', 'https://dmdproject02.000webhostapp.com/photo/3.jpg', 0),
(4, 'user1', 'user', '1', 'password', '2023-01-12 03:00:36', 'https://dmdproject02.000webhostapp.com/photo/4.jpg', 0),
(5, 'user2', 'user', '2', 'password', '2023-01-12 03:00:41', 'https://dmdproject02.000webhostapp.com/photo/5.jpg', 0),
(7, 'user3', 'user', '3', 'password', '2023-01-12 03:00:59', 'https://dmdproject02.000webhostapp.com/photo/7.jpg', 1),
(8, 'user4', 'user', '4', 'password', '2023-01-12 03:00:55', 'https://dmdproject02.000webhostapp.com/photo/8.jpg', 0),
(9, 'user5', 'user', '5', 'password', '2023-01-12 03:01:03', 'https://dmdproject02.000webhostapp.com/photo/9.jpg', 0),
(10, 'user6', 'user', '6', 'password', '2023-01-12 12:01:51', 'https://dmdproject02.000webhostapp.com/photo/10.jpg', 1);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `memes`
--
ALTER TABLE `memes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

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
