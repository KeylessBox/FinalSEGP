-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 06, 2017 at 02:49 PM
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
-- Table structure for callsTable `accounts`
--

CREATE TABLE `accounts` (
  `Name` tinytext,
  `Surname` tinytext,
  `Email` tinytext,
  `Password` tinytext,
  `Privileges` tinytext
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for callsTable `accounts`
--

INSERT INTO `accounts` (`Name`, `Surname`, `Email`, `Password`, `Privileges`) VALUES
('Angel', 'Juarez', 'a.juarez@wypd.co.uk', 'password', 'user'),
('a', 'b', 'name', '1234', 'biba'),
('', '', '', '', 'user');

-- --------------------------------------------------------

--
-- Table structure for callsTable `calls`
--

CREATE TABLE `calls` (
  `Id` int(11) NOT NULL,
  `CaseId` int(11) DEFAULT NULL,
  `CallerPhoneNumber` varchar(18) DEFAULT NULL,
  `ReceiverPhoneNumber` varchar(18) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `Time` time DEFAULT NULL,
  `TypeOfCall` varchar(10) DEFAULT NULL,
  `Duration` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for callsTable `calls`
--

INSERT INTO `calls` (`Id`, `CaseId`, `CallerPhoneNumber`, `ReceiverPhoneNumber`, `Date`, `Time`, `TypeOfCall`, `Duration`) VALUES
(0, 1, '077 6109 8258', '078 0680 1334', '2016-07-26', '20:50:00', 'Standard', '11:00:00'),
(1, 1, '078 0680 1334', '077 3628 5886', '2016-03-19', '10:15:00', 'Standard', '15:00:00'),
(2, 1, '078 0680 1334', '070 0913 6953', '2016-05-16', '14:25:00', 'Standard', '13:00:00'),
(3, 1, '078 0680 1334', '070 6369 5729', '2016-06-01', '18:35:00', 'Standard', '03:00:00'),
(4, 1, '077 6052 1169', '078 0680 1334', '2016-06-11', '19:35:00', 'Standard', '20:00:00'),
(5, 1, '077 6109 8258', '078 0680 1334', '2016-07-26', '20:50:00', 'Standard', '20:00:00'),
(6, 1, '078 0680 1334', '077 3628 5886', '2016-03-19', '10:15:00', 'Standard', '20:00:00'),
(7, 1, '078 0680 1334', '070 0913 6953', '2016-05-16', '14:25:00', 'Standard', '13:00:00'),
(8, 1, '078 0680 1334', '070 6369 5729', '2016-06-01', '18:35:00', 'Standard', '03:00:00'),
(9, 1, '077 6052 1169', '078 0680 1334', '2016-06-11', '19:35:00', 'Standard', '20:00:00'),
(10, 1, '077 6109 8258', '078 0680 1334', '2016-07-26', '20:50:00', 'Standard', '11:00:00');

-- --------------------------------------------------------

--
-- Table structure for callsTable `cases`
--

CREATE TABLE `cases` (
  `Id` int(11) NOT NULL,
  `Name` varchar(35) DEFAULT NULL,
  `Details` varchar(255) DEFAULT NULL,
  `Status` enum('Investigating','Solved','Preliminary') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for callsTable `cases`
--

INSERT INTO `cases` (`Id`, `Name`, `Details`, `Status`) VALUES
(1, 'Cheeky Scar', 'A mass murderer in Essex terrorizes peaceful people', 'Investigating'),
(2, 'Little Rabbit', 'A girl found dead with her rabbit close by', 'Solved');

-- --------------------------------------------------------

--
-- Table structure for callsTable `emailaddresses`
--

CREATE TABLE `emailaddresses` (
  `Id` int(11) NOT NULL,
  `PersonID` int(11) DEFAULT NULL,
  `EmailAddress` varchar(78) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for callsTable `houseaddresses`
--

CREATE TABLE `houseaddresses` (
  `Id` int(11) NOT NULL,
  `PersonID` int(11) DEFAULT NULL,
  `HouseNumber` varchar(5) DEFAULT NULL,
  `StreetName` varchar(35) DEFAULT NULL,
  `Town` varchar(35) DEFAULT NULL,
  `County` varchar(35) DEFAULT NULL,
  `PostCode` varchar(8) DEFAULT NULL,
  `Country` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for callsTable `houseaddresses`
--

INSERT INTO `houseaddresses` (`Id`, `PersonID`, `HouseNumber`, `StreetName`, `Town`, `County`, `PostCode`, `Country`) VALUES
(1, 1, '16', 'Kendell Street', 'SHEFFORD WOODLANDS', 'West Berkshire', 'RG17 1XY', 'England'),
(2, 2, '36', 'Broad Street', 'LOWER GODNEY', 'Somerset', 'BA5 0QP', 'England'),
(3, 3, '62', 'Town Lane', 'SOUGHTON', 'Clwyd', 'CH7 5LL', 'Wales'),
(4, 4, '45', 'Mill Lane', 'CORNTOWN', 'South Glamorgan', 'CF35 0FY', 'Wales'),
(5, 5, '89', 'Gloucester Road', 'CLASHBAN', 'Scotland', 'CF35 0FY', 'Scotland'),
(6, 6, '38', 'Fraserburgh Rd', 'LICKEY END', 'Worcestershire', 'B60 3XH', 'England');

-- --------------------------------------------------------

--
-- Table structure for callsTable `notes`
--

CREATE TABLE `notes` (
  `Id` int(11) NOT NULL,
  `UserId` int(11) DEFAULT NULL,
  `CaseId` int(11) DEFAULT NULL,
  `Title` varchar(100) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `data` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for callsTable `people`
--

CREATE TABLE `people` (
  `Id` int(11) NOT NULL,
  `FirstName` varchar(35) DEFAULT NULL,
  `LastName` varchar(35) DEFAULT NULL,
  `Gender` enum('m','f') DEFAULT NULL,
  `DayOfBirth` tinyint(4) DEFAULT NULL,
  `MonthOfBirth` tinyint(4) DEFAULT NULL,
  `YearOfBirth` smallint(6) DEFAULT NULL,
  `DateOfBirth` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for callsTable `people`
--

INSERT INTO `people` (`Id`, `FirstName`, `LastName`, `Gender`, `DayOfBirth`, `MonthOfBirth`, `YearOfBirth`, `DateOfBirth`) VALUES
(1, 'Spencer', 'Blackburn', 'm', 10, 12, 1936, '1936-12-10'),
(2, 'Jack', 'Sullivan', 'm', 2, 8, 1949, '1949-08-02'),
(3, 'Cameron', 'Cole', 'm', 17, 1, 1973, '1973-01-17'),
(4, 'Elizabeth', 'Powell', 'f', 19, 10, 1995, '1995-10-19'),
(5, 'Mason', 'Berry', 'm', 8, 1, 1993, '1993-01-08'),
(6, 'Ben', 'Clark', 'm', 18, 1, 1983, '1983-01-18');

-- --------------------------------------------------------

--
-- Table structure for callsTable `phonenumbers`
--

CREATE TABLE `phonenumbers` (
  `Id` int(11) NOT NULL,
  `PersonID` int(11) DEFAULT NULL,
  `PhoneNumber` varchar(18) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for callsTable `phonenumbers`
--

INSERT INTO `phonenumbers` (`Id`, `PersonID`, `PhoneNumber`) VALUES
(1, 1, '078 0680 1334'),
(2, 2, '077 3628 5886'),
(3, 3, '070 0913 6953'),
(4, 4, '070 6369 5729'),
(5, 5, '077 6052 1169'),
(6, 6, '077 6109 8258');

-- --------------------------------------------------------

--
-- Table structure for callsTable `users`
--

CREATE TABLE `users` (
  `Id` int(11) NOT NULL,
  `Name` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for callsTable `users`
--

INSERT INTO `users` (`Id`, `Name`) VALUES
(1, ' Sherlock Holmes');

--
-- Indexes for dumped tables
--

--
-- Indexes for callsTable `calls`
--
ALTER TABLE `calls`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `CaseId` (`CaseId`);

--
-- Indexes for callsTable `cases`
--
ALTER TABLE `cases`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for callsTable `cases`
--
ALTER TABLE `cases`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
