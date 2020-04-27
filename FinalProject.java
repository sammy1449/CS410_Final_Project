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
					"?verifyServerCertificate=false&useSSL=true", "msandbox",
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
						+ ":" + rs.getString(5)
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
	
	public static void runCreateItem(Connection conn, String code, String description, String price) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("call CreateItem(?, ?, ?)");
			stmt.setString(1, code);
			stmt.setString(2, description);
			stmt.setString(3, price);
			
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
	
	public static void runCreatePurchase(Connection conn, String code, String purchaseQuantity) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("call CreatePurchase(?, ?)");
			stmt.setString(1, code);
			stmt.setString(2, purchaseQuantity);
			
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

	public static void runCreateShipment(Connection conn, String code, String shipmentQuantity, String date) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("call CreateShipment(?, ?, ?)");
			stmt.setString(1, code);
			stmt.setString(2, shipmentQuantity);
			stmt.setString(3, date);
			
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
	
	public static void runGetItems(Connection conn, String code) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call GetItems(?)");
			stmt.setString(1, code); // input parameter
			
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
						+ ":" + rs.getDouble(4)
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
	
	public static void runGetShipments(Connection conn, String code) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call GetShipments(?)");
			stmt.setString(1, code); // input parameter
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

			// We have DATA!
			rs.beforeFirst();  
			while (rs.next()) {  
      			// output must match result set columns
				System.out.println(rs.getInt(1) 
						+ ":" + rs.getInt(2) 
						+ ":" + rs.getInt(3)
						+ ":" + rs.getDate(4)
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
	
	public static void runGetPurchases(Connection conn, String code) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call GetPurchases(?)");
			stmt.setString(1, code); // input parameter
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

			// We have DATA!
			rs.beforeFirst();  
			while (rs.next()) {  
      			// output must match result set columns
				System.out.println(rs.getInt(1) 
						+ ":" + rs.getInt(2) 
						+ ":" + rs.getInt(3)
						+ ":" + rs.getDate(4)
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
	
	public static void runItemsAvailable(Connection conn, String code) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call ItemsAvailable(?)");
			stmt.setString(1, code); // input parameter
			
      		boolean isResultSet = stmt.execute();    
      		if(isResultSet) {
          			rs = stmt.getResultSet();
			}

			// We have DATA!
			rs.beforeFirst();  
			while (rs.next()) {  
      			// output must match result set columns
				System.out.println(rs.getString(1) 
						+ ":" + rs.getString(2) 
						+ ":" + rs.getInt(3)
						+ ":" + rs.getInt(4)
						+ ":" + rs.getInt(4)
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
	
	public static void runUpdateItem(Connection conn, String code, String price) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call UpdateItem(?, ?)");
			stmt.setString(1, code);
			stmt.setString(2, price);
			
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
	
	public static void runDeleteItem(Connection conn, String code) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call DeleteItem(?)");
			stmt.setString(1, code); // input parameter
			
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
	
	public static void runDeleteShipment(Connection conn, String code) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call DeleteShipment(?)");
			stmt.setString(1, code); // input parameter
			
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
	
	public static void runDeletePurchase(Connection conn, String code) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
				// be sure to call the appropriate procedure based on the function youre running
			stmt = conn.prepareStatement("call DeletePurchase(?)");
			stmt.setString(1, code); // input parameter
			
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
	
	public static void runQuery(Connection conn) {

		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Student;");
			// Now do something with the ResultSet ....
			
			rs.beforeFirst();
			while (rs.next()) {
				System.out.println(rs.getInt(1) 
						+ ":" + rs.getString(2) 
						+ ":" + rs.getString(3) 
						+ ":" + rs.getString(4));
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
			  System.out.println ("Usage :   CreateItem <ItemCode> <ItemDescription> <Price>");
			  System.out.println ("Usage :   CreatePurchase <ItemCode> <PurchaseQuantity>");
			  System.out.println ("Usage :   CreateShipment <ItemCode> <ShipmentQuantity> ShipmentDate");
			  System.out.println ("Usage :   GetItems <ItemCode>");
			  System.out.println ("Usage :   GetShipments <ItemCode>");
			  System.out.println ("Usage :   GetPurchases <ItemCode>");
			  System.out.println ("Usage :   ItemsAvailable <ItemCode>");
			  System.out.println ("Usage :   UpdateItem <ItemCode> <NewPrice>");
			  System.out.println ("Usage :   DeleteItem <ItemCode>");
			  System.out.println ("Usage :   DeleteShipment <ItemCode>");
			  System.out.println ("Usage :   DeletePurchase <ItemCode>");
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

			if (args.length >= 1 && args.length <= 4)
			{
			 	if(args[0].equals("ListClasses")) {
			 		System.out.println("Running ListClasses");
			 		runlistclasses(conn);
			 	}
			 	else if(args[0].equals("CreatePurchase")) {
			 		System.out.println("Running CreatePurchase");
			 		runCreatePurchase(conn, args[1], args[2]);
			 	}
			 	else if(args[0].equals("CreateShipment")) {
			 		System.out.println("Running CreateShipment");
			 		runCreateShipment(conn, args[1], args[2], args[3]);
			 	}
			 	else if(args[0].equals("GetItems")) {
			 		System.out.println("Running GetItems");
			 		runGetItems(conn, args[1]);
			 	}
			 	else if(args[0].equals("GetShipments")) {
			 		System.out.println("Running GetShipments");
			 		runGetShipments(conn, args[1]);
			 	}
			 	else if(args[0].equals("GetPurchases")) {
			 		System.out.println("Running GetPurchases");
			 		runGetPurchases(conn, args[1]);
			 	}
			 	else if(args[0].equals("ItemsAvailable")) {
			 		System.out.println("Running ItemsAvailable");
			 		runItemsAvailable(conn, args[1]);
			 	}
			 	else if(args[0].equals("UpdateItem")) {
			 		System.out.println("Running UpdateItem");
			 		runUpdateItem(conn, args[1], args[2]);
			 	}
			 	else if(args[0].equals("DeleteItem")) {
			 		System.out.println("Running DeleteItem");
			 		runDeleteItem(conn, args[1]);
			 	}
			 	else if(args[0].equals("DeleteShipment")) {
			 		System.out.println("Running DeleteShipment");
			 		runDeleteShipment(conn, args[1]);
			 	}
			 	else if(args[0].equals("DeletePurchase")) {
			 		System.out.println("Running DeletePurchase");
			 		runDeletePurchase(conn, args[1]);
			 	}
			}
			else if (args[0].equals("/?") ){
			 	System.out.println("Running test");
				
			}
			else if (args[0].equals( "test") ){
			 	System.out.println("Running test");
				runQuery(conn);
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
