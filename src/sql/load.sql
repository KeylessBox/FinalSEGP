DROP TABLE IF EXISTS notes, calls, phoneNumbers, cases, users, accounts;

CREATE TABLE `accounts` (id INT PRIMARY KEY AUTO_INCREMENT,
  `name` tinytext,
  `surname` tinytext,
  `email` tinytext,
  `password` tinytext,
  `privileges` tinytext
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS cases(id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(35), details VARCHAR(255), status ENUM ('New','Done'), date DATETIME DEFAULT NULL ) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS notes(id INT PRIMARY KEY AUTO_INCREMENT,
    caseID INT, title VARCHAR(100), date DATE, data VARCHAR(255),
    FOREIGN KEY(caseID) REFERENCES cases(id) ON DELETE CASCADE) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS phoneNumbers(id INT PRIMARY KEY AUTO_INCREMENT,
    personName VARCHAR(80), phoneNumber VARCHAR(18)) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS calls(id INT PRIMARY KEY AUTO_INCREMENT,
    caseId INT, origin VARCHAR(18), destination VARCHAR(18), date DATE, time TIME, callType VARCHAR(10), duration TIME,
    FOREIGN KEY(caseId) REFERENCES cases(id) ON DELETE CASCADE) ENGINE= InnoDB;

INSERT INTO cases(name, details, status, date) VALUES
    ('Cheeky Scar', 'A mass murderer in Essex terrorizes peaceful people','New','2017-02-14 17:52:20'),
    ('Little Rabbit', 'A girl found dead with her rabbit close by', 'New','2017-02-14 17:52:20'),
    ('Black Shoe', 'Description', 'New','2017-02-14 17:52:20'),
    ('Red Wedding', 'Description', 'Done','2017-02-14 17:52:20'),
    ('Bloody locker', 'Description', 'Done','2017-02-14 17:52:20'),
    ('Forest trail', 'Description', 'Done','2017-02-14 17:52:20'),
    ('Donuts of skin', 'Description', 'Done','2017-02-14 17:52:20'),
    ('Python in the house', 'Description', 'Done','2017-02-14 17:52:20');

INSERT INTO phoneNumbers (personName, phoneNumber) VALUES
    ('Spencer Blackburn','078 0680 1334'),
    ('Jack Sullivan', '077 3628 5886'),
    ('Cameron Cole', '070 0913 6953'),
    ('Elizabeth Powell','070 6369 5729'),
    ('Mason Berry','077 6052 1169'),
    ('Ben Clark','077 6109 8258');

INSERT INTO calls(caseId, origin, destination, date, time, callType, duration) VALUES
    (1,'078 0680 1334','077 3628 5886','2016/03/19','10:15','Standard', '20:00'),
    (1,'078 0680 1334','070 0913 6953','2016/05/16','14:25','Standard', '13:00'),
    (1,'078 0680 1334','070 6369 5729','2016/06/01','18:35','Standard', '03:00'),
    (1,'077 6052 1169','078 0680 1334','2016/06/11','19:35','Standard', '20:00'),
    (1,'077 6109 8258','078 0680 1334','2016/07/26','20:50','Standard', '11:00');