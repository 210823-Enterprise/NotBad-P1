package com.revature.minigame.models;

import java.util.Random;

public class GameBoard {
	
	private static final int SIZE = 6;
	
	private final Area[][] board;
	
	public GameBoard() {
		this.board = new Area[SIZE][SIZE];
		generateMaze(0,SIZE/2,0);
	}
	
	public Area getArea(final Position pos) {
		return this.board[pos.x][pos.y];
	}
	
	public Position getStart() {
		return new Position(0,SIZE/2);
	}
	
	public Position getFinish() {
		return new Position(SIZE-1,SIZE/2);
	}
	
	private void generateMaze(final int x, final int y, final int level) {
		this.board[x][y] = new Area(level);
		final int[] directions = new int[] {1,2,3,4};
		shuffle(directions);
		for(final int i: directions) {
			if(i == 1 && y-1 >= 0 && this.board[x][y-1] == null) {
				generateMaze(x,y-1,level+1);
				this.board[x][y].setDirection(true, EDirection.south);
				this.board[x][y-1].setDirection(true, EDirection.north);
			}
			if(i == 2 && y+1 < SIZE && this.board[x][y+1] == null) {
				generateMaze(x,y+1,level+1);
				this.board[x][y].setDirection(true, EDirection.north);
				this.board[x][y+1].setDirection(true, EDirection.south);
			}
			if(i == 3 && x-1 >= 0 && this.board[x-1][y] == null) {
				generateMaze(x-1,y,level+1);
				this.board[x][y].setDirection(true, EDirection.east);
				this.board[x-1][y].setDirection(true, EDirection.west);
			}
			if(i == 4 && x+1 < SIZE && this.board[x+1][y] == null) {
				generateMaze(x+1,y,level+1);
				this.board[x][y].setDirection(true, EDirection.west);
				this.board[x+1][y].setDirection(true, EDirection.east);
			}
		}
	}
	
	private void shuffle(final int[] array) {
		final Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--)
	    {
	      final int index = random.nextInt(i + 1);
	      final int a = array[index];
	      array[index] = array[i];
	      array[i] = a;
	    }
	}
}
