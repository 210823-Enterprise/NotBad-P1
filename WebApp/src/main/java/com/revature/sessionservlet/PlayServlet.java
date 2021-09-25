package com.revature.sessionservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.CharacterModel;
import com.revature.orm.ORM;
import com.revature.util.ServletConfiguration;

public class PlayServlet extends HttpServlet{

	private static final long serialVersionUID = 3907504483070371578L;
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		//triggers statically loaded data in servlet config
		ServletConfiguration.setUp();

		// when we trigger a get request at this servlet, it will return the character
		// object that's saved to the session
		
		// 1. grab the session
		final HttpSession session = request.getSession();
		
		// 3. after capturing the object, print the object's info to the screen
		final PrintWriter out = response.getWriter();
		
		/// let's generate an html page on the fly!
		out.println("<html><body>");
		
		out.println("<head><style>body  {background-image: url(goblin.gif);background-repeat: no-repeat;background-position: center;}</style></head>");
		
		out.println("<form>");
		out.println("<h4><label for=choice>A wild goblin appeared!</label></h4>");
		out.println("<br><br>");
		out.println("<label for=Attack>Attack</label><input type=radio value=Attack id=Attack name=choice");
		out.println("<br>");
		out.println("<label for=Run>Run</label><input type=radio value=Run id=Run name=choice");
		out.println("<br>");
		out.println("<input type=submit for=choice value=Choose action>");
		out.println("</form>");
		out.println("</body></html>");

		
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		//triggers statically loaded data in servlet config
		ServletConfiguration.setUp();	
		doGet(request, response);
	}
}
