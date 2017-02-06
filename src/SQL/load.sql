DROP TABLE IF EXISTS Notes, Calls, PhoneNumbers, EmailAddresses, HouseAddresses, People, Cases, Users, Accounts;

CREATE TABLE `Accounts` (
  `Name` tinytext,
  `Surname` tinytext,
  `Email` tinytext,
  `Password` tinytext,
  `Privileges` tinytext
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS Cases(Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(35),
    Details VARCHAR(255), Status Enum ('Investigating','Solved','Preliminary')) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS Users(Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(25)) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS Notes(Id INT PRIMARY KEY AUTO_INCREMENT,
    UserId INT, CaseId INT, Title VARCHAR(100), Date date, data VARCHAR(255),
    FOREIGN KEY(UserId) REFERENCES Users(Id) ON DELETE CASCADE,
    FOREIGN KEY(CaseId) REFERENCES Cases(Id) ON DELETE CASCADE) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS People(Id INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(35) DEFAULT NULL, LastName VARCHAR(35) DEFAULT NULL, Gender ENUM ('m','f'), DayOfBirth TINYINT DEFAULT NULL,
    MonthOfBirth TINYINT DEFAULT NULL, YearOfBirth SMALLINT DEFAULT NULL, DateOfBirth DATE DEFAULT NULL) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS HouseAddresses(Id INT PRIMARY KEY AUTO_INCREMENT,
    PersonID INT, HouseNumber VARCHAR(5), StreetName VARCHAR(35), Town VARCHAR(35), County VARCHAR(35), PostCode VARCHAR(8), Country VARCHAR(20),
    FOREIGN KEY(PersonID) REFERENCES People(Id) ON DELETE CASCADE) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS PhoneNumbers(Id INT PRIMARY KEY AUTO_INCREMENT,
    PersonID INT, PhoneNumber VARCHAR(18),
    FOREIGN KEY(PersonId) REFERENCES People(Id) ON DELETE CASCADE) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS EmailAddresses(Id INT PRIMARY KEY AUTO_INCREMENT,
    PersonID INT, EmailAddress VARCHAR(78),
    FOREIGN KEY(PersonID) REFERENCES People(Id) ON DELETE CASCADE) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS Calls(Id INT PRIMARY KEY AUTO_INCREMENT,
    CaseId INT, CallerPhoneNumber VARCHAR(18), ReceiverPhoneNumber VARCHAR(18), Date DATE, Time TIME, TypeOfCall VARCHAR(10), Duration TIME,
    FOREIGN KEY(CaseId) REFERENCES Cases(Id) ON DELETE CASCADE) ENGINE= InnoDB;


INSERT INTO `accounts`(`Name`, `Surname`, `Email`, `Password`, `Privileges`) VALUES
('Angel', 'Juarez', 'a.juarez@wypd.co.uk', 'password', 'user');

INSERT INTO Cases(Name, Details, Status) VALUES('Cheeky Scar', 'A mass murderer in Essex terrorizes peaceful people','Investigating');
INSERT INTO Cases(Name, Details, Status) VALUES('Little Rabbit', 'A girl found dead with her rabbit close by','Solved');

INSERT INTO Users(Name) VALUES(' Sherlock Holmes');

INSERT INTO Calls(CaseId, CallerPhoneNumber, ReceiverPhoneNumber, Date, Time, TypeOfCall, Duration)
VALUES(1,'078 0680 1334','077 3628 5886','2016/03/19','10:15','Standard', '20:00');
INSERT INTO Calls( CaseId, CallerPhoneNumber, ReceiverPhoneNumber, Date, Time, TypeOfCall, Duration)
VALUES(1,'078 0680 1334','070 0913 6953','2016/05/16','14:25','Standard', '13:00');
INSERT INTO Calls( CaseId, CallerPhoneNumber, ReceiverPhoneNumber, Date, Time, TypeOfCall, Duration)
VALUES(1,'078 0680 1334','070 6369 5729','2016/06/01','18:35','Standard', '03:00');
INSERT INTO Calls( CaseId, CallerPhoneNumber, ReceiverPhoneNumber, Date, Time, TypeOfCall, Duration)
VALUES(1,'077 6052 1169','078 0680 1334','2016/06/11','19:35','Standard', '20:00');
INSERT INTO Calls( CaseId, CallerPhoneNumber, ReceiverPhoneNumber, Date, Time, TypeOfCall, Duration)
VALUES(1,'077 6109 8258','078 0680 1334','2016/07/26','20:50','Standard', '11:00');

INSERT INTO People(FirstName, LastName, Gender, DayOfBirth, MonthOfBirth, YearOfBirth, DateOfBirth)
VALUES('Spencer','Blackburn','M','10','12','1936', '1936/12/10');
INSERT INTO HouseAddresses(PersonId, HouseNumber, StreetName, Town, County, PostCode, Country)
VALUES(1,'16','Kendell Street','SHEFFORD WOODLANDS','West Berkshire','RG17 1XY', 'England');

INSERT INTO People(FirstName, LastName, Gender, DayOfBirth, MonthOfBirth, YearOfBirth, DateOfBirth)
VALUES('Jack','Sullivan','M','02','08','1949', '1949/08/02');
INSERT INTO HouseAddresses(PersonId, HouseNumber, StreetName, Town, County, PostCode, Country)
VALUES(2,'36','Broad Street','LOWER GODNEY','Somerset','BA5 0QP', 'England');

INSERT INTO People(FirstName, LastName, Gender, DayOfBirth, MonthOfBirth, YearOfBirth, DateOfBirth)
VALUES('Cameron','Cole','M','17','01','1973', '1973/01/17');
INSERT INTO HouseAddresses(PersonId, HouseNumber, StreetName, Town, County, PostCode, Country)
VALUES(3,'62','Town Lane','SOUGHTON','Clwyd','CH7 5LL', 'Wales');

INSERT INTO People(FirstName, LastName, Gender, DayOfBirth, MonthOfBirth, YearOfBirth, DateOfBirth)
VALUES('Elizabeth','Powell','F','19','10','1995', '1995/10/19');
INSERT INTO HouseAddresses(PersonId, HouseNumber, StreetName, Town, County, PostCode, Country)
VALUES(4,'45','Mill Lane','CORNTOWN','South Glamorgan','CF35 0FY', 'Wales');

INSERT INTO People(FirstName, LastName, Gender, DayOfBirth, MonthOfBirth, YearOfBirth, DateOfBirth)
VALUES('Mason','Berry','M','08','01','1993', '1993/01/08');
INSERT INTO HouseAddresses(PersonId, HouseNumber, StreetName, Town, County, PostCode, Country)
VALUES(5,'89','Gloucester Road','CLASHBAN','Scotland','CF35 0FY', 'Scotland');

INSERT INTO People(FirstName, LastName, Gender, DayOfBirth, MonthOfBirth, YearOfBirth, DateOfBirth)
VALUES('Ben','Clark','M','18','01','1983', '1983/01/18');
INSERT INTO HouseAddresses(PersonId, HouseNumber, StreetName, Town, County, PostCode, Country)
VALUES(6,'38','Fraserburgh Rd','LICKEY END','Worcestershire','B60 3XH', 'England');


INSERT INTO PhoneNumbers (PersonId)
VALUES (1);
UPDATE PhoneNumbers SET PhoneNumber = (SELECT Calls.CallerPhoneNumber FROM Calls WHERE Id = 1)
WHERE PersonId = 1;

INSERT INTO PhoneNumbers (PersonId)
VALUES (2);
UPDATE PhoneNumbers SET PhoneNumber = (SELECT Calls.ReceiverPhoneNumber FROM Calls WHERE Id = 1)
WHERE PersonId = 2;

INSERT INTO PhoneNumbers (PersonId)
VALUES (3);
UPDATE PhoneNumbers SET PhoneNumber = (SELECT Calls.ReceiverPhoneNumber FROM Calls Where Id = 2)
WHERE PersonId = 3;

INSERT INTO PhoneNumbers (PersonId)
VALUES (4);
UPDATE PhoneNumbers SET PhoneNumber = (SELECT Calls.ReceiverPhoneNumber FROM Calls Where Id = 3)
WHERE PersonId = 4;

INSERT INTO PhoneNumbers (PersonId)
VALUES (5);
UPDATE PhoneNumbers SET PhoneNumber = (SELECT Calls.CallerPhoneNumber FROM Calls Where Id = 4)
WHERE PersonId = 5;

INSERT INTO PhoneNumbers (PersonId)
VALUES (6);
UPDATE PhoneNumbers SET PhoneNumber = (SELECT Calls.CallerPhoneNumber FROM Calls Where Id = 5)
WHERE PersonId = 6;






