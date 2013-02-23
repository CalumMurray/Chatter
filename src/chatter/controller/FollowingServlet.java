package chatter.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chatter.model.User;
import chatter.service.FriendService;

/*-----------------------REST Interface-----------------------------
 * GET /following - Returns list (UserList?) of all users, showing people you're following.
 * POST /following - Add a user to people you're following
 * PUT /following - Same as POST
 * DELETE /following/<userEmail> - delete specific user you're following (stop following them) (or 404)
 */
@WebServlet({ "/following", "/following/*" })
public class FollowingServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private FriendService friendService = new FriendService();
	private User user;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if (checkLoggedIn(request))
		{
			//Logged in...
		
			//Simply list all users being followed..
			
			List<User> following = friendService.getFollowing(user.getEmail());	//Get friends
			List<User> allUsers = friendService.getAllUsers(user.getEmail());	//Get all users
			
			
			if (following.size() == 0)
			{
				//Return with a message if you aren't following anybody
				request.setAttribute("failureMessage", "You are currently not following anybody");
			}
				//Loop through all users adding a flag saying whether they're friends of the currently logged-in user or not.
				for (User user : allUsers)
				{
					String userEmail = user.getEmail();
					for (User friend : following)
					{
						if (friend.getEmail().equals(userEmail))
							user.setFriend(true);
					}
					
				}
				
				request.setAttribute("users", allUsers);
				request.getRequestDispatcher("following.jsp").forward(request, response);
				return;
			

		}
		else
		{
			//Not logged in - Redirect to login page
			response.sendRedirect("login");	
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String userToFollow = (String) request.getParameter("userToFollow");
		
		//Add user to friends table
		friendService.addFollowing(user.getEmail(), userToFollow);
		request.setAttribute("returnMessage", "You are now following " + userToFollow);

		response.sendRedirect(request.getContextPath() + "/following");
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);	//Same as POST
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String uri = request.getRequestURI();
		//Stop following user - Delete row in friends table
		int lastSeparator = request.getRequestURI().lastIndexOf('/');
		String followingToDelete = request.getRequestURI().substring(lastSeparator + 1);
			//TODO: Remove debug
			System.out.println("Attempting to delete " + followingToDelete);
		
		friendService.deleteFollowing(user.getEmail(), followingToDelete);
		
		request.setAttribute("deleteMessage", "You've stopped following " + followingToDelete);
		response.sendRedirect(request.getContextPath() + "/following");

	}

	
	private boolean checkLoggedIn(HttpServletRequest request)
	{
		user = (User) request.getSession().getAttribute("user");
		//Check is user is logged in for session
		return (user != null);
			
	}
}
