package com.revature.minigame.models;

public class Monster {
	
	protected final String name;
	protected final String image;
	protected final int bonusHealth;
	protected final int bonusDamage;
	
	public Monster(final String name, final String image, final int bonusDamage, final int bonusHealth) {
		this.name = name;
		this.image = image;
		this.bonusHealth = bonusHealth;
		this.bonusDamage = bonusDamage;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getImage() {
		return this.image;
	}

	public int getBonusHealth() {
		return this.bonusHealth;
	}

	public int getBonusDamage() {
		return this.bonusDamage;
	}
	
}
