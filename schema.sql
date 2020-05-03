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
    Category_ID INTEGER NOT NULL REFERENCES Category,
    A_Description TINYTEXT NOT NULL,
    Points INTEGER NOT NULL,
    
    FOREIGN KEY (Category_ID) REFERENCES Category(Category_ID),
    INDEX(Category_ID),
	FOREIGN KEY (Class_ID) REFERENCES Class (Class_ID),
	INDEX (Class_ID)
);

CREATE TABLE Category(
	Category_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    Class_ID INTEGER NOT NULL REFERENCES Class,
    Category_Name VARCHAR(20) UNIQUE NOT NULL,
    Weight DOUBLE NOT NULL,
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
CREATE PROCEDURE new_class(IN c_num VARCHAR(6), term VARCHAR(6), sec_num INTEGER, ds VARCHAR(100))
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

select * from Category;
select * from Assignments;
Select * From Class;
Select * from Students;
select * from Enrolled;
select * from Gradebook;

#show-categories
DELIMITER $$
CREATE PROCEDURE show_categories(in c_id INTEGER)
BEGIN
SELECT * FROM Category WHERE Class_ID = c_id;
END $$
DELIMITER ;

Call show_categories('1');

#add-category Name weight
DELIMITER $$
CREATE PROCEDURE add_category(in c_id INTEGER, cat_name VARCHAR(20), weight Double)
BEGIN
INSERT INTO Category (Class_ID, Category_Name, Weight) values (c_id, cat_name, weight);
END $$
DELIMITER ;

Call add_category('1', 'Homework','35.00');


#show-assignment
DELIMITER $$
CREATE PROCEDURE show_assignment(in c_id INTEGER)
BEGIN
SELECT A_Name, Points, Category_Name
FROM Assignments JOIN Category ON (Assignments.Category_ID = Category.Category_ID)
WHERE Category.Class_ID = c_id
GROUP BY A_Name, Points, Category_Name;
END $$
DELIMITER ;

Call show_assignment('1');

#add-assignment name Category Description points
DELIMITER $$
CREATE PROCEDURE add_assignment(in c_id INTEGER, a_name VARCHAR(50), cat_name VARCHAR(20), a_des TINYTEXT, points INTEGER)
BEGIN
Insert into Assignments(Class_ID, A_Name, Category_ID, A_Description, Points) values (c_id, a_name, 
(select Category_ID 
from Category 
where Category_Name = cat_name && Class_ID = c_id), a_des, points);
END $$
DELIMITER ;

Call add_assignment('1', 'Assignment 1', 'Homework', 'ERModel Diagram', '100');

#add-student username studentid Last First
#check if username exits
Select Student_ID From Students Where Username = 'jd5001';
#checks if f&l name exits
Select Student_ID From Students Where Username = 'jd5001' && S_FName = 'Jane' && S_LName = 'Doe'; 
#if not, then add them
insert into Students (S_FName, S_LName, Username) values ('Jane', 'Doe', 'jd5001');
#else, update name
Update Students ()
#add student to class
insert into Enrolled (Class_ID, Student_ID) values ('1',(Select Student_ID From Students Where Username = 'jd5001'));

#add-student username
#username is null, return error
(Select Student_ID From Students Where Username = 'jd5001');
insert into Enrolled (Class_ID, Student_ID) values ('1',(Select Student_ID From Students Where Username = 'jd5001'));


DELIMITER $$
CREATE PROCEDURE add_student(in c_id INTEGER, username VARCHAR(50))
BEGIN
    DECLARE EXIT HANDLER FOR 1048
    BEGIN
 	SELECT CONCAT('Student username: ',username,' does not exist') AS message;
    END;
insert into Enrolled (Class_ID, Student_ID) values (c_id, (Select Student_ID From Students Where Username = username));
END $$
DELIMITER ;

call add_student('1','jd5001');






#show-students

#show-students string

#grade assignment username grade

#grade assignmentname username grade

#student-grades username

#gradebook


