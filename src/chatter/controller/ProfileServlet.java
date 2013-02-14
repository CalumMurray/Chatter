package chatter.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chatter.model.User;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet({ "/profile", "/profile/*" })
public class ProfileServlet extends HttpServlet 
{
	private	User user;
	private static final long serialVersionUID = 1L;

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
			//View from corresponding profile.jsp
			request.getRequestDispatcher("profile.jsp").forward(request, response);
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
