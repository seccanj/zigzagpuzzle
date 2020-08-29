package com.seccanj.zigzagpuzzle;

import java.util.Collections;

public class ZigZagPuzzle {

	public static final int ROWS = 20;
	public static final int COLS = 20;
	
	private ZigZagMatrix game;

	public void initGame() {
		Dictionary dictionary = new Dictionary();
		
		game = new ZigZagMatrix(ROWS, COLS);

		int wordId = 1;
		
		boolean isFull = false;
		while (!isFull) {
			int desiredLen = (int)Math.round(Math.random()*6) + 8;
			isFull = game.placeWord(dictionary, desiredLen, wordId++);
		}
	
		System.out.println("\n\nMinimum word length (greater than one): "+game.getMinLength()+"\n\n");
		
		Collections.sort(game.getWords());
		
		for (String word: game.getWords()) {
			System.out.println(word);
		}

		System.out.println("\n\n");

		System.out.println(game.dump());
	}
	
	
	public static void main(String[] args) {
		
		ZigZagPuzzle puzzle = new ZigZagPuzzle();
		puzzle.initGame();
	}

}
