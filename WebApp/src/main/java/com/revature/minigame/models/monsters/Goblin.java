package com.revature.minigame.models.monsters;

public class Goblin extends Monster {

	public Goblin() {
		super(String.format("%s Goblin",randomAttribute()), "goblin.gif", 1);
	}

}
