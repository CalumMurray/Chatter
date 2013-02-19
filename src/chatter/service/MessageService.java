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
	
	public List<Message> fetchUserFriendMessages(User user)
	{
		//TODO: Save Message bean as field to return?
		
		initConnection("jdbc/chatter");
		
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		List<Message> messageList = null;
		try 
		{
			
			/* Union query produces a list of email addresses - 
		       the person you're looking for plus all his friends
		       Starts with string literal for the main user
		    */
			//Prepare Statement
			preparedString = "SELECT `timestamp`,  user,  firstname,  lastname,  content " +	/* First part of the UNION returns friends messages */
					          "FROM " +
					          "messages " +
					          "INNER JOIN " +
					          "(SELECT ? AS email " +	//Union query produces a list of email addresses - 
					          							//the person you're looking for plus all his friends
					          							//Starts with string literal for the main user
					          "UNION " +
					          "SELECT friend AS email FROM friends WHERE email = ?) user_and_friends ON messages.user = user_and_friends.email " + /* Plus all his friends into one list joined against messages */
					          "INNER JOIN users ON user_and_friends.email = users.email " +	/* Join against the users table for name info */
					          "ORDER BY `timestamp` DESC;";
			preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
			preparedQuery.setString(1, user.getEmail());
			preparedQuery.setString(2, user.getEmail());
			
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
				newMessage.setContent(resultSet.getString("content"));
				
				//Get author as User object
				User author = new User();
				author.setEmail(resultSet.getString("user"));
				author.setFirstName(resultSet.getString("firstname"));
				author.setLastName(resultSet.getString("lastname"));
				
				newMessage.setAuthor(author);
				
				messageList.add(newMessage);

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
		return messageList;		
	}
	
	public void deleteMessage(Timestamp messageToDelete)
	{
		initConnection("jdbc/chatter");
		
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		
		try
		{
			//Prepare Statement
			preparedString = "DELETE FROM messages WHERE `timestamp` = ?;";
			preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
			preparedQuery.setTimestamp(1, messageToDelete);
			
			//Execute DELETE statement
			preparedQuery.executeUpdate();
			
			//TODO: Remove debug
			System.out.println("Executing  [" + preparedQuery.toString() + "] ");
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
	}
	
}
