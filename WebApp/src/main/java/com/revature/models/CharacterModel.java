package com.revature.models;

import java.util.Objects;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;

@Entity(tableName="character_table")
public class CharacterModel {
	
	@Id(autoGenerated = true, columnName = "character_id")
	private int id;
	
	@Column(columnName="username", nullable=false, unique = true)
	private String username;
	
	@Column(columnName="password", nullable=false, unique = false)
	private String password;
	
	@Column(columnName="gender", nullable=false, unique = false)
	private String gender;
	
	@Column(columnName="race", nullable=false, unique = false)
	private String race;
	
	@Column(columnName="class", nullable=false, unique = false)
	private String clazz;

	@Column(columnName="specialAbility", nullable=false, unique = false)
	private String specialAbility;

	@Column(columnName="game_data", nullable=false, unique = false)
	private CharacterStats gameData;
	
	public CharacterModel() {
		super();
	}

	public CharacterModel(final String username, final String password, final String gender, final String race, final String clazz, final String specialAbility, final CharacterStats gameData) {
		super();
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.race = race;
		this.clazz = clazz;
		this.specialAbility = specialAbility;
		this.gameData = gameData;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public boolean equalsPassword(final String password) {
		return this.password.equals(password);
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(final String gender) {
		this.gender = gender;
	}

	public String getRace() {
		return this.race;
	}

	public void setRace(final String race) {
		this.race = race;
	}

	public String getClazz() {
		return this.clazz;
	}

	public void setClazz(final String clazz) {
		this.clazz = clazz;
	}

	public String getSpecialAbility() {
		return this.specialAbility;
	}

	public void setSpecialAbility(final String specialAbility) {
		this.specialAbility = specialAbility;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.clazz, this.gender, this.id, this.password, this.race, this.specialAbility, this.username);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CharacterModel other = (CharacterModel) obj;
		return Objects.equals(this.clazz, other.clazz) && Objects.equals(this.gender, other.gender) && this.id == other.id
				&& Objects.equals(this.password, other.password) && Objects.equals(this.race, other.race)
				&& Objects.equals(this.specialAbility, other.specialAbility) && Objects.equals(this.username, other.username);
	}

	@Override
	public String toString() {
		return "[Character Name = " + this.username + "| gender= " + this.gender
				+ "| race= " + this.race + "| class= " + this.clazz + "| Special Ability= " + this.specialAbility + "]";
	}

	

}
