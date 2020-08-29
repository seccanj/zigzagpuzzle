package com.seccanj.zigzagpuzzle;

public class Word {
	public String word;
	public int length;
	
	@Override
	public boolean equals(Object other) {
		
		if (other == null || !(other instanceof Word)) {
			return false;
		}
		
		Word o = (Word)other;
		
		return word.equalsIgnoreCase(o.word);
	}
	
	@Override
	public int hashCode() {
		return word.hashCode();
	}
}
