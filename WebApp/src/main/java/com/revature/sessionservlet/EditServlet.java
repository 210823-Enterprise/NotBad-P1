package com.revature.sessionservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.CharacterModel;
import com.revature.util.ServletConfiguration;

public class EditServlet extends HttpServlet{

	private static final long serialVersionUID = 8375387335881044255L;
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		//triggers statically loaded data in servlet config
		ServletConfiguration.setUp();
		
		// 1. grab the session
		final HttpSession session = request.getSession();
		
		// 2. save the object retrieved from the session to a character object
		final CharacterModel character = (CharacterModel) session.getAttribute("character"); 
		
		//ORM.getInstance().addObjectToDb(character);
		
		// 3. after capturing the object, print the object's info to the screen
		final PrintWriter out = response.getWriter();
		
		/// let's generate an html page on the fly!
		out.println("<html><body>");
			
			if(character != null) {
				out.println("<h1>Which character do you want to edit?</h1>");
				
				// print out html that shows the properties of the character object captured
				out.println("<h3>Character Name: " + character.getName() + "</h3><br />");
				out.println("<i>Gender: " + character.getGender() + " </i><br/>");
				out.println("<i>Race: " + character.getRace() + " </i><br/>");
				out.println("<i>Class: " + character.getClazz() + " </i><br/>");
				out.println("<i>Special Ability: " + character.getSpecialAbility() + " </i><br/>");
				
				out.println("<button name=simple_button type=button>Edit</button>");
				
			} else {
				out.println("<i>Couldn't find any characters.</i>");
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

