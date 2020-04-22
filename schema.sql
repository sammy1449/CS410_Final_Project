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



DELIMITER $$

CREATE PROCEDURE New_Class(IN c_num VARCHAR(6), term VARCHAR(6), sec_num INTEGER, ds VARCHAR(100))
BEGIN
	INSERT INTO Class (Course_number, Term, Section_Number, C_Description) values (c_num, term, sec_num, ds);
END $$

DELIMITER ;

CALL New_Class('CS410', 'SP20', 1, 'Databases');


Select * From Class;





