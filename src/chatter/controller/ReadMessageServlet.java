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
import chatter.service.MessageService;

/**
 * Servlet implementation class ReadMessageServlet
 */
@WebServlet({ "/read/message", "/read/message/*" })
public class ReadMessageServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private User user;
	private List<Message> userMessages;
	
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
			response.sendRedirect("../login");	//Not logged in yet - redirect to login page
			return;
		}
		else
		{	//Logged in
			MessageService messageService = new MessageService();
			userMessages = (List<Message>) messageService.getUserMessages(user);
			request.getSession().setAttribute("userMessages", userMessages);	//TODO Session or just Page scope?
			request.getRequestDispatcher("../readMessage.jsp").forward(request, response);
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
