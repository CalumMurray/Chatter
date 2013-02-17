package chatter.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import chatter.model.Message;
import chatter.model.User;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageService extends DatabaseConnector
{

	public void postMessage(String userEmail, String messageContent)
	{
		initConnection("jdbc/chatter");
		
		
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		try 
    	{

			//Prepare Statement
			preparedString = "INSERT INTO messages VALUES (?, ?, ?);";
    		preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
    		preparedQuery.setTimestamp(1, new Timestamp(new Date().getTime()));	//Get current timestamp
    		preparedQuery.setString(2, userEmail);
    		preparedQuery.setString(3, messageContent);
    		//Execute Statement
    		
    		preparedQuery.executeUpdate();  	
    		
	    		//TODO: Remove debug
				System.out.println("Executing  [" + preparedQuery.toString() + "] ");
		
    	}
    	catch(Exception ex)
    	{
			System.out.println("Problem executing SQL Query [" + preparedQuery.toString() + "] in MessageService.java");
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

	}
	
	public List<Message> getUserMessages(User user)
	{
		//TODO: Save Message bean as field to return?
		
		initConnection("jdbc/chatter");
		
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		List<Message> messageList = null;
		try 
		{
			//Prepare Statement
			preparedString = "SELECT timestamp,user,content FROM messages WHERE messages.user = ?;";
			preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
			preparedQuery.setString(1, user.getEmail());
			
			//TODO: Remove debug
			System.out.println("Get user with SQL:  [" + preparedQuery.toString() + "] ");
			
			//Execute Statement
			ResultSet resultSet = preparedQuery.executeQuery();  	
			

			messageList = new ArrayList<Message>();
			//Extract user into a Bean
			while (resultSet.next())
			{
				//Add new message to list for each message from the user
				Message newMessage = new Message();
				
				newMessage.setTimeStamp(resultSet.getTimestamp("timestamp"));
				newMessage.setAuthor(user);
				newMessage.setContent(resultSet.getString("content"));
				
				messageList.add(newMessage);

			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return messageList;		
	}
	
	
}
