package com.revature.sessionservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.CharacterModel;
import com.revature.orm.ORM;

public class EditServlet extends HttpServlet{

	private static final long serialVersionUID = 8375387335881044255L;
	
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {	
		final HttpSession session = request.getSession();
		
		// grab stored character model
		final CharacterModel characterModel = (CharacterModel) session.getAttribute("character_model");
		
		//retrieve fields
		final String password = request.getParameter("password");
		if(password.length() > 1)
			characterModel.setPassword(request.getParameter("password"));
		characterModel.setGender(request.getParameter("gender"));
		characterModel.setRace(request.getParameter("race"));
		characterModel.setClazz(request.getParameter("class"));
		characterModel.setSpecialAbility(request.getParameter("specialability"));
		
		//update model
		ORM.getInstance().updateObjectInDb(characterModel);
		
		// 3. after capturing the object, print the object's info to the screen
		final PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("Character Updated! :D");
		out.println("</body></html>");
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
