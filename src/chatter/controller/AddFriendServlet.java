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
 * Servlet implementation class AddFriendServlet
 */
@WebServlet("/add/friend")
public class AddFriendServlet extends HttpServlet 
{
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
			response.sendRedirect("../login");	//Not logged in yet - redirect to login page
			return;
		}
		else
		{	
			request.getRequestDispatcher("../addFriend.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String friendToAdd = (String) request.getAttribute("friend");
		
		FriendService friendService = new FriendService();
		User newFriend = friendService.findFriend(friendToAdd);
		
	}

}
