-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 05, 2017 at 02:54 PM
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
CREATE DATABASE IF NOT EXISTS `investigationsdb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `investigationsdb`;

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `Name` tinytext,
  `Surname` tinytext,
  `Email` tinytext,
  `Password` tinytext,
  `Privileges` tinytext
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`Name`, `Surname`, `Email`, `Password`, `Privileges`) VALUES
('Angel', 'Juarez', 'a.juarez@wypd.co.uk', 'password', 'user'),
('a', 'b', 'name', '1234', 'biba');

-- --------------------------------------------------------

--
-- Table structure for table `calls`
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
-- Dumping data for table `calls`
--

INSERT INTO `calls` (`Id`, `CaseId`, `CallerPhoneNumber`, `ReceiverPhoneNumber`, `Date`, `Time`, `TypeOfCall`, `Duration`) VALUES
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
-- Table structure for table `cases`
--

CREATE TABLE `cases` (
  `Id` int(11) NOT NULL,
  `Name` varchar(35) DEFAULT NULL,
  `Details` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cases`
--

INSERT INTO `cases` (`Id`, `Name`, `Details`) VALUES
(1, 'Cheeky Scar', 'A mass murderer in Essex terrorizes peaceful people'),
(2, 'Little Rabbit', 'A girl found dead with her rabbit close by');

-- --------------------------------------------------------

--
-- Table structure for table `emailaddresses`
--

CREATE TABLE `emailaddresses` (
  `Id` int(11) NOT NULL,
  `PersonID` int(11) DEFAULT NULL,
  `EmailAddress` varchar(78) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `houseaddresses`
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
-- Dumping data for table `houseaddresses`
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
-- Table structure for table `notes`
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
-- Table structure for table `people`
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
-- Dumping data for table `people`
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
-- Table structure for table `phonenumbers`
--

CREATE TABLE `phonenumbers` (
  `Id` int(11) NOT NULL,
  `PersonID` int(11) DEFAULT NULL,
  `PhoneNumber` varchar(18) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `phonenumbers`
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
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `Id` int(11) NOT NULL,
  `Name` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`Id`, `Name`) VALUES
(1, ' Sherlock Holmes');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `calls`
--
ALTER TABLE `calls`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `CaseId` (`CaseId`);

--
-- Indexes for table `cases`
--
ALTER TABLE `cases`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `emailaddresses`
--
ALTER TABLE `emailaddresses`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `PersonID` (`PersonID`);

--
-- Indexes for table `houseaddresses`
--
ALTER TABLE `houseaddresses`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `PersonID` (`PersonID`);

--
-- Indexes for table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `UserId` (`UserId`),
  ADD KEY `CaseId` (`CaseId`);

--
-- Indexes for table `people`
--
ALTER TABLE `people`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `phonenumbers`
--
ALTER TABLE `phonenumbers`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `PersonID` (`PersonID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `calls`
--
ALTER TABLE `calls`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `cases`
--
ALTER TABLE `cases`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `emailaddresses`
--
ALTER TABLE `emailaddresses`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `houseaddresses`
--
ALTER TABLE `houseaddresses`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `notes`
--
ALTER TABLE `notes`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `people`
--
ALTER TABLE `people`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `phonenumbers`
--
ALTER TABLE `phonenumbers`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `calls`
--
ALTER TABLE `calls`
  ADD CONSTRAINT `calls_ibfk_1` FOREIGN KEY (`CaseId`) REFERENCES `cases` (`Id`) ON DELETE CASCADE;

--
-- Constraints for table `emailaddresses`
--
ALTER TABLE `emailaddresses`
  ADD CONSTRAINT `emailaddresses_ibfk_1` FOREIGN KEY (`PersonID`) REFERENCES `people` (`Id`) ON DELETE CASCADE;

--
-- Constraints for table `houseaddresses`
--
ALTER TABLE `houseaddresses`
  ADD CONSTRAINT `houseaddresses_ibfk_1` FOREIGN KEY (`PersonID`) REFERENCES `people` (`Id`) ON DELETE CASCADE;

--
-- Constraints for table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `users` (`Id`) ON DELETE CASCADE,
  ADD CONSTRAINT `notes_ibfk_2` FOREIGN KEY (`CaseId`) REFERENCES `cases` (`Id`) ON DELETE CASCADE;

--
-- Constraints for table `phonenumbers`
--
ALTER TABLE `phonenumbers`
  ADD CONSTRAINT `phonenumbers_ibfk_1` FOREIGN KEY (`PersonID`) REFERENCES `people` (`Id`) ON DELETE CASCADE;
--
-- Database: `phpmyadmin`
--
CREATE DATABASE IF NOT EXISTS `phpmyadmin` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `phpmyadmin`;

-- --------------------------------------------------------

--
-- Table structure for table `pma__bookmark`
--

CREATE TABLE `pma__bookmark` (
  `id` int(11) NOT NULL,
  `dbase` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `user` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `label` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `query` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- --------------------------------------------------------

--
-- Table structure for table `pma__central_columns`
--

CREATE TABLE `pma__central_columns` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `col_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `col_type` varchar(64) COLLATE utf8_bin NOT NULL,
  `col_length` text COLLATE utf8_bin,
  `col_collation` varchar(64) COLLATE utf8_bin NOT NULL,
  `col_isNull` tinyint(1) NOT NULL,
  `col_extra` varchar(255) COLLATE utf8_bin DEFAULT '',
  `col_default` text COLLATE utf8_bin
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Central list of columns';

-- --------------------------------------------------------

--
-- Table structure for table `pma__column_info`
--

CREATE TABLE `pma__column_info` (
  `id` int(5) UNSIGNED NOT NULL,
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `column_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `comment` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `mimetype` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `transformation` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `transformation_options` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `input_transformation` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `input_transformation_options` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Column information for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__designer_settings`
--

CREATE TABLE `pma__designer_settings` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `settings_data` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Settings related to Designer';

-- --------------------------------------------------------

--
-- Table structure for table `pma__export_templates`
--

CREATE TABLE `pma__export_templates` (
  `id` int(5) UNSIGNED NOT NULL,
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `export_type` varchar(10) COLLATE utf8_bin NOT NULL,
  `template_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `template_data` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Saved export templates';

-- --------------------------------------------------------

--
-- Table structure for table `pma__favorite`
--

CREATE TABLE `pma__favorite` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `tables` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Favorite tables';

-- --------------------------------------------------------

--
-- Table structure for table `pma__history`
--

CREATE TABLE `pma__history` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `username` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `db` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `table` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `timevalue` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sqlquery` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='SQL history for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__navigationhiding`
--

CREATE TABLE `pma__navigationhiding` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `item_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `item_type` varchar(64) COLLATE utf8_bin NOT NULL,
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Hidden items of navigation tree';

-- --------------------------------------------------------

--
-- Table structure for table `pma__pdf_pages`
--

CREATE TABLE `pma__pdf_pages` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `page_nr` int(10) UNSIGNED NOT NULL,
  `page_descr` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='PDF relation pages for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__recent`
--

CREATE TABLE `pma__recent` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `tables` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Recently accessed tables';

--
-- Dumping data for table `pma__recent`
--

INSERT INTO `pma__recent` (`username`, `tables`) VALUES
('root', '[{"db":"investigationsdb","table":"accounts"},{"db":"investigationsdb","table":"calls"},{"db":"investigationsdb","table":"cases"},{"db":"investigationsdb","table":"emailaddresses"},{"db":"investigationsdb","table":"houseaddresses"},{"db":"investigationsdb","table":"notes"},{"db":"investigationsdb","table":"people"},{"db":"tutorgroups","table":"newview"},{"db":"tutorgroups","table":"studentattached"},{"db":"tutorgroups","table":"myview"}]');

-- --------------------------------------------------------

--
-- Table structure for table `pma__relation`
--

CREATE TABLE `pma__relation` (
  `master_db` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `master_table` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `master_field` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `foreign_db` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `foreign_table` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `foreign_field` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Relation table';

-- --------------------------------------------------------

--
-- Table structure for table `pma__savedsearches`
--

CREATE TABLE `pma__savedsearches` (
  `id` int(5) UNSIGNED NOT NULL,
  `username` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `search_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `search_data` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Saved searches';

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_coords`
--

CREATE TABLE `pma__table_coords` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `pdf_page_number` int(11) NOT NULL DEFAULT '0',
  `x` float UNSIGNED NOT NULL DEFAULT '0',
  `y` float UNSIGNED NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table coordinates for phpMyAdmin PDF output';

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_info`
--

CREATE TABLE `pma__table_info` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `display_field` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table information for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_uiprefs`
--

CREATE TABLE `pma__table_uiprefs` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `prefs` text COLLATE utf8_bin NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Tables'' UI preferences';

--
-- Dumping data for table `pma__table_uiprefs`
--

INSERT INTO `pma__table_uiprefs` (`username`, `db_name`, `table_name`, `prefs`, `last_update`) VALUES
('root', 'tutorgroups', 'tutors', '{"sorted_col":"`tutors`.`tutorUB`  ASC"}', '2016-11-04 00:21:14');

-- --------------------------------------------------------

--
-- Table structure for table `pma__tracking`
--

CREATE TABLE `pma__tracking` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `version` int(10) UNSIGNED NOT NULL,
  `date_created` datetime NOT NULL,
  `date_updated` datetime NOT NULL,
  `schema_snapshot` text COLLATE utf8_bin NOT NULL,
  `schema_sql` text COLLATE utf8_bin,
  `data_sql` longtext COLLATE utf8_bin,
  `tracking` set('UPDATE','REPLACE','INSERT','DELETE','TRUNCATE','CREATE DATABASE','ALTER DATABASE','DROP DATABASE','CREATE TABLE','ALTER TABLE','RENAME TABLE','DROP TABLE','CREATE INDEX','DROP INDEX','CREATE VIEW','ALTER VIEW','DROP VIEW') COLLATE utf8_bin DEFAULT NULL,
  `tracking_active` int(1) UNSIGNED NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Database changes tracking for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__userconfig`
--

CREATE TABLE `pma__userconfig` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `timevalue` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `config_data` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User preferences storage for phpMyAdmin';

--
-- Dumping data for table `pma__userconfig`
--

INSERT INTO `pma__userconfig` (`username`, `timevalue`, `config_data`) VALUES
('root', '2016-11-02 16:47:28', '{"collation_connection":"utf8mb4_unicode_ci"}');

-- --------------------------------------------------------

--
-- Table structure for table `pma__usergroups`
--

CREATE TABLE `pma__usergroups` (
  `usergroup` varchar(64) COLLATE utf8_bin NOT NULL,
  `tab` varchar(64) COLLATE utf8_bin NOT NULL,
  `allowed` enum('Y','N') COLLATE utf8_bin NOT NULL DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User groups with configured menu items';

-- --------------------------------------------------------

--
-- Table structure for table `pma__users`
--

CREATE TABLE `pma__users` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `usergroup` varchar(64) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Users and their assignments to user groups';

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pma__central_columns`
--
ALTER TABLE `pma__central_columns`
  ADD PRIMARY KEY (`db_name`,`col_name`);

--
-- Indexes for table `pma__column_info`
--
ALTER TABLE `pma__column_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `db_name` (`db_name`,`table_name`,`column_name`);

--
-- Indexes for table `pma__designer_settings`
--
ALTER TABLE `pma__designer_settings`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_user_type_template` (`username`,`export_type`,`template_name`);

--
-- Indexes for table `pma__favorite`
--
ALTER TABLE `pma__favorite`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__history`
--
ALTER TABLE `pma__history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`,`db`,`table`,`timevalue`);

--
-- Indexes for table `pma__navigationhiding`
--
ALTER TABLE `pma__navigationhiding`
  ADD PRIMARY KEY (`username`,`item_name`,`item_type`,`db_name`,`table_name`);

--
-- Indexes for table `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  ADD PRIMARY KEY (`page_nr`),
  ADD KEY `db_name` (`db_name`);

--
-- Indexes for table `pma__recent`
--
ALTER TABLE `pma__recent`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__relation`
--
ALTER TABLE `pma__relation`
  ADD PRIMARY KEY (`master_db`,`master_table`,`master_field`),
  ADD KEY `foreign_field` (`foreign_db`,`foreign_table`);

--
-- Indexes for table `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_savedsearches_username_dbname` (`username`,`db_name`,`search_name`);

--
-- Indexes for table `pma__table_coords`
--
ALTER TABLE `pma__table_coords`
  ADD PRIMARY KEY (`db_name`,`table_name`,`pdf_page_number`);

--
-- Indexes for table `pma__table_info`
--
ALTER TABLE `pma__table_info`
  ADD PRIMARY KEY (`db_name`,`table_name`);

--
-- Indexes for table `pma__table_uiprefs`
--
ALTER TABLE `pma__table_uiprefs`
  ADD PRIMARY KEY (`username`,`db_name`,`table_name`);

--
-- Indexes for table `pma__tracking`
--
ALTER TABLE `pma__tracking`
  ADD PRIMARY KEY (`db_name`,`table_name`,`version`);

--
-- Indexes for table `pma__userconfig`
--
ALTER TABLE `pma__userconfig`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__usergroups`
--
ALTER TABLE `pma__usergroups`
  ADD PRIMARY KEY (`usergroup`,`tab`,`allowed`);

--
-- Indexes for table `pma__users`
--
ALTER TABLE `pma__users`
  ADD PRIMARY KEY (`username`,`usergroup`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `pma__column_info`
--
ALTER TABLE `pma__column_info`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `pma__history`
--
ALTER TABLE `pma__history`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  MODIFY `page_nr` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;--
-- Database: `test`
--
CREATE DATABASE IF NOT EXISTS `test` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `test`;
--
-- Database: `tutorgroups`
--
CREATE DATABASE IF NOT EXISTS `tutorgroups` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `tutorgroups`;

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `courseID` int(11) NOT NULL,
  `courseName` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`courseID`, `courseName`) VALUES
(1, 'Computer Science for Business BSc (Hons)'),
(2, 'Computer Science BSc (Hons)'),
(3, 'Computer Science for Games BSc (Hons)'),
(4, 'Computer Science for Cyber Security BSc '),
(5, 'Software Engineering BEng (Hons)'),
(6, 'Cyber Security MSc'),
(7, 'Big Data Science and Technology MSc');

-- --------------------------------------------------------

--
-- Stand-in structure for view `newview`
--
CREATE TABLE `newview` (
`studentName` varchar(50)
,`studentUB` int(11)
,`studentMAIL` varchar(50)
,`tutorName` varchar(30)
,`tutorMAIL` varchar(30)
,`tutorUB` int(11)
,`courseName` varchar(40)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `studentattached`
--
CREATE TABLE `studentattached` (
`tutorName` varchar(30)
,`COUNT(s.studentName)` bigint(21)
);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `studentID` int(11) NOT NULL,
  `studentName` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `studentUB` int(11) NOT NULL,
  `courseID` varchar(60) NOT NULL,
  `yearOfStudy` int(11) NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `tutorID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`studentID`, `studentName`, `studentUB`, `courseID`, `yearOfStudy`, `email`, `tutorID`) VALUES
(2, 'Cherie Shaun', 25101045, '3', 2, 'cherie.s@bradford.ac.uk', 56),
(3, 'Devereux Rylee', 15025896, '1', 3, 'devereux.r@bradford.ac.uk', 2),
(4, 'Winton Mat', 45896587, '2', 3, 'winston.m@bradford.ac.uk', 2),
(5, 'Belle Norene', 74125050, '3', 2, 'belle.n@bradford.ac.uk', 2),
(6, 'Braidy Moira', 15252541, '2', 3, 'braidy.m@bradford.ac.uk', 1),
(7, 'Brooke Prissy', 17858965, 'Computer Science for Games BSc (Hons)', 1, 'brooke.p@bradford.ac.uk', 3),
(8, 'Marta Elfleda', 53521036, 'Computer Science for Games BSc (Hons)', 2, 'marta.e@bradford.ac.uk', 3),
(9, 'Dreda Percival', 15017963, 'Computer Science BSc (Hons)', 3, 'dreda.p@bradford.ac.uk', 7),
(10, 'Gareth Roz', 12963544, 'Big Data Science and Technology MSc', 1, 'gareth.r@bradford.ac.uk', 7),
(11, 'Peter Wystan', 12545698, 'Big Data Science and Technology MSc', 1, 'peter.w@bradford.ac.uk', 7),
(12, 'Sean Preston', 12587493, 'Software Engineering BEng (Hons)', 1, 'sean.p@bradford.ac.uk', 7),
(13, 'Grover Kendra', 85896541, 'Cyber Security MSc', 2, 'grover.k@bradford.ac.uk', 1),
(14, 'Periklis Alin', 98525410, 'Software Engineering BEng (Hons)', 1, 'periklis.a@bradford.ac.uk', 1),
(15, 'Shaylyn Lenard', 152032520, 'Software Engineering BEng (Hons)', 2, 'shaylyn.l@bradford.ac.uk', 1),
(16, 'Thorburn Hunter', 12305675, 'Software Engineering BEng (Hons)', 3, 'thorburn.h@bradford.ac.uk', 1),
(17, 'Aristeidis China', 15201254, 'Computer Science for Business BSc (Hons)', 2, 'aristeidis.c@bradford.ac.uk', 6),
(18, 'Ioanna Antonia', 12014158, 'Computer Science for Cyber Security BSc', 1, 'ioanna.a@bradford.ac.uk', 6),
(19, 'Jerald Bennie', 16250203, 'Computer Science for Business BSc (Hons)', 2, 'jerald.b@bradford.ac.uk', 6),
(20, 'Johnathan Andrei', 20125369, 'Cyber Security MSc', 3, 'johnathan.a@bradford.ac.uk', 6),
(21, 'Billy Woodrow', 87458923, 'Big Data Science and Technology MSc', 3, 'billy.w@bradford.ac.uk', 5),
(22, 'Charles Chu', 15935826, 'Computer Science BSc (Hons)', 1, 'charles.c@bradford.ac.uk', 5),
(23, 'Lazar Zachariah', 15017963, 'Big Data Science and Technology MSc', 2, 'lazar.z@bradford.ac.uk', 5),
(26, 'Aura Kaisa', 65254178, 'Computer Science for Business BSc (Hons)', 3, 'aura.k@bradford.ac.uk', 232),
(27, 'Cristen Bobbi', 15018243, 'Computer Science BSc (Hons)', 2, 'cristen.b@bradford.ac.uk', 223),
(28, 'Mahalia Akim', 15018243, 'Big Data Science and Technology MSc', 3, 'mahalia.a@bradford.ac.uk', 2),
(29, 'Reinier Orion', 12502304, 'Cyber Security MSc', 3, 'reiner.o@bradford.ac.uk', 2),
(30, 'Alad', 12510364, 'Cyber Security MSc', 1, 'vladislava.gexample@bradford.ac%.uk', 2),
(45, 'Samson Serafim', 14825669, 'Computer Science BSc (Hons)', 2, 'samson.s@bradford.ac.uk', 5),
(52, 'Abad', 12510364, 'Cyber Security MSc', 1, 'vladislava.gexample@bradford.ac%.uk', 2),
(67, 'Robin Kyleigh', 12523658, 'Cyber Security MSc', 1, 'robin.k@bradford.ac.uk', 5),
(68, 'Cristen Bobbi', 15018243, 'Computer Science BSc (Hons)', 2, 'cristen.b@bradford.ac.uk', 223),
(69, 'testName', 23, '1', 2, 'aleks@gmial.com', 2);

-- --------------------------------------------------------

--
-- Table structure for table `tutors`
--

CREATE TABLE `tutors` (
  `tutorID` int(11) NOT NULL,
  `tutorName` varchar(30) NOT NULL,
  `tutorUB` int(11) NOT NULL DEFAULT '15010000',
  `email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='tutors table';

--
-- Dumping data for table `tutors`
--

INSERT INTO `tutors` (`tutorID`, `tutorName`, `tutorUB`, `email`) VALUES
(1, 'Aleks Retardinio', 15012058, 'astserba@gmail.com'),
(2, 'Andrei Retard', 15012058, 'testamili@gmail.com'),
(232, 'fdsa', 15012058, 'example@example.com');

-- --------------------------------------------------------

--
-- Structure for view `newview`
--
DROP TABLE IF EXISTS `newview`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `newview`  AS  select distinct `s`.`studentName` AS `studentName`,`s`.`studentUB` AS `studentUB`,`s`.`email` AS `studentMAIL`,`t`.`tutorName` AS `tutorName`,`t`.`email` AS `tutorMAIL`,`t`.`tutorUB` AS `tutorUB`,`c`.`courseName` AS `courseName` from ((`students` `s` join `tutors` `t`) join `courses` `c`) where ((`s`.`tutorID` = `t`.`tutorID`) and (`s`.`courseID` = `c`.`courseID`)) order by `t`.`tutorName` desc ;

-- --------------------------------------------------------

--
-- Structure for view `studentattached`
--
DROP TABLE IF EXISTS `studentattached`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `studentattached`  AS  select `t`.`tutorName` AS `tutorName`,count(`s`.`studentName`) AS `COUNT(s.studentName)` from (`tutors` `t` join `students` `s`) where (`s`.`tutorID` = `t`.`tutorID`) group by `t`.`tutorName` order by `t`.`tutorName` ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`courseID`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`studentID`);

--
-- Indexes for table `tutors`
--
ALTER TABLE `tutors`
  ADD PRIMARY KEY (`tutorID`),
  ADD UNIQUE KEY `tutorIndex` (`tutorName`,`tutorUB`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `courseID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `studentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;
--
-- AUTO_INCREMENT for table `tutors`
--
ALTER TABLE `tutors`
  MODIFY `tutorID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=233;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
