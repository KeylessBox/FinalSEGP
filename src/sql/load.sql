DROP TABLE IF EXISTS notes, calls, phoneNumbers, emailAddresses, houseAddresses, people, cases, users, accounts;

CREATE TABLE `accounts` (id INT PRIMARY KEY AUTO_INCREMENT,
  `name` tinytext,
  `surname` tinytext,
  `email` tinytext,
  `password` tinytext,
  `privileges` tinytext
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS cases(id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(35),
    details VARCHAR(255), status ENUM ('Investigating','Solved','Preliminary'), date DATETIME DEFAULT NULL ) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS notes(id INT PRIMARY KEY AUTO_INCREMENT,
    accountID INT, caseID INT, title VARCHAR(100), date DATE, data VARCHAR(255),
    FOREIGN KEY(accountID) REFERENCES accounts(id) ON DELETE CASCADE,
    FOREIGN KEY(caseID) REFERENCES cases(id) ON DELETE CASCADE) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS people(Id INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(35) DEFAULT NULL, lastName VARCHAR(35) DEFAULT NULL, gender ENUM ('m','f'), dayOfBirth TINYINT DEFAULT NULL,
    monthOfBirth TINYINT DEFAULT NULL, yearOfBirth SMALLINT DEFAULT NULL, dateOfBirth DATE DEFAULT NULL) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS houseAddresses(Id INT PRIMARY KEY AUTO_INCREMENT,
    personID INT, houseNumber VARCHAR(5), streetName VARCHAR(35), town VARCHAR(35), county VARCHAR(35), postCode VARCHAR(8), country VARCHAR(20),
    FOREIGN KEY(personID) REFERENCES people(id) ON DELETE CASCADE) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS phoneNumbers(id INT PRIMARY KEY AUTO_INCREMENT,
    personID INT, phoneNumber VARCHAR(18),
    FOREIGN KEY(personID) REFERENCES people(id) ON DELETE CASCADE) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS emailAddresses(id INT PRIMARY KEY AUTO_INCREMENT,
    personID INT, emailAddress VARCHAR(78),
    FOREIGN KEY(personID) REFERENCES people(id) ON DELETE CASCADE) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS calls(id INT PRIMARY KEY AUTO_INCREMENT,
    caseId INT, origin VARCHAR(18), destination VARCHAR(18), date DATE, time TIME, typeOfCall VARCHAR(10), duration TIME,
    FOREIGN KEY(caseId) REFERENCES cases(id) ON DELETE CASCADE) ENGINE= InnoDB;

INSERT INTO `accounts`(`name`, `surname`, `email`, `password`, `privileges`) VALUES
('Angel', 'Juarez', 'a.juarez@wypd.co.uk', 'password', 'user'),
('', '', '', '', 'user');

INSERT INTO cases(name, details, status, date) VALUES
    ('Cheeky Scar', 'A mass murderer in Essex terrorizes peaceful people','Investigating','2017-02-14 17:52:20'),
    ('Little Rabbit', 'A girl found dead with her rabbit close by', 'Solved','2017-02-14 17:52:20'),
    ('Black Shoe', 'Description', 'Investigating','2017-02-14 17:52:20'),
    ('Red Wedding', 'Description', 'Investigating','2017-02-14 17:52:20'),
    ('Bloody locker', 'Description', 'Investigating','2017-02-14 17:52:20'),
    ('Forest trail', 'Description', 'Investigating','2017-02-14 17:52:20'),
    ('Donuts of skin', 'Description', 'Investigating','2017-02-14 17:52:20'),
    ('Python in the house', 'Description', 'Investigating','2017-02-14 17:52:20');


INSERT INTO people(firstName, lastName, gender, dayOfBirth, monthOfBirth, yearOfBirth, dateOfBirth) VALUES
    ('Spencer','Blackburn','M','10','12','1936', '1936/12/10'),
    ('Jack','Sullivan','M','02','08','1949', '1949/08/02'),
    ('Cameron','Cole','M','17','01','1973', '1973/01/17'),
    ('Elizabeth','Powell','F','19','10','1995', '1995/10/19'),
    ('Mason','Berry','M','08','01','1993', '1993/01/08'),
    ('Ben','Clark','M','18','01','1983', '1983/01/18');



INSERT INTO houseAddresses(personId, houseNumber, streetName, town, county, postCode, country) VALUES
    (1,'16','Kendell Street','SHEFFORD WOODLANDS','West Berkshire','RG17 1XY', 'England'),
    (2,'36','Broad Street','LOWER GODNEY','Somerset','BA5 0QP', 'England'),
    (3,'62','Town Lane','SOUGHTON','Clwyd','CH7 5LL', 'Wales'),
    (4,'45','Mill Lane','CORNTOWN','South Glamorgan','CF35 0FY', 'Wales'),
    (5,'89','Gloucester Road','CLASHBAN','Scotland','CF35 0FY', 'Scotland'),
    (6,'38','Fraserburgh Rd','LICKEY END','Worcestershire','B60 3XH', 'England');


INSERT INTO phoneNumbers (personId)
VALUES (1);
UPDATE phoneNumbers SET phoneNumber = (SELECT calls.origin FROM calls WHERE id = 1)
WHERE personId = 1;

INSERT INTO phoneNumbers (personId)
VALUES (2);
UPDATE phoneNumbers SET phoneNumber = (SELECT calls.destination FROM calls WHERE Id = 1)
WHERE personId = 2;

INSERT INTO phoneNumbers (personId)
VALUES (3);
UPDATE phoneNumbers SET phoneNumber = (SELECT calls.destination FROM calls Where Id = 2)
WHERE personId = 3;

INSERT INTO phoneNumbers (personId)
VALUES (4);
UPDATE phoneNumbers SET phoneNumber = (SELECT calls.destination FROM calls Where Id = 3)
WHERE personId = 4;

INSERT INTO phoneNumbers (personId)
VALUES (5);
UPDATE phoneNumbers SET phoneNumber = (SELECT calls.origin FROM calls Where Id = 4)
WHERE personId = 5;

INSERT INTO phoneNumbers (personId)
VALUES (6);
UPDATE phoneNumbers SET phoneNumber = (SELECT calls.origin FROM calls Where Id = 5)
WHERE personId = 6;



INSERT INTO calls(caseId, origin, destination, date, time, typeOfCall, duration) VALUES
    (1,'078 0680 1334','077 3628 5886','2016/03/19','10:15','Standard', '20:00'),
    (1,'078 0680 1334','070 0913 6953','2016/05/16','14:25','Standard', '13:00'),
    (1,'078 0680 1334','070 6369 5729','2016/06/01','18:35','Standard', '03:00'),
    (1,'077 6052 1169','078 0680 1334','2016/06/11','19:35','Standard', '20:00'),
    (1,'077 6109 8258','078 0680 1334','2016/07/26','20:50','Standard', '11:00');
