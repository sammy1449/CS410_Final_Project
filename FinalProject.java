import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

/*
Do not forget to set up your classpath.  EVERY TIME 
you can use the mysql setup document to do this permanently, 

you must create a classpath to reference (so that the java import jar file can be found)
run the following command in onyx:
here it is for your convenience:

export CLASSPATH=/opt/mysql/mysql-connector-java-5.1.45/mysql-connector-java-5.1.45-bin.jar:$CLASSPATH

NOTE:  you must be on ONYX, and not a node for the 
Compiler to find the classpath jar file
*/

class project {
	public static Connection makeConnection(String port, String database, String password) {
		try {
			Connection conn = null;
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:" + port+ "/" + database +
					"?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC", "msandbox",
					password);
			// Do something with the Connection
			System.out.println("Database " + database +" connection succeeded!");
			System.out.println();
			return conn;
		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		}
		return null;
	}

	public static void runlistclasses(Connection conn) {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call list_classes()");
			//stmt.setString(1, code); // input parameter
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

			// We have DATA!
			rs.beforeFirst();  
			while (rs.next()) {  
      			// output must match result set columns
				System.out.println(rs.getInt(1) 
						+ ":" + rs.getString(2) 
						+ ":" + rs.getString(3) 
						+ ":" + rs.getInt(4)
						+ ":" + rs.getInt(5)
						);
			}
		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}
	
	public static void runCreateClass(final Connection conn, final String course, final String semester, final String section, final String description) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("call new_class(?, ?, ?, ?)");
			stmt.setString(1, course);
			stmt.setString(2, semester);
			stmt.setString(3, section);
			stmt.setString(4, description);
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}
			// We have DATA!
		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}
	
	public static void runSelectClass(Connection conn, String course) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("call select_class(?)");
			stmt.setString(1, course);
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

			rs.beforeFirst();  
			while (rs.next()) {  
      			// output must match result set columns
				System.out.println(rs.getInt(1) 
						+ ":" + rs.getString(2) 
						+ ":" + rs.getString(3) 
						+ ":" + rs.getInt(4)
						+ ":" + rs.getString(5)
						+ ":" + rs.getString(6)
						);
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}

	public static void runSelectClass2(Connection conn, String course, String term) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("call select_class2(?, ?)");
			stmt.setString(1, course);
			stmt.setString(2, term);
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

			rs.beforeFirst();  
			while (rs.next()) {  
      			// output must match result set columns
				System.out.println(rs.getInt(1) 
						+ ":" + rs.getString(2) 
						+ ":" + rs.getString(3) 
						+ ":" + rs.getInt(4)
						+ ":" + rs.getString(5)
						+ ":" + rs.getString(6)
						);
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}
	
	public static void runSelectClass3(Connection conn, String course, String term, String number) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call select_class3(?, ?, ?)");
			stmt.setString(1, course); // input parameter
			stmt.setString(2, term);
			stmt.setString(3, number);

      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

			// We have DATA!
			rs.beforeFirst();  
			while (rs.next()) {  
      			// output must match result set columns
				System.out.println(rs.getInt(1) 
						+ ":" + rs.getString(2) 
						+ ":" + rs.getString(3) 
						+ ":" + rs.getInt(4)
						+ ":" + rs.getString(5)
						+ ":" + rs.getString(6)
						);
			}
		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}
	
	public static void runShowClass(Connection conn) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call show_class()");
			
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

			// We have DATA!
			rs.beforeFirst();  
			while (rs.next()) {  
      			// output must match result set columns
				System.out.println(rs.getInt(1) 
						+ ":" + rs.getString(2) 
						+ ":" + rs.getString(3) 
						+ ":" + rs.getInt(4)
						+ ":" + rs.getString(5)
						+ ":" + rs.getString(6)
						);
			}
		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}
	
	public static void runShowCategories(Connection conn) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call show_categories()");
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

			// We have DATA!
			rs.beforeFirst();  
			while (rs.next()) {  
      			// output must match result set columns
				System.out.println(rs.getString(1) 
						+ ":" + rs.getInt(2) 
						+ ":" + rs.getInt(3)
						);
			}
		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}
	
	public static void runAddCategory(Connection conn, String Category, String weight) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call add_category(?, ?)");
			stmt.setString(1, Category);
			stmt.setString(2, weight);
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

			
		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}
	
	public static void runShowAssignment(Connection conn) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call show_assignment()");
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

			rs.beforeFirst();  
			while (rs.next()) {  
      			// output must match result set columns
				System.out.println(rs.getString(1) 
						+ ":" + rs.getInt(2) 
						+ ":" + rs.getString(3)
						);
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}
	
	public static void runAddAssignment(Connection conn, String assignment, String cat_name, String a_des, String points) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call add_assignment(?, ?, ?, ?)");
			stmt.setString(1, assignment);
			stmt.setString(2, cat_name);
			stmt.setString(3, a_des);
			stmt.setString(4, points);
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}
	
	public static void runAddStudent(Connection conn, String uname, String s_id, String LName, String FName) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call add_student(?, ?, ?, ?)");
			stmt.setString(1, uname); 
			stmt.setString(2, s_id);
			stmt.setString(3, LName);
			stmt.setString(4, FName);
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}
		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}
	
	public static void runAddStudent2(Connection conn, String uname) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call add_student2(?)");
			stmt.setString(1, uname); // input parameter
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}
		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}
	
	public static void runShowStudents(Connection conn) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call show_students()");
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

			rs.beforeFirst();  
			while (rs.next()) {  
      			// output must match result set columns
				System.out.println(rs.getInt(1) 
						+ ":" + rs.getString(2) 
						+ ":" + rs.getString(3)
						+ ":" + rs.getString(4)
						+ ":" + rs.getInt(5)
						);
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}

	public static void runShowStudents2(Connection conn, String str) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call show_students2(?)");
			stmt.setString(1, str);
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

			rs.beforeFirst();  
			while (rs.next()) {  
      			// output must match result set columns
				System.out.println(rs.getString(1) 
						+ ":" + rs.getString(2) 
						+ ":" + rs.getString(3)
						);
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}

	public static void runGrade(Connection conn, String assignment_name, String uName, String grade) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call grade(?, ?, ?)");
			stmt.setString(1, assignment_name);
			stmt.setString(2, uName);
			stmt.setString(3, grade);
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

			rs.beforeFirst();  
			while (rs.next()) {  
      			// output must match result set columns
				System.out.println(rs.getString(1) 
						+ ":" + rs.getInt(2) 
						+ ":" + rs.getString(3)
						);
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}

	public static void main(String[] args) {
		try {
			// The newInstance() call is a work around for some broken Java implementations
			
			
			if (args[0].equals("/?") ){
			  System.out.println ("Usage :   new-class <CourseNumber> <Term> <Section> <Description>");
			  System.out.println ("Usage :   list-classes");
			  System.out.println ("Usage :   select-class <CourseNumber>");
			  System.out.println ("Usage :   select-class2 <CourseNumber> <Term>");
			  System.out.println ("Usage :   select-class3 <CourseNumber> <Term> <Section>");
			  System.out.println ("Usage :   show-class");
			  System.out.println ("Usage :   show-categories");
			  System.out.println ("Usage :   add-category <CatName> <Weight>");
			  System.out.println ("Usage :   show-assignment");
			  System.out.println ("Usage :   add-assignment <AName> <Category> <Description> <Points>");
			  System.out.println ("Usage :   add-student <Username> <sID> <LastN> <FirstN>");
			  System.out.println ("Usage :   add-student2 <Username>");
			  System.out.println ("Usage :   show-students");
			  System.out.println ("Usage :   show-students2 <String>");
			  System.out.println ("Usage :   grade <AName> <Username> <Grade>");
			  return;
			}
			else {
				System.out.println(args[0]);
				System.out.println("**");

				if (args.length == 2)
				{
					System.out.println(args[1]);  // show extra args
				}
			}

			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println();
			System.out.println("JDBC driver loaded");
    Connection conn = makeConnection("57036", "Final","Nightwing0225");

// TO DO:  add to the code here to run the appropriate function
// when the getdata request is made 
// at the command line

			if (args.length >= 1 && args.length <= 6)
			{
			 	if(args[0].equals("list-classes")) {
			 		System.out.println("Running list-classes");
			 		runlistclasses(conn);
			 	}
			 	else if(args[0].equals("new-class")) {
			 		System.out.println("Running new-class");
			 		runCreateClass(conn, args[1], args[2], args[3], args[4]);
			 	}
			 	else if(args[0].equals("select-class")) {
			 		System.out.println("Running select-class");
			 		runSelectClass(conn, args[1]);
			 	}
			 	else if(args[0].equals("select-class2")) {
			 		System.out.println("Running select-class2");
			 		runSelectClass2(conn, args[1], args[2]);
			 	}
			 	else if(args[0].equals("select-class3")) {
			 		System.out.println("Running select-class3");
			 		runSelectClass3(conn, args[1], args[2], args[3]);
			 	}
			 	else if(args[0].equals("show-class")) {
			 		System.out.println("Running show-class");
			 		runShowClass(conn);
			 	}
			 	else if(args[0].equals("show-categories")) {
			 		System.out.println("Running show-categories");
			 		runShowCategories(conn);
			 	}
			 	else if(args[0].equals("add-category")) {
			 		System.out.println("Running add-category");
			 		runAddCategory(conn, args[1], args[2]);
			 	}
			 	else if(args[0].equals("show-assignments")) {
			 		System.out.println("Running show-assignments");
			 		runShowAssignment(conn);
			 	}
			 	else if(args[0].equals("add-assignments")) {
			 		System.out.println("Running add-assignments");
			 		runAddAssignment(conn, args[1], args[2], args[3], args[4]);
			 	}
			 	else if(args[0].equals("add-student")) {
			 		System.out.println("Running add-student");
			 		runAddStudent(conn, args[1], args[2], args[3], args[4]);
			 	}
				else if(args[0].equals("add-student2")){
					System.out.println("Running add-student2")
					runAddStudent2(conn, args[1])
				}
				else if(args[0].equals("show-students")){
					System.out.println("Running show-students")
					runShowStudents(conn)
				}
				else if(args[0].equals("show-students2")){
					System.out.println("Running show-students2")
					runShowStudents2(conn, args[1])
				}
				else if(args[0].equals("grade")){
					System.out.println("Running grade")
					runGrade(conn, args[1], args[2], args[3])
				}
			}
			else if (args[0].equals("/?") ){
			 	System.out.println("Running test");
				
			}
			else {
				System.out.println("No process requested");
			}
			conn.close();
			System.out.println();
			System.out.println("Database connection closed");
			System.out.println();
		} catch (Exception ex) {
			// handle the error
			System.err.println(ex);
		}
	}
}
