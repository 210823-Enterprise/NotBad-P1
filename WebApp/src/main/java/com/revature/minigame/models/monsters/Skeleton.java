package com.revature.minigame.models.monsters;

public class Skeleton extends Monster {
	
	public Skeleton() {
		super(String.format("%s Skeleton",randomAttribute()),"skeleton.png",3);
	}
	
}
