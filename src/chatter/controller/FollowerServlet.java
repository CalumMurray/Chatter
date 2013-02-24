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
 * GET /follower - Returns list (UserList?) of all users following the currently logged in user
 */
@WebServlet({ "/followers", "/followers/*" })
public class FollowerServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private List<User> followerList;
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
			followerList = friendService.getFollowers(user.getEmail());
			
			if (followerList.size() == 0)
				request.setAttribute("failureMessage", "Nobody is following you currently");
			
			request.setAttribute("followerList", followerList);
			request.getRequestDispatcher("followers.jsp").forward(request, response);
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
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

	}

	private boolean checkLoggedIn(HttpServletRequest request)
	{
		user = (User) request.getSession().getAttribute("user");
		//Check is user is logged in for session
		return (user != null);
			
	}
	
}
