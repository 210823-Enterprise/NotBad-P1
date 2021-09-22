package com.revature.models;

import java.util.Objects;

public class Character {

	private String name;
	private String gender;
	private String race;
	private String clazz;
	private String specialAbility;
	
	public Character() {
		super();
	}

	public Character(String name, String gender, String race, String clazz, String specialAbility) {
		super();
		this.name = name;
		this.gender = gender;
		this.race = race;
		this.clazz = clazz;
		this.specialAbility = specialAbility;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getSpecialAbility() {
		return specialAbility;
	}

	public void setSpecialAbility(String specialAbility) {
		this.specialAbility = specialAbility;
	}

	@Override
	public String toString() {
		return "Character [name=" + name + ", gender=" + gender + ", race=" + race + ", clazz=" + clazz
				+ ", specialAbility=" + specialAbility + ", getName()=" + getName() + ", getGender()=" + getGender()
				+ ", getRace()=" + getRace() + ", getClazz()=" + getClazz() + ", getSpecialAbility()="
				+ getSpecialAbility() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(clazz, gender, name, race, specialAbility);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Character other = (Character) obj;
		return Objects.equals(clazz, other.clazz) && Objects.equals(gender, other.gender)
				&& Objects.equals(name, other.name) && Objects.equals(race, other.race)
				&& Objects.equals(specialAbility, other.specialAbility);
	}
	
}
