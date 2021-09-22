package com.revature.sessionservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.Character;

public class HelperSessionServlet extends HttpServlet {

	private static final long serialVersionUID = -7393813005277884981L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// when we trigger a get request at this servlet, it will return the villain
		// object that's saved to the session
		
		// 1. grab the session
		HttpSession session = request.getSession();
		
		// 2. save the object retrieved from the session to a character object
		Character character = (Character) session.getAttribute("character"); 
		
		// 3. after capturing the object, print the object's info to the screen
		PrintWriter out = response.getWriter();
		
		/// let's generate an html page on the fly!
		out.println("<html><body>");
			
			if(character != null) {
				out.println("We have captured the villain from the session!");
				
				// print out html that shows the properties of the villain object captured
				out.println("<h1>Character Name: " + character.getName() + "</h1><br />");
				out.println("<b>Gender: " + character.getGender() + " </b><br/>");
				out.println("<i>Race: " + character.getRace() + " </i><br/>");
				out.println("<i>Class: " + character.getClazz() + " </i><br/>");
				out.println("<i>Special Ability: " + character.getSpecialAbility() + " </i><br/>");
				
			} else {
				out.println("<i>Couldn't find any characters.</i>");
			}
			
			out.println("</body></html>");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}