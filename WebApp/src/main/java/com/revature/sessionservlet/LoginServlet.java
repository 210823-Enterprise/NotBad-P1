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

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -7393813005277884981L;
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final HttpSession session = request.getSession();
		CharacterModel characterModel = (CharacterModel) session.getAttribute("character_model");
		
		if(characterModel == null) {
			
			final String username = request.getParameter("username");
			final String password = request.getParameter("password");
			
			characterModel = ORM.getInstance().getObjectFromDb(CharacterModel.class, "username", username);
			if(characterModel != null && !characterModel.equalsPassword(password))
				characterModel = null;
		}
		
		final PrintWriter out = response.getWriter();
		if(characterModel != null) {
			session.setAttribute("character_model", characterModel);
			HTMLFormatter.writeFile("character.html", out, new String[] {
					characterModel.getUsername(),
					characterModel.getGender(),
					characterModel.getRace(),
					characterModel.getClazz(),
					characterModel.getSpecialAbility()
				});
		} else {
			out.println("Incorrect username/password. Please try again.");
		}
	}
		
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}