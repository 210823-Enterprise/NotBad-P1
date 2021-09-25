package com.revature.models;

import java.util.Objects;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;

@Entity(tableName="character_statistics")
public class CharacterStats {

	@Column(columnName="characterLevel", nullable=false, unique = false)
	private int characterLevel;
	
	@Column(columnName="health", nullable=false, unique = false)
	private int health;
	
	@Column(columnName="mana", nullable=false, unique = false)
	private int mana;
	
	@Column(columnName="stamina", nullable=false, unique = false)
	private int stamina;
	
	@Column(columnName="speed", nullable=false, unique = false)
	private int speed;

	public CharacterStats() {
		super();
	}

	public CharacterStats(int characterLevel, int health, int mana, int stamina, int speed) {
		super();
		this.characterLevel = characterLevel;
		this.health = health;
		this.mana = mana;
		this.stamina = stamina;
		this.speed = speed;
	}

	public int getCharacterLevel() {
		return characterLevel;
	}

	public void setCharacterLevel(int characterLevel) {
		this.characterLevel = characterLevel;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(characterLevel, health, mana, speed, stamina);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharacterStats other = (CharacterStats) obj;
		return characterLevel == other.characterLevel && health == other.health && mana == other.mana
				&& speed == other.speed && stamina == other.stamina;
	}

	@Override
	public String toString() {
		return "CharacterStats [characterLevel=" + characterLevel + ", health=" + health + ", mana=" + mana
				+ ", stamina=" + stamina + ", speed=" + speed + "]";
	}	
	
}
