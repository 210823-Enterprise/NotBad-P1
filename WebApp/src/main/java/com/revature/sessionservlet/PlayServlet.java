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
		
		if(characterModel == null || characterModel.getId() == -1) {
			response.sendRedirect("homeserv");
			return;
		}
		
		final Response result = Minigame.generateResponse(characterModel,request.getParameter("action"));
		String players = "";
		for(final String name: result.getPlayers()) {
			players += name + "<br>";
		}
		String actions = "";
		for(final String str: result.getActions()) {
			actions += HTMLFormatter.createRadioButtion(str) + "<br>";
		}
		String button = "Next";
		if(!actions.isEmpty())
			button = "Select";
		
		final PrintWriter out = response.getWriter();
		ORM.getInstance().updateObjectInDb(characterModel);
		HTMLFormatter.writeFile("game.html", out, new String[] {
				result.getImage(),
				result.getEncounter(),
				characterModel.getGameData().getHealth() + "/" + characterModel.getGameData().maxHealth(),
				characterModel.getGameData().getMana() + "/" + characterModel.getGameData().maxMana(),
				characterModel.getGameData().getStamina() + "/" + characterModel.getGameData().maxStamina(),
				characterModel.getGameData().getCharacterLevel() + "",
				actions,
				button,
				players
			});
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
