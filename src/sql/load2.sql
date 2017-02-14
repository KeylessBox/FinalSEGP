-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 14, 2017 at 06:59 PM
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
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `id` int(11) NOT NULL,
  `name` tinytext,
  `surname` tinytext,
  `email` tinytext,
  `password` tinytext,
  `privileges` tinytext
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`id`, `name`, `surname`, `email`, `password`, `privileges`) VALUES
(1, 'Angel', 'Juarez', 'a.juarez@wypd.co.uk', 'password', 'user'),
(2, '', '', '', '', 'user');

-- --------------------------------------------------------

--
-- Table structure for table `calls`
--

CREATE TABLE `calls` (
  `id` int(11) NOT NULL,
  `caseId` int(11) DEFAULT NULL,
  `callerPhoneNumber` varchar(18) DEFAULT NULL,
  `receiverPhoneNumber` varchar(18) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `typeOfCall` varchar(10) DEFAULT NULL,
  `duration` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `calls`
--

INSERT INTO `calls` (`id`, `caseId`, `callerPhoneNumber`, `receiverPhoneNumber`, `date`, `time`, `typeOfCall`, `duration`) VALUES
(1193, 138, 'suka', '0', '1900-01-01', '00:00:00', 'Standard', '00:00:00'),
(1194, 138, '0', '0', '1900-01-01', '00:00:00', 'Standard', '00:00:00'),
(1195, 138, '0', '0', '1900-01-01', '00:00:00', 'Standard', '00:00:00'),
(1196, 138, '0', '0', '1900-01-01', '00:00:00', 'Standard', '00:00:00'),
(1197, 138, '0', '0', '1900-01-01', '00:00:00', 'Standard', '00:00:00'),
(1198, 138, '0', '0', '1900-01-01', '00:00:00', 'Standard', '00:00:00'),
(1199, 138, '0', '0', '1900-01-01', '00:00:00', 'Standard', '00:00:00'),
(1200, 138, '0', '0', '1900-01-01', '00:00:00', 'Standard', '00:00:00'),
(1201, 138, '0', '0', '1900-01-01', '00:00:00', 'Standard', '00:00:00'),
(1202, 138, '0', '0', '1900-01-01', '00:00:00', 'Standard', '00:00:00'),
(1203, 138, '0', '0', '1900-01-01', '00:00:00', 'Standard', '00:00:00'),
(1204, 138, '0', '0', '1900-01-01', '00:00:00', 'Standard', '00:00:00'),
(1205, 138, '0', '0', '1900-01-01', '00:00:00', 'Standard', '00:00:00');

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
(154, 'case1', 'Description', 'Investigating', '2017-02-14 17:58:02');

-- --------------------------------------------------------

--
-- Table structure for table `emailaddresses`
--

CREATE TABLE `emailaddresses` (
  `id` int(11) NOT NULL,
  `personID` int(11) DEFAULT NULL,
  `emailAddress` varchar(78) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `houseaddresses`
--

CREATE TABLE `houseaddresses` (
  `Id` int(11) NOT NULL,
  `personID` int(11) DEFAULT NULL,
  `houseNumber` varchar(5) DEFAULT NULL,
  `streetName` varchar(35) DEFAULT NULL,
  `town` varchar(35) DEFAULT NULL,
  `county` varchar(35) DEFAULT NULL,
  `postCode` varchar(8) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `houseaddresses`
--

INSERT INTO `houseaddresses` (`Id`, `personID`, `houseNumber`, `streetName`, `town`, `county`, `postCode`, `country`) VALUES
(1, 1, '16', 'Kendell Street', 'SHEFFORD WOODLANDS', 'West Berkshire', 'RG17 1XY', 'England'),
(2, 2, '36', 'Broad Street', 'LOWER GODNEY', 'Somerset', 'BA5 0QP', 'England'),
(3, 3, '62', 'Town Lane', 'SOUGHTON', 'Clwyd', 'CH7 5LL', 'Wales'),
(4, 4, '45', 'Mill Lane', 'CORNTOWN', 'South Glamorgan', 'CF35 0FY', 'Wales'),
(5, 5, '89', 'Gloucester Road', 'CLASHBAN', 'Scotland', 'CF35 0FY', 'Scotland'),
(6, 6, '38', 'Fraserburgh Rd', 'LICKEY END', 'Worcestershire', 'B60 3XH', 'England');

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

CREATE TABLE `notes` (
  `id` int(11) NOT NULL,
  `accountID` int(11) DEFAULT NULL,
  `caseID` int(11) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `data` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notes`
--

INSERT INTO `notes` (`id`, `accountID`, `caseID`, `title`, `date`, `data`) VALUES
(135, 1, 138, 'Case file', '2017-02-14', ' '),
(136, 1, 138, 'Case file', '2017-02-14', ' ');

-- --------------------------------------------------------

--
-- Table structure for table `people`
--

CREATE TABLE `people` (
  `Id` int(11) NOT NULL,
  `firstName` varchar(35) DEFAULT NULL,
  `lastName` varchar(35) DEFAULT NULL,
  `gender` enum('m','f') DEFAULT NULL,
  `dayOfBirth` tinyint(4) DEFAULT NULL,
  `monthOfBirth` tinyint(4) DEFAULT NULL,
  `yearOfBirth` smallint(6) DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `people`
--

INSERT INTO `people` (`Id`, `firstName`, `lastName`, `gender`, `dayOfBirth`, `monthOfBirth`, `yearOfBirth`, `dateOfBirth`) VALUES
(1, 'Spencer', 'Blackburn', 'm', 10, 12, 1936, '1936-12-10'),
(2, 'Jack', 'Sullivan', 'm', 2, 8, 1949, '1949-08-02'),
(3, 'Cameron', 'Cole', 'm', 17, 1, 1973, '1973-01-17'),
(4, 'Elizabeth', 'Powell', 'f', 19, 10, 1995, '1995-10-19'),
(5, 'Mason', 'Berry', 'm', 8, 1, 1993, '1993-01-08'),
(6, 'Ben', 'Clark', 'm', 18, 1, 1983, '1983-01-18');

-- --------------------------------------------------------

--
-- Table structure for table `phonenumbers`
--

CREATE TABLE `phonenumbers` (
  `id` int(11) NOT NULL,
  `personID` int(11) DEFAULT NULL,
  `phoneNumber` varchar(18) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `phonenumbers`
--

INSERT INTO `phonenumbers` (`id`, `personID`, `phoneNumber`) VALUES
(1, 1, NULL),
(2, 2, NULL),
(3, 3, NULL),
(4, 4, NULL),
(5, 5, NULL),
(6, 6, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `calls`
--
ALTER TABLE `calls`
  ADD PRIMARY KEY (`id`),
  ADD KEY `caseId` (`caseId`);

--
-- Indexes for table `cases`
--
ALTER TABLE `cases`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `emailaddresses`
--
ALTER TABLE `emailaddresses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `personID` (`personID`);

--
-- Indexes for table `houseaddresses`
--
ALTER TABLE `houseaddresses`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `personID` (`personID`);

--
-- Indexes for table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `accountID` (`accountID`),
  ADD KEY `caseID` (`caseID`);

--
-- Indexes for table `people`
--
ALTER TABLE `people`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `phonenumbers`
--
ALTER TABLE `phonenumbers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `personID` (`personID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `calls`
--
ALTER TABLE `calls`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1206;
--
-- AUTO_INCREMENT for table `cases`
--
ALTER TABLE `cases`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=155;
--
-- AUTO_INCREMENT for table `emailaddresses`
--
ALTER TABLE `emailaddresses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `houseaddresses`
--
ALTER TABLE `houseaddresses`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `notes`
--
ALTER TABLE `notes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=142;
--
-- AUTO_INCREMENT for table `people`
--
ALTER TABLE `people`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `phonenumbers`
--
ALTER TABLE `phonenumbers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `calls`
--
ALTER TABLE `calls`
  ADD CONSTRAINT `calls_ibfk_1` FOREIGN KEY (`caseId`) REFERENCES `cases` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `emailaddresses`
--
ALTER TABLE `emailaddresses`
  ADD CONSTRAINT `emailaddresses_ibfk_1` FOREIGN KEY (`personID`) REFERENCES `people` (`Id`) ON DELETE CASCADE;

--
-- Constraints for table `houseaddresses`
--
ALTER TABLE `houseaddresses`
  ADD CONSTRAINT `houseaddresses_ibfk_1` FOREIGN KEY (`personID`) REFERENCES `people` (`Id`) ON DELETE CASCADE;

--
-- Constraints for table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`accountID`) REFERENCES `accounts` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `notes_ibfk_2` FOREIGN KEY (`caseID`) REFERENCES `cases` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `phonenumbers`
--
ALTER TABLE `phonenumbers`
  ADD CONSTRAINT `phonenumbers_ibfk_1` FOREIGN KEY (`personID`) REFERENCES `people` (`Id`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
