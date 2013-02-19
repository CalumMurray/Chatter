package chatter.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chatter.service.RegisterService;
import chatter.service.SecurityUtils;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	private RegisterService regService = new RegisterService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//View from corresponding register.jsp page
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Get register form data
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
	
	
		//Check database for duplicate email, i.e. account already exists
		boolean duplicateExists = regService.checkEmail(email);
		if (duplicateExists)
		{
			//If email already exists, return to register page with message
			request.setAttribute("message", "User with that email already registered.");
			request.getRequestDispatcher("register.jsp").forward(request, response);	
			return;
		}
		else
		{
			//Make new user
			try 
			{
				regService.createNewUser(email, firstname, lastname, SecurityUtils.sha1(password));
			} 
			catch (NoSuchAlgorithmException e) 
			{
				e.printStackTrace();
			}
			response.sendRedirect("login");	//Success, go to login page
			return;	
		}
		
				
	}

}
