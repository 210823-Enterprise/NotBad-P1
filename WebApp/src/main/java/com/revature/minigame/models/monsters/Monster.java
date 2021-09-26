package com.revature.minigame.models.monsters;

import java.util.Random;

public abstract class Monster {
	
	private boolean angry;
	protected int health;
	protected final String name;
	protected final String image;
	protected final int maxHealth;
	protected final int level;
	protected final int minDamage;
	protected final int maxDamage;
	public Monster(final String name, final String image, final int level) {
		this.angry = false;
		this.name = name;
		this.image = image;
		this.level = level;
		this.maxHealth = 10 + level * 10;
		this.health = this.maxHealth;
		this.minDamage = level * 4;
		this.maxDamage = level * 6;
	}
	
	public Monster(final String name, final String image, final int level, final int maxHealth, final int minDamage, final int maxDamage) {
		this.angry = false;
		this.name = name;
		this.image = image;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.level = level;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
	}
	
	public int damage(final int amount) {
		setAngry(true);
		this.health -= amount;
		return this.health;
	}
	
	protected static final String[] attributes = new String[] {
		"Deadly",
		"Menacing",
		"Vexing",
		"Large",
		"Giant",
		"Small",
		"Tiny"
	};
	
	protected static String randomAttribute() {
		final Random random = new Random();
		final int index = random.nextInt(attributes.length);
		return attributes[index];
	}
	
	public int getDamage() {
		final Random random = new Random();
		return this.minDamage + random.nextInt(this.maxDamage - this.minDamage + 1);
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public String getImage() {
		return this.image;
	}
	
	public boolean getAngry() {
		return this.angry;
	}
	
	public boolean isAlive() {
		return this.health > 0;
	}

	public int getLevel() {
		return this.level;
	}
	
	public void setAngry(final boolean angry) {
		this.angry = angry;
	}
	
}
