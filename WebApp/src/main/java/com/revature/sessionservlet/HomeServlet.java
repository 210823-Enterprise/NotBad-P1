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

public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 7751372469078935199L;
	
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
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
		
		final CharacterModel character = new CharacterModel(username, password, gender, race, clazz, specialAbility, new CharacterStats());
		
		final HttpSession session = request.getSession();
		session.setAttribute("character_model", character);

		final PrintWriter out = response.getWriter();
		if(ORM.getInstance().addObjectToDb(character)) {
			out.println("Character successfully created!");
			response.sendRedirect("loginserv");
		} else {
			out.println("Character not created. Please try again.");
			response.sendRedirect("index_create_error.html");
		}
	}

}
