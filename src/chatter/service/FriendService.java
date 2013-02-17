package chatter.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import chatter.model.User;

public class FriendService extends DatabaseConnector
{

	public List<User> findFriend(String friendToAdd)
	{
		
		//Possibly extract first and last name
		String firstName = null;
		String lastName = null;
		if (friendToAdd.contains(" "))
		{
			firstName = friendToAdd.substring(0, friendToAdd.indexOf(" ") - 1);
			lastName = friendToAdd.substring(friendToAdd.indexOf(" ") - 1, friendToAdd.length() - 1);
			System.out.println("\nFriend to Add: First - " + firstName + "\n\tLast - " + lastName);
		}
		
		
		initConnection("jdbc/chatter");
		
		List<User> userList = new ArrayList<User>();
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		try 
		{
			//Prepare Statement
			preparedString = "SELECT * FROM users WHERE " +
							 "email = ? " +
							 "OR firstname = ? " +
							 "OR lastname = ? " +
							 "OR firstname = ? " +
							 "OR lastname = ?;";
			preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
			preparedQuery.setString(1, friendToAdd);
			preparedQuery.setString(2, friendToAdd);
			preparedQuery.setString(3, friendToAdd);
			preparedQuery.setString(4, firstName);
			preparedQuery.setString(5, lastName);
			
			//TODO: Remove debug
			System.out.println("Get user with SQL:  [" + preparedQuery.toString() + "] ");
			
			//Execute Statement
			ResultSet resultSet = preparedQuery.executeQuery();  	
			
			//No user by search terms
			if (!resultSet.first())
				return null;
			
			
			//Extract users into Bean List
			while (resultSet.next())
			{
				//Add new message to list for each message from the user
				User newUser = new User();
				
				newUser.setEmail(resultSet.getString("email"));
				newUser.setFirstName(resultSet.getString("firstname"));
				newUser.setLastName(resultSet.getString("lastname"));
				
				userList.add(newUser);

			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return userList;		
	}

	public void addFriend(String email, String friendEmail)
	{
		initConnection("jdbc/chatter");
		
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		try 
		{
			//Prepare Statement
			preparedString = "INSERT INTO friends VALUES (?, ?);";
			preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
			preparedQuery.setString(1, email);
			preparedQuery.setString(2, friendEmail);

			//TODO: Remove debug
			System.out.println("Add user with SQL:  [" + preparedQuery.toString() + "] ");
			
			//Execute Statement
			preparedQuery.executeUpdate();  	
			

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public List<User> getFriends(String userEmail)
	{
		initConnection("jdbc/chatter");
		
		List<User> friendsList = new ArrayList<User>();
		String preparedString = null;
		PreparedStatement preparedQuery = null;
		try 
		{
			//Prepare Statement - Get all user's friends with a JOIN
			preparedString = "SELECT users.email, users.firstname, users.lastname " +
							 "FROM friends " +
							 "INNER JOIN users " +
							 "ON friends.friend = users.email " +
							 "WHERE friends.email = ?;";
			preparedQuery = (PreparedStatement) connection.prepareStatement(preparedString);
			preparedQuery.setString(1, userEmail);


			//TODO: Remove debug
			System.out.println("Select all user's friends with SQL:  [" + preparedQuery.toString() + "] ");
			
			//Execute Statement
			ResultSet resultSet = preparedQuery.executeQuery();  	
			
			while (resultSet.next())
			{
				User newFriend = new User();
				newFriend.setEmail(resultSet.getString("email"));
				newFriend.setFirstName(resultSet.getString("firstname"));
				newFriend.setLastName(resultSet.getString("lastname"));
				
				friendsList.add(newFriend);
			}
			

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return friendsList;
	}
	
}