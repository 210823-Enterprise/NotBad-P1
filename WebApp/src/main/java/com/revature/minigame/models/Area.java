package com.revature.minigame.models;

import java.util.Random;

public class Area {
	
	private static final Monster[] MONSTERS = new Monster[] {
		new Monster("Goblin",		"goblin.gif",		0, 0),
		new Monster("Zombie",		"zombie.png",		0, 0),
		new Monster("Skeleton",		"skeleton.png",		0, 0),
		new Monster("Orc",			"orc.png",			0, 0),
		new Monster("Creeper",		"creeper.png",		40, -40),
		new Monster("Robo-Spider",	"spider.png",		0, 0),
		new Monster("Repear",		"repear.png",		0, 0),
		new Monster("Dwarf",		"dwarf.png",		0, 0),
		new Monster("Ice Dragon",	"ice_dragon.png",	3, 10),
		new Monster("Fire Dragon",	"fire_dragon.png",	6, 20),
		new Monster("Death Dragon",	"death_dragon.png",	9, 30),
		new Monster("Charged Creeper","charged_creeper.png", 80, -80)
	};
	
	private final MonsterInstance monster;
	private Item item;
	
	private boolean north;
	private boolean south;
	private boolean east;
	private boolean west;
	
	public Area(final int level) {
		this.north = false;
		this.south = false;
		this.east = false;
		this.west = false;
		
		if(level == 0) {
			this.monster = null;
		} else {
			final Random random = new Random();
			this.monster = new MonsterInstance(MONSTERS[ random.nextInt(Math.min(level, MONSTERS.length)) ], level);
		}
	}
	
	public void setDirection(final boolean state, final EDirection direction) {
		switch(direction) {
		case north:
			this.north = state;
			break;
		case south:
			this.south = state;
			break;
		case east:
			this.east = state;
			break;
		case west:
			this.west = state;
			break;
		}
	}
	
	public boolean getDirection(final EDirection direction) {
		switch(direction) {
		case north:
			return this.north;
		case south:
			return this.south;
		case east:
			return this.east;
		case west:
			return this.west;
		}
		return false;
	}
	
	public String[] textDirection() {
		final String[] out = new String[] {"   "," + ","   "};
		if(this.north)
			out[0] = " | ";	
		if(this.south)
			out[2] = " | ";	
		if(this.east)
			out[1] = '-' + out[1].substring(1);
		if(this.west)
			out[1] = out[1].substring(0,2) + '-';
		return out;
	}
	
	@Override
	public String toString() {
		String direction = "";
		if(this.north) direction += 'n';
		if(this.south) direction += 's';
		if(this.east) direction += 'e';
		if(this.west) direction += 'w';
		return String.format("[monster=%s,item=%s,directions=%s]", this.monster, this.item, direction);
	}

	public MonsterInstance getMonster() {
		return this.monster;
	}

	public boolean isSafe() {
		return this.monster == null || !this.monster.isAlive();
	}
}
