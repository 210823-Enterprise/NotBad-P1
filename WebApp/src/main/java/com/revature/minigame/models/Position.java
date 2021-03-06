package com.revature.minigame.models;

import java.util.Objects;

public final class Position {
	
	final int x;
	final int y;
	
	public Position(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position transform(final int x, final int y) {
		return new Position(this.x+x, this.y+y);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.x, this.y);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Position other = (Position) obj;
		return this.x == other.x && this.y == other.y;
	}

	@Override
	public String toString() {
		return "Position (" + this.x + ", " + this.y + ")";
	}
	
}
