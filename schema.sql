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
    A_Name VARCHAR(50) NOT NULL,
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
    Category_Name VARCHAR(20) NOT NULL,
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

Call show_categories('21');

#add-category Name weight
DELIMITER $$
CREATE PROCEDURE add_category(in c_id INTEGER, cat_name VARCHAR(20), weight Double)
BEGIN
INSERT INTO Category (Class_ID, Category_Name, Weight) values (c_id, cat_name, weight);
END $$
DELIMITER ;

Call add_category('21', 'Project', '20');
Call add_category('21', 'Homework','35.00');


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

Call show_assignment('21');

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

Call add_assignment('21', 'Assignment 1', 'Homework', 'ERModel Diagram', '100');
Call add_assignment('21', 'Final Project', 'Project', 'Partner project', '100');

#------------------------------
#add-student username studentid Last First !!NOT FINISHED!!
DELIMITER $$
CREATE PROCEDURE add_student(in c_id INTEGER, uname VARCHAR(50), s_id INTEGER, lName VARCHAR(50), fName VARCHAR(50))
BEGIN
	DECLARE EXIT HANDLER FOR 1048
	BEGIN
		SET @checkfirstname = (Select S_FName From Students Where Username = uname);
        SET @checklaststname = (Select S_LName From Students Where Username = uname);
        IF (@checkfirstname != fName) || (@checklastname != lName) THEN
			UPDATE Students SET S_FNAME = fName, S_LName = lName WHERE Username = uname;
		ELSE
        	INSERT INTO Students (S_FName, S_LName, Username) values (lName, fName, uname);
			INSERT INTO Enrolled (Class_ID, Student_ID) values (c_id, s_id);
        END IF;
	END;
 INSERT INTO Enrolled (Class_ID, Student_ID) values (c_id,(Select Student_ID From Students Where Username = uname && S_FName = fName && S_LName = lname));
END $$
DELIMITER ;

Select * From Students Where Username = 'jd5001';

INSERT INTO Enrolled (Class_ID, Student_ID) values ('21',(Select Student_ID From Students Where Username = 'jd5001' && S_FName = 'John' && S_LName = 'Doe'));

call add_student('21', 'jd5001', '101','John','Daxton');

#--------------------------------
#add-student username
#username is null, return error
Select Student_ID From Students Where Username = 'jd5001';

DELIMITER $$
CREATE PROCEDURE add_student2(in c_id INTEGER, uname VARCHAR(50))
BEGIN
	DECLARE EXIT HANDLER FOR 1048
	BEGIN
		SELECT 'Username was not found.';
	END;
    
INSERT INTO Enrolled (Class_ID, Student_ID) values (c_id,(Select Student_ID From Students Where Username = uname));

END $$
DELIMITER ;

call add_student2('21','jd5001');

#show-students
DELIMITER $$
CREATE PROCEDURE show_students(in c_id INTEGER)
BEGIN
SELECT Students.Student_ID, S_FName, S_LName, Username
FROM Students JOIN Enrolled ON (Students.Student_ID = Enrolled.Student_ID)
WHERE Class_ID = c_id;
END $$
DELIMITER ;

call show_students('21');

#show-students string
DELIMITER $$
CREATE PROCEDURE show_students2(in c_id INTEGER, str VARCHAR(50))
BEGIN
Select S_FName, S_LName, Username 
FROM Students JOIN Enrolled ON (Students.Student_ID = Enrolled.Student_ID)
Where ((Locate(str, S_FName) > 0) || (Locate(str, S_LName)>0) || (Locate(str, Username)>0)) && Class_ID = c_id;
END $$
DELIMITER ;

call show_students2('21', 'a');

#grade assignment username grade

#grade assignmentname username grade

#student-grades username

#gradebook


