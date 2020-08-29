package com.seccanj.zigzagpuzzle;

import java.util.ArrayList;
import java.util.List;

public class ZigZagMatrix {

	public enum Direction {
		
		RIGHT(1, 0), 
		DOWN(0, 1), 
		LEFT(0, -1), 
		UP(-1, 0);
		
		private int dx, dy;
		
		public static List<Direction> directions = new ArrayList<>();
		
		static {
			directions.add(RIGHT);
			directions.add(DOWN);
			directions.add(LEFT);
			directions.add(UP);
		}
		
		private Direction(int dx, int dy) {	
			this.dx = dx;
			this.dy = dy;
		}
	}
	
	public  ZigZagCell[][] cells;
	
	private List<String> words = new ArrayList<>();
	
	private int minLength = 1000;

	public ZigZagMatrix(int numRows, int numCols) {
		cells = new ZigZagCell[numRows][];
		
		for (int i=0; i<numRows; i++) {
			cells[i] = new ZigZagCell[numCols];
			for (int j=0; j<numCols; j++) {
				cells[i][j] = new ZigZagCell();				
			}
		}
	}

	public boolean placeWord(Dictionary dictionary, int desiredLen, int wordId) {
		boolean isFull = false;

		System.out.println("Placing word "+wordId+" of desired length "+desiredLen);
		
		MatrixPosition startPosition = findFreePosition();
		
		if (startPosition != null) {
			List<MatrixPosition> path = new ArrayList<>();
			path.add(startPosition);
			
			MatrixPosition currPosition = startPosition;
			
			int actualLength = 1;
			while (actualLength < desiredLen) {
				List<MatrixPosition> freeNextPositions = new ArrayList<>();
				for (Direction d: Direction.directions) {
					MatrixPosition p = getNextCell(currPosition, d);
					
					if (p != null) {
						freeNextPositions.add(p);
					}
				}
				
				if (!freeNextPositions.isEmpty()) {
					currPosition = freeNextPositions.get((int)Math.floor(Math.random() * freeNextPositions.size()));
					path.add(currPosition);
					
					actualLength++;
				} else {
					break;
				}
			}
			
			System.out.println("Found a path of length "+actualLength+" starting from position ("+startPosition.getRow()+", "+startPosition.getCol()+")");
			
			if (actualLength > 1 && actualLength < minLength) {
				minLength = actualLength;
			}
			
			String word = null;
			if (actualLength > 1) {
				word = dictionary.findRandomWord(actualLength);
				words.add(word);
			} else {
				word = "#";
			}
			
			if (word != null) {
				MatrixPosition prevPos = null;
				for (int l=0; l<path.size(); l++) {
					MatrixPosition pos = path.get(l);
					
					ZigZagCell cell = cells[pos.getRow()][pos.getCol()];
					cell.setPosition(pos);
					cell.setLetter(""+word.charAt(l));
					cell.setWord(word);
					cell.setPositionInWord(l);
					cell.setWordId(wordId);
					
					if (l > 0) {
						cell.setPreviousLetterPosition(prevPos);
					}
					
					if (l < path.size()-1) {
						cell.setNextLetterPosition(path.get(l+1));
					}
					
					prevPos = pos;
				}

			} else {
				System.out.println("Could not find a word of length "+actualLength);
			}
		} else {
			System.out.println("Could not find a free start position");
			isFull = true;
		}
		
		return isFull;
	}

	private MatrixPosition getNextCell(MatrixPosition startPosition, Direction d) {
		MatrixPosition p = new MatrixPosition(startPosition.getRow() + d.dy, startPosition.getCol() + d.dx);
		return isValid(p) && isFree(p) ? p : null;
	}

	private boolean isValid(MatrixPosition p) {
		return p.getRow() >= 0 && p.getRow() < cells.length && p.getCol() >= 0 && p.getCol() < cells[p.getRow()].length;
	}

	private boolean isFree(MatrixPosition p) {
		return cells[p.getRow()][p.getCol()].getLetter() == null;
	}

	private MatrixPosition findFreePosition() {
		MatrixPosition position = null;
		
		boolean found = false;
		for (int i=0; i<cells.length && !found; i++) {
			ZigZagCell[] row = cells[i];
			for (int j=0; j<row.length && !found; j++) {
				if (cells[i][j].getWord() == null) {
					position = new MatrixPosition(i, j);
					found = true;
				}
			}
		}
		
		return position;
	}
	
	public List<String> getWords() {
		return words;
	}

	public int getMinLength() {
		return minLength;
	}
	
	public String dump() {
		StringBuilder s = new StringBuilder();
		
		s.append("=");
		for (int j=0; j<cells[0].length; j++) {
			s.append("===");
		}
		s.append("=\n");
		
		for (int i=0; i<cells.length; i++) {
			ZigZagCell[] row = cells[i];

			s.append("[");

			for (int j=0; j<row.length; j++) {
				s.append(" "+row[j].getLetter()+" ");
			}
			
			s.append("]\n");
		}

		s.append("=");
		for (int j=0; j<cells[0].length; j++) {
			s.append("===");
		}
		s.append("=");
		
		
		return s.toString();
	}

}
