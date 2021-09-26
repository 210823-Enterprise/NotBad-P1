package com.revature.minigame;

import java.io.IOException;

import com.revature.models.CharacterModel;

public class Minigame {
	
	public static Response generateResponse(final CharacterModel characterModel, final String action) throws IOException {
		final Response response = new Response();
		if(action == null) {
			response.setEncounter("A goblin stands in your path, do you:");
			response.addAction("Cast Firebolt");
			response.addAction("Cast Protection");
			response.addAction("Charm the Golbin");
			response.addAction("Confuse the Golbin");
			response.addAction("Run Away");
		} else {
			response.setEncounter("You have defeated the goblin!");
		}
		return response;
	}
}
