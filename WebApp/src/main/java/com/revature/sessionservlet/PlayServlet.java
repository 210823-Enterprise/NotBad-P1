package com.revature.sessionservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.minigame.Minigame;
import com.revature.minigame.Response;
import com.revature.models.CharacterModel;
import com.revature.orm.ORM;
import com.revature.util.HTMLFormatter;

public class PlayServlet extends HttpServlet{

	private static final long serialVersionUID = 3907504483070371578L;
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final HttpSession session = request.getSession();
		final CharacterModel characterModel = (CharacterModel) session.getAttribute("character_model");
		
		if(characterModel == null)
			response.sendRedirect("homeserv");
		
		final Response result = Minigame.generateResponse(characterModel,request.getParameter("action"));
		String actions = "";
		for(final String str: result.getActions()) {
			actions += HTMLFormatter.createRadioButtion(str) + "<br>";
		}
		
		final PrintWriter out = response.getWriter();
		ORM.getInstance().updateObjectInDb(characterModel);
		HTMLFormatter.writeFile("game.html", out, new String[] {result.getEncounter(),actions});
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
