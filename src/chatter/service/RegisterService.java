package chatter.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class RegisterService extends DatabaseConnector
{


	public boolean checkEmail(String emailToCheck)
	{
		initConnection();
		
		
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		try 
    	{
			//Prepare Statement
			preparedString = "SELECT * FROM users WHERE email = ?;";
    		preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
    		preparedQuery.setString(1, emailToCheck);
    		
    		//Execute Statement
    		ResultSet resultSet = preparedQuery.executeQuery();  	
    		
	    		//TODO: Remove debug
				System.out.println("Checking duplicate email with:  [" + preparedQuery.toString() + "] " + ((resultSet == null) ? "== null" : "!= null"));
    		
    		//Handle Result
    		try 
    		{
    			return resultSet.first();
    		} 
    		catch (SQLException e) 
    		{
    			e.printStackTrace();

    		}
    		return false;   		
    	}
    	catch(Exception ex)
    	{

			System.out.println("Problem executing SQL Query [" + preparedQuery.toString() + "] in RegisterService.java");
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
	
	
	public boolean createNewUser(String email, String firstname, String lastname, String hashedPassword)
	{
		initConnection();

		int result = 0;
		PreparedStatement sqlStatement = null;
		try 
    	{
			//Prepare Statement
			sqlStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO users VALUES (?,?,?,?);");
			sqlStatement.setString(1, email);
			sqlStatement.setString(2, firstname);
			sqlStatement.setString(3, lastname);
			sqlStatement.setString(4, hashedPassword);
			
			//Execute Prepared Statement
    		result = sqlStatement.executeUpdate();
    			//TODO: Remove debug
    			System.out.println("Registering user with:  [" + sqlStatement.toString() + "] == " + result);

    	}
    	catch(Exception ex)
    	{
    		System.out.println("Problem executing SQL statement [" + sqlStatement.toString() + "] in RegisterService.java");
    		ex.printStackTrace();
    		return false;
    	}
		finally
    	{
    		try
    		{
    			//Finally close stuff to return connection to pool for reuse
    			sqlStatement.close();
        		connection.close();
    		}
    		catch (SQLException sqle)
    		{
    			sqle.printStackTrace();
    		}

    	}
    	return (result == 0 ? false : true);

	}


	public void deleteUser(String userEmail)
	{
		initConnection();

		PreparedStatement sqlStatement = null;
		try 
    	{
			//Prepare Statement
			sqlStatement = (PreparedStatement) connection.prepareStatement("DELETE FROM users WHERE email= ?;");
			sqlStatement.setString(1, userEmail);
			
			//Execute Prepared Statement
    		sqlStatement.executeUpdate();
    			//TODO: Remove debug
    			System.out.println("Deleting user with:  [" + sqlStatement.toString() + "]");

    	}
    	catch(Exception ex)
    	{
    		System.out.println("Problem executing SQL statement [" + sqlStatement.toString() + "] in RegisterService.java");
    		ex.printStackTrace();
    	}
		finally
    	{
    		try
    		{
    			//Finally close stuff to return connection to pool for reuse
    			sqlStatement.close();
        		connection.close();
    		}
    		catch (SQLException sqle)
    		{
    			sqle.printStackTrace();
    		}

    	}
		
	}
}
