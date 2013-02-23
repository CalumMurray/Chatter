package chatter.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*-----------------------REST Interface-----------------------------
 * GET /logout - Close session and display logout page (logout.jsp)
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Close session
		request.getSession().removeAttribute("user");
		request.getSession().invalidate();
		//display goodbye page
		request.getRequestDispatcher("logout.jsp").forward(request, response);
	}

}
