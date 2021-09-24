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

public class HelperSessionServlet extends HttpServlet {

	private static final long serialVersionUID = -7393813005277884981L;
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		//triggers statically loaded data in servlet config
		ServletConfiguration.setUp();

		// when we trigger a get request at this servlet, it will return the character
		// object that's saved to the session
		
		// 1. grab the session
		final HttpSession session = request.getSession();
		
		// 2. save the object retrieved from the session to a character object
//		Character character = (Character) session.getAttribute("character"); 
		
		final List<CharacterModel> characterList = ORM.getInstance().getAllObjectsFromDb(CharacterModel.class);
		
		// 3. after capturing the object, print the object's info to the screen
		final PrintWriter out = response.getWriter();
		
		/// let's generate an html page on the fly!
		out.println("<html><body>");
			
		for(final CharacterModel character : characterList) {
			
			// print out html that shows the properties of the character object captured
			out.println("<h3>Character Name: " + character.getName() + "</h3><br />");
			out.println("<i>Gender: " + character.getGender() + " </i><br/>");
			out.println("<i>Race: " + character.getRace() + " </i><br/>");
			out.println("<i>Class: " + character.getClazz() + " </i><br/>");
			out.println("<i>Special Ability: " + character.getSpecialAbility() + " </i><br/>");
			
			} 

			out.println("</body></html>");
		
			//ORM.getInstance().getObjectFromDb(character.getClass(),"name", character.getName());
			
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		//triggers statically loaded data in servlet config
		ServletConfiguration.setUp();
		
		
		
		doGet(request, response);
	}

}