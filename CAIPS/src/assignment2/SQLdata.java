package assignment2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLdata {
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/subtables"; // After localhost/databasename
	
	// -------> INPUTS <-------
	// Database credentials (must be changed by the user)
	static final String USER = "root";
	static final String PASS = "root"; 
	// ------------------------
	
	Connection conn = null;
	Statement stmt = null;
	String sql = null;
	
	public List <Measurements> commsSQLJAVA(String table_name){
		
		try{
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			
			// Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
//			// Execute a query to select data
			System.out.println("Selecting Data...");
			stmt = conn.createStatement();
			String sql = "SELECT rdfid, name, time, value, sub_rdfid FROM " + table_name + " ORDER BY time";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("Data obtained successfully...");
			
			List <Measurements> MeasurementList = new ArrayList<Measurements>();
			MeasurementList.clear();
			
			// iterate through the java resultset
		      while (rs.next())
		      {

		    	String rdfid = rs.getString("rdfid");
		    	String name = rs.getString("name");
		        double time = rs.getDouble("time");
		        double value = rs.getDouble("value");
		        String subrdfid = rs.getString("sub_rdfid");
		        
		        // print the results
		        //System.out.format("%s, %s \n", time, value);//, name);

		        //System.out.println("time " + time + " ,value: " + value);
		        MeasurementList.add(new Measurements(rdfid, name, time, value, subrdfid));
		      }
		      
		      stmt.close(); //Close query.
			  conn.close(); //Close connection.
				
		      return MeasurementList;
				
		}catch(SQLException se){
		//Handle errors for JDBC
		se.printStackTrace();
		return null;
		}catch(Exception e){
		//Handle errors for Class.forName
		e.printStackTrace();
		return null;
		}
	}

}