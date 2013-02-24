package chatter.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chatter.model.*;
import chatter.service.*;
import com.google.gson.Gson;

/*-----------------------REST Interface-----------------------------
 * GET /messages - Returns list (MessageList?) of all users and following's messages.
 * GET /messages/<id> - returns specific message (or 404)
 * GET /messages/<user email> - returns list (MessageList?) of messages from specific user (or 404)
 * POST /messages - creates a new message from logged-in user at current time
 * PUT /messages - Same as POST
 * DELETE /messages/<id> - delete specific message (only if created from logged-in user) (or 404)
 */
@WebServlet({ "/messages", "/messages/*" })
public class MessageServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private User user;
	private MessageService messageService = new MessageService();
	private List<Message> userMessages;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		user = (User) request.getSession().getAttribute("user");
		System.out.println("User: " + user);
		//Check is user is logged in for session
		if (checkLoggedIn(request))
		{
			//Logged-in...
			
			if (request.getRequestURI().equals(request.getContextPath() + "/messages"))
			{
				//Simply ask for all user's and people their following's messages:
				userMessages = (List<Message>) messageService.fetchUserFriendMessages(user);
				request.setAttribute("userMessages", userMessages);	
				request.getRequestDispatcher("/messages.jsp").forward(request, response);	//TODO: Correct forward jsp page?
				return;
			}
			else
			{
				
				//TODO: Check for 404 x2
				
				//Check uri for "/messages/<id>"  - Get single Message:
				int lastSeparator = request.getRequestURI().lastIndexOf('/');
				String uriString = request.getRequestURI().substring(lastSeparator + 1);
				
				
				try
				{
					//Check uri for "/messages/<id>"  - Get single Message
					int messageId = Integer.valueOf(uriString);
					Message message = messageService.fetchMessage(messageId);
					if (message == null)
					{
						request.getRequestDispatcher("404.jsp").forward(request, response);
						return;
					}
					request.setAttribute("message", message);	
					request.getRequestDispatcher("/message.jsp").forward(request, response);	//TODO: Correct forward jsp page?
					
				}
				catch (NumberFormatException nfe)
				{
					//Not an id, must be a user
					// check for "/messages/<userEmail>" - Get all a user's messages:
					userMessages = messageService.getUserMessages(uriString);
					request.setAttribute("userMessages", userMessages);	
					request.getRequestDispatcher("profile.jsp").forward(request, response);	//TODO: Correct forward jsp page?
				}

				return;
			}
			

		}
		else
		{	//Not logged in - Redirect to login page
			response.sendRedirect(request.getContextPath() + "/login");	
			return;

		}
		
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Create message!
		String messageContent = request.getParameter("message");
		String authorEmail = (String)((User) request.getSession().getAttribute("user")).getEmail();
		
		messageService.postMessage(authorEmail, messageContent);
		
		//Return to profile.jsp with success message
		request.setAttribute("successMessage", "Chat posted Successfully!");
		//request.getRequestDispatcher("profile").forward(request, response);	//Go back to posting page
		response.sendRedirect("profile");
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response); //Same as POST
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Delete specific message (IDed by "/<id>" and ONLY if it was posted by the logged-in user)

		response.setContentType("text/plain");
		int lastSeparator = request.getRequestURI().lastIndexOf('/');
		String messageToDelete = request.getRequestURI().substring(lastSeparator + 1);

			
		try 
		{																
			int messageId= Integer.valueOf(messageToDelete);
			messageService.deleteMessage(messageId);
			//request.setAttribute("message", "Message deleted");
			//request.getRequestDispatcher("/message.jsp");		
            response.getWriter().println("Message deleted");
		}
		catch (NumberFormatException nfe)
		{
			//request.setAttribute("failureMessage", "No such message " + messageToDelete);
			response.getWriter().println("No such message " + messageToDelete);
			return;
		}
		
		
		return;
		
		
	}

	
	private boolean checkLoggedIn(HttpServletRequest request)
	{
		user = (User) request.getSession().getAttribute("user");
		//Check is user is logged in for session
		return (user != null);
			
	}
}
