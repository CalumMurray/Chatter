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

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet({ "/profile", "/profile/*" })
public class ProfileServlet extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;
	private FriendService friendService = new FriendService();
	private MessageService messageService = new MessageService();
	private	User user;
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		user = (User) request.getSession().getAttribute("user");
		System.out.println("User: " + user);
		//Check is user is logged in for session
		if (user == null)
		{
			response.sendRedirect("login");	//Not logged in yet - redirect to login page
			return;
		}
		else
		{
			//Otherwise show this page
			
			//TODO: Check null for attributes before fetching them again?
			
			
			request.getSession().setAttribute("following", countFollowing());
			request.getSession().setAttribute("followers", countFollowers());
			request.getSession().setAttribute("userMessages", getMessages(request));
			request.getRequestDispatcher("profile.jsp").forward(request, response);	//View from corresponding profile.jsp
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String messageContent = request.getParameter("message");
		String posterEmail = (String)((User) request.getSession().getAttribute("user")).getEmail();
		
		messageService.postMessage(posterEmail, messageContent);
		
		//Return to createMessage.jsp with success message
		request.setAttribute("successMessage", "Chat posted Successfully!");
		request.getRequestDispatcher("profile.jsp").forward(request, response);
	}

	
	private List<Message> getMessages(HttpServletRequest request)
	{
		MessageService messageService = new MessageService();
		return (List<Message>) messageService.fetchUserFriendMessages(user);
		

	}
	
	private int countFollowing()
	{
		friendService = new FriendService();
		List<User> friends = (List<User>) friendService.getFriends(user.getEmail());
		return friends.size();
		
	}
	
	private int countFollowers()
	{
		friendService = new FriendService();
		List<User> friends = (List<User>) friendService.getFollowers(user.getEmail());
		return friends.size();
		
	}
}
