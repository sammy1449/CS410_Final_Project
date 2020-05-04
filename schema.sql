CREATE DATABASE Final;
USE Final;
SET SQL_SAFE_UPDATES = 0;

Drop Table Class;
Drop Table Students;
Drop Table Assignments;
Drop Table Category;
Drop Table Enrolled;
Drop table Gradebook;
Drop table Category;


CREATE TABLE Class (
	Class_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    Course_Number VARCHAR(6) NOT NULL,
    Term VARCHAR(6),
    Section_Number INTEGER NOT NULL,
    C_Description VARCHAR(100) NOT NULL,
    C_Status VARCHAR(8) DEFAULT 'Inactive'
);

CREATE TABLE Students (
	Student_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    S_FName VARCHAR(50) NOT NULL,
	S_LName VARCHAR(50) NOT NULL,
    Username VARCHAR(50) NOT NULL
);

CREATE TABLE Category(
	Category_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    Class_ID INTEGER NOT NULL REFERENCES Class,
    Category_Name VARCHAR(20) NOT NULL,
    Weight DOUBLE NOT NULL,
    FOREIGN KEY (Class_ID) REFERENCES Class (Class_ID),
	INDEX (Class_ID)
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

call new_class ('CS421', 'Sp19', 2, 'Algorithms');


DELIMITER $$
CREATE PROCEDURE list_classes()
BEGIN
	SELECT Class.Class_ID,Course_Number, Term, Section_Number, COUNT(Student_ID)
	FROM Class JOIN Enrolled ON (Class.Class_ID = Enrolled.Class_ID)
	GROUP BY Class.Class_ID, Course_Number, Term, Section_Number;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE select_class(in c_num VARCHAR(6))
BEGIN
SET @activeclass = (Select Course_Number from Class Where C_Status = 'Active');
IF (@activeclass != c_num) THEN
	UPDATE Class SET C_Status = 'Inactive' Where Course_Number = @activeclass;
	UPDATE Class SET C_Status = 'Active' Where Course_Number = c_num;
ELSE
	UPDATE Class SET C_Status = 'Active' Where Course_Number = c_num;
END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE show_class()
BEGIN
Select Course_Number from Class Where C_Status = 'Active';
END $$
DELIMITER ;

call show_class();
Select Course_Number from Class Where C_Status = 'Active';

call select_class('AAB518');
Select * from Class;

DELIMITER $$
CREATE PROCEDURE select_class2(in c_num VARCHAR(6), term VARCHAR(6))
BEGIN
UPDATE Class SET C_Status = 'Active' Where Course_Number = c_num && Term = term;
SELECT * FROM Class WHERE Course_Number = c_num AND Term = term;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE select_class3(in c_num VARCHAR(6), term VARCHAR(6), sec_num INTEGER)
BEGIN
UPDATE Class SET C_Status = 'Active' Where Course_Number = c_Number;
SELECT * FROM Class WHERE Course_Number = c_num AND Term = term AND Section_Number = sec_num;
END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE show_categories(in c_id INTEGER)
BEGIN
SELECT * FROM Category WHERE Class_ID = c_id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE add_category(in c_id INTEGER, cat_name VARCHAR(20), weight Double)
BEGIN
INSERT INTO Category (Class_ID, Category_Name, Weight) values (c_id, cat_name, weight);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE show_assignment(in c_id INTEGER)
BEGIN
SELECT A_Name, Points, Category_Name
FROM Assignments JOIN Category ON (Assignments.Category_ID = Category.Category_ID)
WHERE Category.Class_ID = c_id
GROUP BY A_Name, Points, Category_Name;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE add_assignment(in c_id INTEGER, a_name VARCHAR(50), cat_name VARCHAR(20), a_des TINYTEXT, points INTEGER)
BEGIN
Insert into Assignments(Class_ID, A_Name, Category_ID, A_Description, Points) values (c_id, a_name, 
(select Category_ID 
from Category 
where Category_Name = cat_name && Class_ID = c_id), a_des, points);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE add_student(in c_id INTEGER, uname VARCHAR(50), s_id INTEGER, lName VARCHAR(50), fName VARCHAR(50))
BEGIN
	SET @checkgivenname = (Select Student_ID from Students WHERE Username = uname && S_LName = lName && S_FName = fName);
    SET @usernameexits = (Select Username from Students WHERE Username = uname);

    IF (@usernameexits is not null && (@checkgivenname is NULL || @checkgivenname='')) THEN
		Update Students SET S_FName = fName, S_LName = lName Where Student_ID = s_id;
	ELSEIF ((@usernameexits is NULL || @usernameexits='') && (@checkgivenname is NULL || @checkgivenname='')) THEN
		INSERT INTO Students (S_FName, S_LName, Username) values(fName, lName, uname);
    END IF;
    INSERT INTO Enrolled (Class_ID, Student_ID) values (c_id,s_id);
END $$
DELIMITER ;


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

DELIMITER $$
CREATE PROCEDURE show_students(in c_id INTEGER)
BEGIN
SELECT Students.Student_ID, S_FName, S_LName, Username
FROM Students JOIN Enrolled ON (Students.Student_ID = Enrolled.Student_ID)
WHERE Class_ID = c_id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE show_students2(in c_id INTEGER, str VARCHAR(50))
BEGIN
Select S_FName, S_LName, Username 
FROM Students JOIN Enrolled ON (Students.Student_ID = Enrolled.Student_ID)
Where ((Locate(str, S_FName) > 0) || (Locate(str, S_LName)>0) || (Locate(str, Username)>0)) && Class_ID = c_id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE grade(in c_id INTEGER, aname VARCHAR(50), uname VARCHAR(50), grade INTEGER)
BEGIN
#get assignment id
SET @a_id = (Select Assignment_ID from Assignments Where A_Name = aname && Class_ID = c_id LIMIT 1);
SET @s_id = (Select Student_ID from Students Where Username = uname LIMIT 1);
SET @get_points = (Select Points from Assignments Where A_Name = a_name && Class_ID = c_id LIMIT 1);
SET @grade_exits = (Select Grade_ID From Gradebook Where Student_ID = @s_id && Assignment_ID = @a_id LIMIT 1);

IF(grade > @get_points) THEN
	Select CONCAT("Grade cannot be greater than ",@get_points, " points");
END IF;

IF (@grade_exits is not NULL) THEN
	UPDATE Gradebook SET Grade = grade WHERE Student_ID = @s_id && Assignment_ID = @a_id;
ELSE
	INSERT INTO Gradebook (Student_ID, Assignment_ID, Grade) values (@s_id, @a_id, grade);
END IF;
END $$
DELIMITER ;

#student-grades username

SELECT Username, Grade, A_Name, Points, Category_Name
FROM Students Right Join Gradebook on (Students.Student_ID = Gradebook.Student_ID)
Right Join Assignments on (Assignments.Assignment_ID = Gradebook.Assignment_ID)
JOIN Category ON (Assignments.Category_ID = Category.Category_ID)
WHERE Category.Class_ID = '21';
#gradebook



call show_students('21');
call show_assignment('21');
call show_categories('21');
select * 
from Enrolled;
select * from Gradebook;


