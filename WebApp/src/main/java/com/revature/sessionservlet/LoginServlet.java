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
import com.revature.util.ServletConfiguration;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -7393813005277884981L;
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		ServletConfiguration.setUp();
		
		final HttpSession session = request.getSession();
		CharacterModel characterModel;
		
		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		
		if(username != null && password != null) {
			characterModel = ORM.getInstance().getObjectFromDb(CharacterModel.class, "username", username);
			
			if(characterModel != null && !characterModel.equalsPassword(password))
				characterModel = null;
		} else {
			characterModel = (CharacterModel) session.getAttribute("character_model");
		}
		
		final PrintWriter out = response.getWriter();
		if(characterModel != null) {
			session.setAttribute("character_model", characterModel);
			HTMLFormatter.writeFile("character.html", out, new String[] {
					characterModel.getUsername(),
					characterModel.getGender(),
					characterModel.getClazz(),
					characterModel.getRace(),
					characterModel.getSpecialAbility()
				});
		} else {
			HTMLFormatter.writeFile("index_login_error.html", out, new String[] { "Incorrect Username or Password" });
		}
	}
		
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}