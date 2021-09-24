package com.revature.sessionservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.Character;
import com.revature.orm.ORM;

public class HelperSessionServlet extends HttpServlet {

	private static final long serialVersionUID = -7393813005277884981L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// when we trigger a get request at this servlet, it will return the character
		// object that's saved to the session
		
		// 1. grab the session
		HttpSession session = request.getSession();
		
		// 2. save the object retrieved from the session to a character object
//		Character character = (Character) session.getAttribute("character"); 
		
		List<Character> characterList = ORM.getInstance().getAllObjectsFromDb(Character.class);
		
		// 3. after capturing the object, print the object's info to the screen
		PrintWriter out = response.getWriter();
		
		/// let's generate an html page on the fly!
		out.println("<html><body>");
			
		for(Character character : characterList) {
			
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		doGet(request, response);
	}

}