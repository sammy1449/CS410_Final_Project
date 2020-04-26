CREATE DATABASE Final;
USE Final;

DROP TABLE Class;
DROP TABLE Students;
DROP TABLE Assignments;
DROP TABLE Enrolled;
DROP TABLE Gradebook;

CREATE TABLE Class (
	Class_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    Course_Number VARCHAR(6) NOT NULL,
    Term VARCHAR(6),
    Section_Number INTEGER NOT NULL,
    C_Description VARCHAR(100) NOT NULL
);

CREATE TABLE Students (
	Student_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    S_FName VARCHAR(50) NOT NULL,
	S_LName VARCHAR(50) NOT NULL,
    Username VARCHAR(50) NOT NULL
);

CREATE TABLE Assignments (
	Assignment_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    Class_ID INTEGER NOT NULL REFERENCES Class,
    A_Name VARCHAR(50) UNIQUE NOT NULL,
    Category VARCHAR(10) NOT NULL,
    A_Description TINYTEXT NOT NULL,
    Weight DOUBLE NOT NULL,
    Points INTEGER NOT NULL,
    
	FOREIGN KEY (Class_ID) REFERENCES Class (Class_ID),
	INDEX (Class_ID)
);

CREATE TABLE Enrolled (
	Enrolled_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
	Class_ID INTEGER NOT NULL REFERENCES Class,
	Student_ID INTEGER NOT NULL REFERENCES Students,   
    
	FOREIGN KEY (Class_ID) REFERENCES Class (Class_ID),
	FOREIGN KEY (Student_id) REFERENCES Students (Student_ID),
	INDEX (Class_ID),
	INDEX (Student_ID)
);

CREATE TABLE Gradebook (
	Grade_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    Student_ID INTEGER NOT NULL REFERENCES Students, 
    Assignment_ID INTEGER NOT NULL REFERENCES Assignments, 
    Grade DOUBLE ,
    
    FOREIGN KEY (Student_id) REFERENCES Students (Student_ID),
    FOREIGN KEY (Assignment_id) REFERENCES Assignments (Assignment_ID),
    INDEX (Student_ID),
    INDEX (Assignment_ID)
);

select * from Assignments;
Select * From Class;
Select * from Students;
select * from Enrolled;
select * from Gradebook;

DELIMITER $$
CREATE PROCEDURE New_Class(IN c_num VARCHAR(6), term VARCHAR(6), sec_num INTEGER, ds VARCHAR(100))
BEGIN
	INSERT INTO Class (Course_number, Term, Section_Number, C_Description) values (c_num, term, sec_num, ds);
END $$
DELIMITER ;

CALL New_Class('CS410', 'SP20', 1, 'Databases');

DELIMITER $$
CREATE PROCEDURE list_classes()
BEGIN
	SELECT Class.Class_ID,Course_Number, Term, Section_Number, COUNT(Student_ID)
	FROM Class JOIN Enrolled ON (Class.Class_ID = Enrolled.Class_ID)
	GROUP BY Class.Class_ID, Course_Number, Term, Section_Number;
END $$
DELIMITER ;

CALL list_classes();

DELIMITER $$
CREATE PROCEDURE select_class(in c_num VARCHAR(6))
BEGIN
SELECT * FROM Class WHERE Course_Number = c_num;
END $$
DELIMITER ;

CALL select_class('CS410');

DELIMITER $$
CREATE PROCEDURE select_class2(in c_num VARCHAR(6), term VARCHAR(6))
BEGIN
SELECT * FROM Class WHERE Course_Number = c_num && Term = term;
END $$
DELIMITER ;

CALL select_class2('CS410', 'SP20');

DELIMITER $$
CREATE PROCEDURE select_class3(in c_num VARCHAR(6), term VARCHAR(6), sec_num INTEGER)
BEGIN
SELECT * FROM Class WHERE Course_Number = c_num && Term = term && Section_Number = sec_num;
END $$
DELIMITER ;

CALL select_class3('CS410', 'SP20', '1');

