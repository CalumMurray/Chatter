package chatter.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import chatter.model.User;


public class UserService extends DatabaseConnector
{
	public User getProfileUser(String email)
	{
		initConnection();

		//Check password matches for given email
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		try 
    	{
			//Prepare Statement
			preparedString = "SELECT email, firstname, lastname FROM users WHERE email = ?;";
    		preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
    		preparedQuery.setString(1, email);
    		
    		//Execute Statement
    		ResultSet resultSet = preparedQuery.executeQuery();  	
    		
	    		//TODO: Remove debug
				System.out.println("Get profile user with  [" + preparedQuery.toString() + "] ");
			User profileUser = null;
    		while (resultSet.next())
    		{
	    		//Package into User bean
				profileUser = new User();
				profileUser.setEmail(email);
				profileUser.setFirstName(resultSet.getString("firstname"));
				profileUser.setLastName(resultSet.getString("lastname"));
    		}
			return profileUser;
		
    	}
    	catch(Exception ex)
    	{

			System.out.println("Problem executing SQL Query [" + preparedQuery.toString() + "] in UserService.java");
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
		return null;
	}

}
