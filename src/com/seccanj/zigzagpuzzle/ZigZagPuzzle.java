package com.seccanj.zigzagpuzzle;

import java.util.Collections;
import java.util.Comparator;

public class ZigZagPuzzle {

	public static final int ROWS = 20;
	public static final int COLS = 25;
	
	private ZigZagMatrix game;

	public void runGame() {
		Dictionary dictionary = new Dictionary();
		
		game = new ZigZagMatrix(ROWS, COLS);

		int wordId = 1;
		
		boolean isFull = false;
		while (!isFull) {
			int desiredLen = (int)Math.round(Math.random()*6) + 8;
			isFull = game.placeWord(dictionary, desiredLen, wordId++);
		}
	
		Collections.sort(game.getWords(), new Comparator<Word>() {

			@Override
			public int compare(Word w1, Word w2) {
				return w1.word.compareTo(w2.word);
			}
			
		});
		
		System.out.println("\n\n");
		System.out.println("Words to be found:\n");
		for (Word word: game.getWords()) {
			System.out.println(Utils.padWitZeros(word.id, 3) + ": "+word.word);
		}

		System.out.println("\n\n");

		System.out.println("Game schema:\n");
		System.out.println(game.dump(false));

		System.out.println("Solution (word IDs):\n");
		System.out.println(game.dump(true));

		System.out.println("\n\n");
		System.out.println("Number of words: "+game.getWords().size()+"\n");
		System.out.println("Minimum word length (greater than one): "+game.getMinLength()+"\n");
		System.out.println("Number of blank cells: "+game.getNumBlanks()+"\n\n");
	}
	
	public static void main(String[] args) {
		
		ZigZagPuzzle puzzle = new ZigZagPuzzle();
		puzzle.runGame();
	}

}
