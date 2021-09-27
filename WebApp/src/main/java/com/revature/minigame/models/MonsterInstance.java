package com.revature.minigame.models;

import java.util.Random;

public class MonsterInstance {

	private final Monster monsterType;
	private final String attribute;
	private final int level;

	protected int health;
	private boolean angry;
	
	public MonsterInstance(final Monster monster, final int level) {
		this.monsterType = monster;
		this.attribute = randomAttribute();
		this.level = level;
		this.health = maxHealth();
		this.angry = false;
	}
	
	protected static final String[] attributes = new String[] {
		"Capricious",	"Deadly",	"Menacing",
		"Vexing",		"Large",	"Giant",
		"Small",		"Tiny",		"Tired",
		"Mean",			"Ugly",		"Pretty",
		"Drunken",		"Sweet",	"Fast",
		"Quick",		"Slow",		"Hungry",
		"Expert",		"Unusual",	"Strange",
		"Enraged",		"Curious",	"Powerful"
	};
	
	protected static String randomAttribute() {
		final Random random = new Random();
		final int index = random.nextInt(attributes.length);
		return attributes[index];
	}
	
	public int maxHealth() {
		return 10 + this.monsterType.getBonusHealth() + (10 * this.level);
	}
	
	public int getDamage() {
		final Random random = new Random();
		return this.monsterType.getBonusDamage() + (5 * this.level) + random.nextInt(4) - 1;
	}
	
	public int damage(final int amount) {
		setAngry(true);
		this.health -= amount;
		return this.health;
	}
	
	public void setAngry(final boolean angry) {
		this.angry = angry;
	}
	
	public String getName() {
		return this.attribute + " " + this.monsterType.getName();
	}
	
	public Monster getType() {
		return this.monsterType;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public boolean getAngry() {
		return this.angry;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public boolean isAlive() {
		return this.health > 0;
	}
	
}
