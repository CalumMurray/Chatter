package chatter.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chatter.model.User;
import chatter.service.MessageService;

/**
 * Servlet implementation class CreateMessageServlet
 */
@WebServlet("/create/message")
public class CreateMessageServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private User user;
	private MessageService messageService = new MessageService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		user = (User) request.getSession().getAttribute("user");
		
		//Check is user is logged in for session
		if (user == null)
		{
			//Not logged in yet - redirect to login page with message
			request.setAttribute("message", "Not logged in.");
			response.sendRedirect("../login");	
			return;
		}
		else
		{
			//Otherwise show this corresponding createMessage.jsp page
			request.getRequestDispatcher("../createMessage.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//TODO: Add submitted chat message to database
		
		String messageContent = request.getParameter("message");
		String authorEmail = (String)((User) request.getSession().getAttribute("user")).getEmail();
		
		messageService.postMessage(authorEmail, messageContent);
		
		//Return to createMessage.jsp with success message
		request.setAttribute("successMessage", "Chat posted Successfully!");
		request.getRequestDispatcher("../createMessage.jsp").forward(request, response);
	}

}
