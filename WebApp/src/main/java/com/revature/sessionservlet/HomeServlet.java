package com.revature.sessionservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.CharacterModel;
import com.revature.models.CharacterStats;
import com.revature.orm.ORM;
import com.revature.util.HTMLFormatter;
import com.revature.util.ServletConfiguration;

public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 7751372469078935199L;
	
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		ServletConfiguration.setUp();
		
		request.getSession().removeAttribute("character_model");
		response.sendRedirect("index.html");
	}


	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		final String gender = request.getParameter("gender");
		final String race = request.getParameter("race");
		final String clazz = request.getParameter("class");
		final String specialAbility = request.getParameter("specialability");
		

		final HttpSession session = request.getSession();
		final PrintWriter out = response.getWriter();
		
		if(username.length() < 2) {
			HTMLFormatter.writeFile("index_create_error.html", out, new String[] { "Username too short" });
		} else if(password.length() < 2) {
			HTMLFormatter.writeFile("index_create_error.html", out, new String[] { "Password too short" });
		} else {
			final CharacterModel character = new CharacterModel(username, password, gender, race, clazz, specialAbility, new CharacterStats(1));
			
			if(ORM.getInstance().addObjectToDb(character)) {
				response.sendRedirect("loginserv");
				session.setAttribute("character_model", character);
			} else {
				HTMLFormatter.writeFile("index_create_error.html", out, new String[] { "Username is already taken, try another" });
			}
		}	
	}
}
