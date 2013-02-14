package chatter.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import chatter.model.User;
import chatter.service.LoginService;
import chatter.service.SecurityUtils;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = { "/login"}, initParams = {@WebInitParam(name = "data-source", value = "jdbc/chatter", description = "Jdbc link to chatter.db")})
public class LoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	

	
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
		LoginService loginService = new LoginService();
		boolean authenticated = loginService.authenticate(email, hashedPass);
			System.out.println("Authenticated:  " + (authenticated ? "true" : "false"));
		
		//If logged in correctly
		if (authenticated)
		{
			User user = loginService.getUser(email);
			System.out.println("----USER----:\n" + user.getEmail());
			System.out.println(user.getFirstName());
			System.out.println(user.getLastName());
			
			//Set a session User object - Remember user as LOGGED IN
			request.getSession().setAttribute("user", user);
			
			//Success, go to main profile page
			response.sendRedirect("profile");
			return;
		}
		else
		{
			//if incorrect login, return to login page with message
			request.setAttribute("message", "Invalid Username or Password.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		
		
				
		
	}
	
	
	
	
	
	
	

}
