DROP TABLE IF EXISTS Cases, Users, Notes, Info;

CREATE TABLE IF NOT EXISTS Cases(Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(25),
    Details VARCHAR(255)) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS Users (Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(25)) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS Notes(Id INT PRIMARY KEY AUTO_INCREMENT,
    UserId INT, CaseId INT, Title VARCHAR(100), Date date, data VARCHAR(255),
    FOREIGN KEY(UserId) REFERENCES Users(Id) ON DELETE CASCADE,
    FOREIGN KEY(CaseId) REFERENCES Cases(Id) ON DELETE CASCADE) ENGINE= InnoDB;

CREATE TABLE IF NOT EXISTS Info(Id INT PRIMARY KEY AUTO_INCREMENT,
    CaseId INT, SuspectName VARCHAR(100), PhoneNumber VARCHAR(12), Date VARCHAR(10),
    FOREIGN KEY(CaseId) REFERENCES Cases(Id) ON DELETE CASCADE) ENGINE= InnoDB;


INSERT INTO Cases(Id, Name, Details) VALUES(1, 'Cheeky Scar', 'A mass murderer in Essex terrorizes peaceful people');
INSERT INTO Cases(Id, Name, Details) VALUES(2, 'Little Rabbit', 'A girl found dead with her rabbit close by');

INSERT INTO Users(Id, Name) VALUES(1,' Sherlock Holmes');

INSERT INTO Info(Id, CaseId, SuspectName, PhoneNumber, Date) VALUES(1, 1, 'Elliot Melvoin','0745321331','10.01.2015');
INSERT INTO Info(Id, CaseId, SuspectName, PhoneNumber, Date) VALUES(2, 1, 'Elliot Melvoin','0745321331','10.01.2015');
INSERT INTO Info(Id, CaseId, SuspectName, PhoneNumber, Date) VALUES(3, 1, 'Elliot Melvoin','0745321331','10.01.2015');
INSERT INTO Info(Id, CaseId, SuspectName, PhoneNumber, Date) VALUES(4, 1, 'Elliot Melvoin','0745321331','10.01.2015');
INSERT INTO Info(Id, CaseId, SuspectName, PhoneNumber, Date) VALUES(5, 1, 'Elliot Melvoin','0745321331','10.01.2015');
INSERT INTO Info(Id, CaseId, SuspectName, PhoneNumber, Date) VALUES(6, 1, 'Elliot Melvoin','0745321331','10.01.2015');
INSERT INTO Info(Id, CaseId, SuspectName, PhoneNumber, Date) VALUES(7, 1, 'Elliot Melvoin','0745321331','10.01.2015');
INSERT INTO Info(Id, CaseId, SuspectName, PhoneNumber, Date) VALUES(8, 1, 'Elliot Melvoin','0745321331','10.01.2015');
