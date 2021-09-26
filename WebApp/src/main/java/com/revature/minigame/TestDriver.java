package com.revature.minigame;

import java.util.Scanner;

import com.revature.models.CharacterModel;
import com.revature.models.CharacterStats;

public class TestDriver {

	public static void main(final String[] args) {
		try(final Scanner scanner = new Scanner(System.in)) {
			String line = "";
			while(!line.equals("q")) {
				final CharacterModel model = new CharacterModel("ICY", "ICY", "male", "elf", "mage", "leader", new CharacterStats(1));
				Response response;
				if(line.isEmpty())
					response = Minigame.generateResponse(model, null);
				else
					response = Minigame.generateResponse(model, line);
				System.out.println("Encounter: " + response.getEncounter());
				System.out.println("Options: ");
				response.getActions().forEach(s -> System.out.println(" - " + s));
				
				line = scanner.nextLine();
			}
		}
	}
}
