package com.revature.minigame.models.monsters;

public class Zombie extends Monster {

	public Zombie() {
		super(String.format("%s Zombie",randomAttribute()), "zombie.png", 2);
	}

}
