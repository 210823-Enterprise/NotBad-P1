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
import com.revature.util.HTMLFormatter;

public class EditServlet extends HttpServlet{

	private static final long serialVersionUID = 8375387335881044255L;
	
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {	
		final HttpSession session = request.getSession();
		
		// grab stored character model
		final CharacterModel characterModel = (CharacterModel) session.getAttribute("character_model");
		if(characterModel == null || characterModel.getId() == -1) {
			response.sendRedirect("homeserv");
			return;
		}	
		final String password = request.getParameter("password");
		
		if(characterModel.equalsPassword(password)) {
			//retrieve fields
			final String edit_password = request.getParameter("edit_password");
			if(edit_password.length() > 2)
				characterModel.setPassword(request.getParameter("password"));
			characterModel.setGender(request.getParameter("gender"));
			characterModel.setRace(request.getParameter("race"));
			characterModel.setClazz(request.getParameter("class"));
			characterModel.setSpecialAbility(request.getParameter("specialability"));
			
			ORM.getInstance().updateObjectInDb(characterModel);
			response.sendRedirect("loginserv");
		} else {
			final PrintWriter out = response.getWriter();
			HTMLFormatter.writeFile("character_edit_error.html", out, new String[] {
					characterModel.getUsername(),
					characterModel.getGender(),
					characterModel.getRace(),
					characterModel.getClazz(),
					characterModel.getSpecialAbility(),
					"Incorrect password"
				});
		}
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
