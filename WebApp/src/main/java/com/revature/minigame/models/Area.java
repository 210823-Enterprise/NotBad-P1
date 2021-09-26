package com.revature.minigame.models;

public class Area {
	
	private Monster monster;
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
		
		switch(level) {
		case 0:
			this.monster = null;
			break;
		default:
			this.monster = new Goblin();
			break;
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

	public Monster getMonster() {
		return this.monster;
	}

	public boolean isSafe() {
		return this.monster == null || !this.monster.isAlive();
	}
}
