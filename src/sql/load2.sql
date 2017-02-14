-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 14, 2017 at 06:55 PM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `investigationsdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `cases`
--

CREATE TABLE `cases` (
  `id` int(11) NOT NULL,
  `name` varchar(35) DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `status` enum('Investigating','Solved','Preliminary') DEFAULT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cases`
--

INSERT INTO `cases` (`id`, `name`, `details`, `status`, `date`) VALUES
(138, 'case1', 'Description', 'Investigating', '2017-02-14 17:52:20'),
(139, 'case2', 'Description', 'Investigating', '2017-02-14 17:52:21'),
(140, 'case3', 'Description', 'Investigating', '2017-02-14 17:50:22'),
(141, 'case4', 'Description', 'Investigating', '2017-02-14 17:49:28'),
(142, 'case5', 'Description', 'Investigating', '2017-02-14 17:49:29'),
(143, 'case1', 'Description', 'Solved', '2017-02-14 17:50:20'),
(144, 'case2', 'Description', 'Solved', '2017-02-14 17:47:08'),
(145, 'case3', 'Description', 'Solved', '2017-02-14 17:50:20'),
(146, 'case4', 'Description', 'Solved', '2017-02-14 17:47:09'),
(147, 'case1', 'Description', 'Preliminary', '2017-02-14 17:50:00'),
(148, 'case2', 'Description', 'Preliminary', '2017-02-14 17:53:00'),
(149, 'case3', 'Description', 'Preliminary', '2017-02-14 17:47:11'),
(150, 'case4', 'Description', 'Preliminary', '2017-02-14 17:52:56'),
(151, 'case5', 'Description', 'Preliminary', '2017-02-14 17:52:54'),
(152, 'case6', 'Description', 'Preliminary', '2017-02-14 17:52:54'),
(153, 'case7', 'Description', 'Preliminary', '2017-02-14 17:52:53'),
(154, 'case1', 'Description', 'Investigating', '2017-02-14 17:49:30');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cases`
--
ALTER TABLE `cases`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cases`
--
ALTER TABLE `cases`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=155;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
