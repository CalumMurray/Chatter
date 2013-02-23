package chatter.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import chatter.model.User;



public class LoginService extends DatabaseConnector
{
	

	public boolean authenticate(String email, String hash)
	{
		initConnection();

		//Check password matches for given email
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		try 
    	{
			//Prepare Statement
			preparedString = "SELECT email,password FROM users WHERE email = ? AND password = ?;";
    		preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
    		preparedQuery.setString(1, email);
    		preparedQuery.setString(2, hash);
    		
    		//Execute Statement
    		ResultSet resultSet = preparedQuery.executeQuery();  	
    		
	    		//TODO: Remove debug
				System.out.println("resultset for  [" + preparedQuery.toString() + "] " + ((resultSet == null) ? "== null" : "!= null"));
    		
    		//Handle Result
			return resultSet.first();
		
    	}
    	catch(Exception ex)
    	{

			System.out.println("Problem executing SQL Query [" + preparedQuery.toString() + "] in LoginService.java");
    		ex.printStackTrace();
    		
    	}
    	finally
    	{
    		try
    		{
    			//Finally close stuff to return connection to pool for reuse
        		preparedQuery.close();
        		connection.close();
    		}
    		catch (SQLException sqle)
    		{
    			sqle.printStackTrace();
    		}

    	}
		return false;

	}

	//Find successfully logged in user and extract into a bean for use in a jsp.
	public User getUser(String email)  
	{
		//TODO: Save User bean as field to return?
		
		
		initConnection();
		
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		User user = null;
		try 
		{
			//Prepare Statement
			preparedString = "SELECT email,firstname,lastname FROM users WHERE email = ?;";
			preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
			
			preparedQuery.setString(1, email);
			//TODO: Remove debug
			System.out.println("Get user with SQL:  [" + preparedQuery.toString() + "] ");
			//Execute Statement
			ResultSet resultSet = preparedQuery.executeQuery();  	
			

			user = new User();
			//Extract user into a Bean
			while (resultSet.next())
			{
				user.setEmail(resultSet.getString("email"));
				user.setFirstName(resultSet.getString("firstname"));
				user.setLastName(resultSet.getString("lastname"));
				//TODO: Set password?
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
    	{
    		try
    		{
    			//Finally close stuff to return connection to pool for reuse
        		preparedQuery.close();
        		connection.close();
    		}
    		catch (SQLException sqle)
    		{
    			sqle.printStackTrace();
    		}

    	}
		return user;
	}
	

	
}
