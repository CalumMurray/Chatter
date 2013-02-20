package chatter.controller;

import java.io.IOException;
import java.util.List;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import chatter.model.User;
import chatter.service.FriendService;

/**
 * Servlet implementation class AddFriendServlet
 */
@WebServlet({"/add/friend", "/add/friend/*"})
public class AddFriendServlet extends HttpServlet 
{
	private User user;
	FriendService friendService = new FriendService();
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
			response.sendRedirect("../login");	//Not logged in yet - redirect to login page
			return;
		}
		else
		{	
			//Add specific friend from URI?
			String requestURI = request.getRequestURI();
			
			if (requestURI.equals(request.getContextPath() + "/add/friend"))
			{
				System.out.println("RequestURI: " + requestURI);
				System.out.println("Context path: " + request.getContextPath() + " + /add/friend");
				//There is not a specific friend to add
				request.setAttribute("message", "No user selected.");
				request.getRequestDispatcher("../friends.jsp").forward(request, response);
				return;
			}
			else
			{
				
				//There is a specific friend to add...
				
				//Extract friends email from url
				int lastSeparator = requestURI.lastIndexOf('/');
				String friendToAdd = requestURI.substring(lastSeparator + 1);
				System.out.println("Attempting to add " + friendToAdd);
				friendService.addFriend(user.getEmail(), friendToAdd);
				
				request.setAttribute("message", "You are now following " + friendToAdd);
				request.getRequestDispatcher("../../friends.jsp").forward(request, response);
				return;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String friendToAdd = (String) request.getParameter("friend");
		
		
		List<User> possibleFriends = (List<User>) friendService.findFriend(friendToAdd);
		
		if (possibleFriends == null || possibleFriends.size() == 0)
		{
			//Failure - no such friend
			request.setAttribute("message", "No such user exists");
		}
		else if (possibleFriends.size() > 1)
		{
			//show possibilities
			request.setAttribute("possibleFriends", possibleFriends);
			
		}
		else if (possibleFriends.size() == 1)
		{			
			//Add single friend
			friendService.addFriend(user.getEmail(), possibleFriends.get(0).getEmail());
			request.setAttribute("message", "You are now following " + possibleFriends.get(0).getFirstName());
		}
		
		request.getRequestDispatcher("../addFriend.jsp").forward(request, response);
	}

}
