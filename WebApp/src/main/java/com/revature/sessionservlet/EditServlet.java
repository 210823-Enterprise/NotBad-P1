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

public class EditServlet extends HttpServlet{

	private static final long serialVersionUID = 8375387335881044255L;
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		//triggers statically loaded data in servlet config
		ServletConfiguration.setUp();

		// when we trigger a get request at this servlet, it will return the character
		// object that's saved to the session
		
		// 1. grab the session
		final HttpSession session = request.getSession();
		
		// 2. save the object retrieved from the session to a character object
		CharacterModel characterGenetics = (CharacterModel)session.getAttribute("username");
		
		ORM.getInstance().updateObjectInDb(characterGenetics);
		
		// 3. after capturing the object, print the object's info to the screen
		final PrintWriter out = response.getWriter();
		
		/// let's generate an html page on the fly!
		out.println("<html><body>");
		out.println("Character Updated! :D");
		out.println("</body></html>");
		
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		//triggers statically loaded data in servlet config
		ServletConfiguration.setUp();
		doGet(request, response);
	}

}
