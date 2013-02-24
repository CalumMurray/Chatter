package chatter.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chatter.model.Message;
import chatter.model.User;
import chatter.service.FriendService;
import chatter.service.MessageService;
import chatter.service.RegisterService;
import chatter.service.UserService;

/*-----------------------REST Interface-----------------------------
 * GET /profile - Shows user's profile, including all your's and following's messages in a feed.  Allows to post message also. Displays as profile.jsp
 * GET /profile/<userEmail> - Show specific user's profile (or 404) 
 * DELETE /profile - Delete account.
 */
@WebServlet({ "/profile", "/profile/*" })
public class ProfileServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private FriendService friendService = new FriendService();
	private UserService userService = new UserService();
	private MessageService messageService = new MessageService();
	private RegisterService registerService = new RegisterService();
	
	private	User loggedInUser;
	private	User otherUser;
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		loggedInUser = (User) request.getSession().getAttribute("user");
		
		//Check is user is logged in for session
		if (loggedInUser == null)
		{
			response.sendRedirect(request.getContextPath() + "/login");	//Not logged in yet - redirect to login page
			return;
		}
		else
		{
			if (request.getRequestURI().equals(request.getContextPath() + "/profile"))
			{
				
				//Show logged in user's profile
			
				request.getSession().setAttribute("following", countFollowing(loggedInUser));
				request.getSession().setAttribute("followers", countFollowers(loggedInUser));
				request.getSession().setAttribute("userMessages", getAllMessages(loggedInUser));
				request.getRequestDispatcher("profile.jsp").forward(request, response);	//View from corresponding profile.jsp
				return;
			}
			else
			{
				//Show specific user's profile specified in URI - /profile/<userEmail>
				
				int lastSeparator = request.getRequestURI().lastIndexOf('/');
				String uriString = request.getRequestURI().substring(lastSeparator + 1);
				
				
				otherUser = userService.getProfileUser(uriString);
				//Check to display 404
				if (otherUser == null)
				{
					request.getRequestDispatcher("../404.jsp").forward(request, response);
					return;
				}

				request.setAttribute("user", otherUser);
				request.setAttribute("otherUser", true);	//To differentiate between logged-in (Session) user and one we're viewing (Page)
				request.setAttribute("following", countFollowing(otherUser));
				request.setAttribute("followers", countFollowers(otherUser));
				request.setAttribute("userMessages", getUserMessages(otherUser));//Only view the user's messages, not their friends' etc.
				request.getRequestDispatcher("../profile.jsp").forward(request, response);	//View from corresponding profile.jsp
				return;

			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}

	/**
	 * @see HttpServlet#doPDelete(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//Delete Account
		registerService.deleteUser(loggedInUser.getEmail());
		request.getSession().removeAttribute("user");
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/login");
	}
	
	
	private List<Message> getAllMessages(User user)
	{
		messageService = new MessageService();
		return (List<Message>) messageService.fetchUserFriendMessages(user);
		

	}
	
	private List<Message> getUserMessages(User user)
	{
		messageService = new MessageService();
		return (List<Message>) messageService.getUserMessages(user.getEmail());
		

	}

	
	private int countFollowing(User user)
	{
		friendService = new FriendService();
		List<User> friends = (List<User>) friendService.getFollowing(user.getEmail());
		return friends.size();
		
	}
	
	private int countFollowers(User user)
	{
		friendService = new FriendService();
		List<User> friends = (List<User>) friendService.getFollowers(user.getEmail());
		return friends.size();
		
	}
}
