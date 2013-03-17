/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package main;

/**
*
* @author Ritesh Kumar Gupta
*/
import java.sql.*;
import javax.sql.*;
public class dbConnection 
{
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://127.0.0.1/securitysystem";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "hardrock#123";
	Connection conn = null;
	Statement stmt = null;

	public Connection getdbconnectionString()
	{
		conn = null;
		stmt = null;
		try
		{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		}
		catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
		}//end try
		return conn;
	}
	public void closeConnection(Connection link)
	{
		try{
			link.close();
		}
		catch(SQLException se)
		{
		}// do nothing
		try{
			if(link!=null)
				link.close();
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}//end finally try
	}
}
