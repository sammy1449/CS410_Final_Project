# CS410_Final_Project

****************
* Project Grade Management
* Class: CS 410
* Date: May 3, 2020
* Name: Samantha Maxey & Aubrey Spannagel
**************** 

OVERVIEW:

We will be designing and implementing a Java application for managing grades in a class using a mySQL database.

INCLUDED FILES:

-FinalProject.java
-schema.sql
-StudentsMockData.sql
-ClassMockData.sql
-EnrolledMockData.sql

COMPILING AND RUNNING:

To compile (must be on ONYX):

$ export CLASSPATH=/opt/mysql/mysql-connector-java-8.0.18.jar:.:$CLASSPATH

$ javac FinalProject.java

To run:

$ java project ....

If this is your first time running this, I would suggest typing '/?' for a Usage tutorial on the arguments needed.


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

We have 4 separate folders for the SQL files. The schema.sql file shows the table creations as well as 

TESTING:

We did extensive testing in both Java and mySQL, checking to make sure that the data was responding the way we wanted it to and that Java was also showing the information correctly when it was supposed to. We mainly tested through mySQL Workbench, checking that the queries were running successfully before trying to run them through JDBC. 


DISCUSSION:
 
The difficult part was trying to get the grade portion of the project oriented in the way that she had worded she wanted it. It was difficult to try and get the student-grades to cooperate the way we wanted to with the 'complete' class grade that was required. We found a work around since we had gotten gradebook working which is essentially what that column would be. Another thing that we learned about was the SET and IF statements that we could use within the procedure. This was new to us and a very useful tool. 
 
EXTRA CREDIT:
N/A
