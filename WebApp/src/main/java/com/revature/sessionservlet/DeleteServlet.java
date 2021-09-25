package com.revature.sessionservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.CharacterModel;
import com.revature.orm.ORM;

public class DeleteServlet extends HttpServlet{

	private static final long serialVersionUID = -5658844687164616861L;
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final HttpSession session = request.getSession();
		final CharacterModel characterModel = (CharacterModel) session.getAttribute("character_model");

		ORM.getInstance().removeObjectFromDb(characterModel);
		session.removeAttribute("character_model");
		response.sendRedirect("homeserv");
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
