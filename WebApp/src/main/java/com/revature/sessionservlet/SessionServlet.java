package com.revature.sessionservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.CharacterModel;
import com.revature.orm.ORM;
import com.revature.util.ServletConfiguration;

public class SessionServlet extends HttpServlet {

	private static final long serialVersionUID = 7751372469078935199L;
	
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		//triggers statically loaded data in servlet config
		ServletConfiguration.setUp();
		
				
		/*
		 * HttpSession is an interface provides us with the functionality to store user information across out application
		 */
		
		final HttpSession session = request.getSession();
		final PrintWriter out = response.getWriter();

	}


	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		//triggers statically loaded data in servlet config
		ServletConfiguration.setUp();
		
		// Capture the input from the HTTP post request and create a java object
		
		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		final String gender = request.getParameter("gender");
		final String race = request.getParameter("race");
		final String clazz = request.getParameter("class");
		final String specialAbility = request.getParameter("specialability");
		
		
		// 2. Convert the capture params into an object by passing it thru the character Constructor
		final CharacterModel character = new CharacterModel(username, password, gender, race, clazz, specialAbility);
		
		// 3. Grab the HttpSession from the request obj
		final HttpSession session = request.getSession();
		
		// 4. and send the custom character to the session
		session.setAttribute("character", character);
		
		// 5. Print to the screen that the character object has been set to the session
		final PrintWriter out = response.getWriter();
		out.write(new ObjectMapper().writeValueAsString(character));
		//out.println("A character has been created...(in the session)");
		
		// now we have to create a servlet to retrieve the session
		if(ORM.getInstance().addObjectToDb(character)) {
			out.println("Character successfully created!");
		} else {
			out.println("Character not created. Please try again.");
		}
				
		
		
	}

}
