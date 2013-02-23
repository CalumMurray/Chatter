package chatter.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import chatter.model.User;
import chatter.service.LoginService;
import chatter.service.SecurityUtils;


/*-----------------------REST Interface-----------------------------
 * GET /login - Displays login page (login.jsp)
 * POST /login - Authenticates user and logs them in to their profile (otherwise redirects back to login)
 */
@WebServlet(urlPatterns = { "/login"})
public class LoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private LoginService loginService = new LoginService();

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	
		//View from corresponding login.jsp
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//Hash password before storing in database.  Compare hereafter with hashes only
		String hashedPass = password;
		try 
		{
			hashedPass = SecurityUtils.sha1(password);
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}	
		
		//Authenticate login
		boolean authenticated = loginService.authenticate(email, hashedPass);
		
			//TODO: Remove debug
			System.out.println("Authenticated:  " + (authenticated ? "true" : "false"));
		
		//If logged in correctly
		if (authenticated)
		{
			User user = loginService.getUser(email);
			
			//Set a session User object - Remember user as LOGGED IN
			request.getSession().setAttribute("user", user);
			
			//Success, go to main profile page
			response.sendRedirect("profile");
			return;
		}
		else
		{
			//if incorrect login, return to login page with message
			request.setAttribute("failureMessage", "Invalid Username or Password.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		
		
				
		
	}
	
	
	
	
	
	
	

}
