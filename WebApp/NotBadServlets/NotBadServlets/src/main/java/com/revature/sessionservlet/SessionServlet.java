package com.revature.sessionservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Character;

public class SessionServlet extends HttpServlet {

	private static final long serialVersionUID = 7751372469078935199L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Character hochiminh = new Character("Ho Chi Minh", "male", "human", "Jack-of-all-trades", "Leadership");
		
		/*
		 * HttpSession is an interface provides us with the functionality to store user information across out application
		 */
		
		HttpSession session = request.getSession();
		
		session.setAttribute("the-hero", hochiminh);
		
		PrintWriter out = response.getWriter();
		
		out.write(new ObjectMapper().writeValueAsString(hochiminh));
		
		out.println("Ho Chi Minh has unified Vietnam");
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Capture the input from the HTTP post request and create a java object
		
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String race = request.getParameter("race");
		String clazz = request.getParameter("class");
		String specialAbility = request.getParameter("specialability");
		
		
		// 2. Convert the capture params into an object by passing it thru the SuperVillain Constructor
		Character character = new Character(name, gender, race, clazz, specialAbility);
		
		// 3. Grab the HttpSession from the request obj
		HttpSession session = request.getSession();
		
		// 4. and send the custom villain to the session
		session.setAttribute("the-hero", character);
		
		// 5. Print to the screen that the villain object has been set to the session
		PrintWriter out = response.getWriter();
		out.write(new ObjectMapper().writeValueAsString(character));
		out.println("A character has been created...(in the session)");
		
		// now we have to create a servlet to retrieve the session
		
		
		
		
		
	}

}