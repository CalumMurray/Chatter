package chatter.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import chatter.model.User;
import chatter.service.FriendService;

/**
 * Servlet implementation class FriendsServlet
 */
@WebServlet({ "/friends", "/friends/*" })
public class FriendsServlet extends HttpServlet 
{
	private FriendService friendService = new FriendService();
	private User user;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Check is user is logged in for session
		user = (User) request.getSession().getAttribute("user");
		if (user == null)
		{
			response.sendRedirect("login");	//Not logged in yet - redirect to login page
			return;
		}
		else
		{	
			List<User> friends = friendService.getFollowing(user.getEmail());	//Get friends
			List<User> allUsers = friendService.getAllUsers(user.getEmail());	//Get all users
			
			//Loop through all users adding a flag saying whether they're friends of the currently logged-in user or not.
			for (User user : allUsers)
			{
				String userEmail = user.getEmail();
				for (User friend : friends)
				{
					if (friend.getEmail().equals(userEmail))
						user.setFriend(true);
				}
				
			}
			

			request.getSession().setAttribute("users", allUsers);
			request.getRequestDispatcher("friends.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
	}

}
