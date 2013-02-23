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
		initConnection();
		
		
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		try 
    	{

			//Prepare Statement
			preparedString = "INSERT INTO messages (`timestamp`, user, content) VALUES (?, ?, ?);";
    		preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
    		preparedQuery.setTimestamp(1, new Timestamp(new Date().getTime()));	//Get current timestamp
    		preparedQuery.setString(2, userEmail);
    		preparedQuery.setString(3, messageContent);
    		
    		//Execute Statement
    		preparedQuery.executeUpdate();  	
    		
	    		//TODO: Remove debug
				System.out.println("Inserting message with  [" + preparedQuery.toString() + "] ");
		
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
		
		
		initConnection();
		
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		List<Message> messageList = null;
		try 
		{
			
			/* Union query produces a list of email addresses - 
		       the logged-in user for plus all people he's following
		    */
			//Prepare Statement
			preparedString = "SELECT id, `timestamp`,  user,  firstname,  lastname,  content " +													/* First part of the UNION returns friends messages */
					          "FROM " +
					          "messages " +
					          "INNER JOIN " +
					          "(SELECT ? AS email " +	
					          "UNION " +
					          "SELECT friend AS email FROM friends WHERE email = ?) user_and_friends ON messages.user = user_and_friends.email " + /* Plus all his friends into one list joined against messages */
					          "INNER JOIN users ON user_and_friends.email = users.email " +														   /* Join against the users table for name info */
					          "ORDER BY `timestamp` DESC;";
			
			preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
			preparedQuery.setString(1, user.getEmail());
			preparedQuery.setString(2, user.getEmail());
			
			//TODO: Remove debug
			System.out.println("Get all messages with SQL:  [" + preparedQuery.toString() + "] ");
			
			//Execute Statement
			ResultSet resultSet = preparedQuery.executeQuery();  	
			

			messageList = new ArrayList<Message>();
			//Extract messages into list of Beans
			while (resultSet.next())
			{
				//Add new message to list for each message from the user
				Message newMessage = new Message();
				
				newMessage.setId(resultSet.getInt("id"));
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
	
	public void deleteMessage(int messageToDelete)
	{
		initConnection();
		
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		
		try
		{
			//Prepare Statement
			preparedString = "DELETE FROM messages WHERE id = ?;";
			preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
			preparedQuery.setInt(1, messageToDelete);
			
			//Execute DELETE statement
			preparedQuery.executeUpdate();
			
			//TODO: Remove debug
			System.out.println("Deleting message with  [" + preparedQuery.toString() + "] ");
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
	
	public Message fetchMessage(int id)
	{
		initConnection();
		
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		Message message = new Message();
		try 
		{
			//Prepare Statement
			preparedString = "SELECT * FROM messages " +
							 "INNER JOIN users ON users.email = messages.user " +
							 "WHERE id = ?;";
			
			preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
			preparedQuery.setInt(1, id);
			
				//TODO: Remove debug
				System.out.println("Get single message with SQL:  [" + preparedQuery.toString() + "] ");
			
			//Execute Statement
			ResultSet resultSet = preparedQuery.executeQuery();  	
			
			//Extract message into a Bean
			while (resultSet.next())
			{
				message.setId(resultSet.getInt("id"));
				message.setTimeStamp(resultSet.getTimestamp("timestamp"));
				message.setContent(resultSet.getString("content"));
				
				//Get author as User object
				User author = new User();
				author.setEmail(resultSet.getString("user"));
				author.setFirstName(resultSet.getString("firstname"));
				author.setLastName(resultSet.getString("lastname"));
				
				message.setAuthor(author);
				
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
		return message;		
	}
	
	public List<Message> getUserMessages(String userEmail)
	{
		initConnection();
		
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		List<Message> messageList = null;
		try 
		{
			
			//Prepare Statement
			preparedString = "SELECT * FROM messages " +
						     "INNER JOIN users ON users.email = messages.user " +
						     "WHERE user = ?;";
			
			preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
			preparedQuery.setString(1, userEmail);
			
			//TODO: Remove debug
			System.out.println("Get a user's messages with SQL:  [" + preparedQuery.toString() + "] ");
			
			//Execute Statement
			ResultSet resultSet = preparedQuery.executeQuery();  	
			

			messageList = new ArrayList<Message>();
			//Extract messages into list of Beans
			while (resultSet.next())
			{
				//Add new message to list for each message from the user
				Message newMessage = new Message();
				
				newMessage.setId(resultSet.getInt("id"));
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
	
}
