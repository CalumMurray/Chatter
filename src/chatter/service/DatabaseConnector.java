package chatter.service;

import java.sql.*;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.*;

//Base class for connecting to MySQL Database with a Datasource provided by JDBC API
@Resource(name="jdbc/chatter", type=javax.sql.DataSource.class)
public class DatabaseConnector 
{
	private static final String DATA_SOURCE_NAME = "jdbc/chatter";

	protected DataSource datasource;
	protected Connection connection;
	
	
	public void initConnection() 
	{
		//---Get Connection----
		try
		{
			//JNDI Lookups for connection to database through JDBC
			Context ic = new InitialContext();							
			datasource = (DataSource) ic.lookup("java:comp/env/" + DATA_SOURCE_NAME);
			connection = datasource.getConnection();
		}
		catch (NamingException ne)
		{	
			System.out.println("Naming Exception in DatabaseConnector.java");
			ne.printStackTrace();
		} 
		catch (SQLException e) 
		{	
			System.out.println("SQL Exception in DatabaseConnector.java");
			e.printStackTrace();
		}
	}
	
//	public ResultSet query(String prepStatement)
//	{
//		
//		
//		//---Execute PreparedStatement---//
//		
//    	try 
//    	{
//    		preparedQuery = connection.prepareStatement(prepStatement);
//    		resultSet = preparedQuery.executeQuery();  	
//    			//TODO: Remove debug
//    			System.out.println("resultset for  [" + prepStatement + "] " + ((resultSet == null) ? "== null" : "!= null"));
//
//    		
//    	}
//    	catch(Exception ex)
//    	{
//    		System.out.println("Problem executing SQL Query [" + prepStatement + "] in Database.java");
//    		ex.printStackTrace();
//    		
//    	}
//    	finally
//    	{
//    		try
//    		{
//    			//Close
//        		preparedQuery.close();
//        		connection.close();
//    		}
//    		catch (SQLException sqle)
//    		{
//    			sqle.printStackTrace();
//    		}
//    		
//    	}
//
//    	return resultSet;
//	}
//	
//	
//	public boolean update(String prepStatement)
//	{
//		
//		PreparedStatement prepStmt;
//		int resultSet = 0;
//		
//		//---Execute PreparedStatement---//
//		
//    	try 
//    	{
//    		prepStmt = connection.prepareStatement(prepStatement);
//    		resultSet = prepStmt.executeUpdate(prepStatement);
//    			//TODO: Remove debug
//    			System.out.println("resultset for  [" + prepStatement + "] == " + resultSet);
//
//    	}
//    	catch(Exception ex)
//    	{
//    		System.out.println("Problem executing SQL statement [" + prepStatement + "] in Database.java");
//    		ex.printStackTrace();
//    		
//    	}
//    	return (resultSet == 0 ? false : true);
//	}
	
	}
