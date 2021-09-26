package com.revature.minigame.models;

public class Goblin extends Monster {

	public Goblin() {
		super(
			String.format("%s Goblin",randomAttribute()),
			"goblin.gif",
			20,1
		);
	}
	
	@Override
	public int getDamage() {
		return 5;
	}

	

}
