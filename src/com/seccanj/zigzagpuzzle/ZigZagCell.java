package com.seccanj.zigzagpuzzle;

public class ZigZagCell {

	private MatrixPosition position;
	
	private String letter;
	
	private boolean reserved;
	
	private String word;
	
	private int positionInWord;
	
	private MatrixPosition previousLetterPosition, nextLetterPosition;
	
	private int wordId;

	public MatrixPosition getPosition() {
		return position;
	}

	public void setPosition(MatrixPosition position) {
		this.position = position;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getPositionInWord() {
		return positionInWord;
	}

	public void setPositionInWord(int positionInWord) {
		this.positionInWord = positionInWord;
	}

	public MatrixPosition getPreviousLetterPosition() {
		return previousLetterPosition;
	}

	public void setPreviousLetterPosition(MatrixPosition previousLetterPosition) {
		this.previousLetterPosition = previousLetterPosition;
	}

	public MatrixPosition getNextLetterPosition() {
		return nextLetterPosition;
	}

	public void setNextLetterPosition(MatrixPosition nextLetterPosition) {
		this.nextLetterPosition = nextLetterPosition;
	}

	public int getWordId() {
		return wordId;
	}

	public void setWordId(int wordId) {
		this.wordId = wordId;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	
}
