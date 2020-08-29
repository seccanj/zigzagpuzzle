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

	private static final int MAX_TRIES = 30;
	
	public  ZigZagCell[][] cells;
	
	private List<Word> words = new ArrayList<>();
	
	private int minLength = 1000;
	private int numBlanks = 0;

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
		
		List<MatrixPosition> path = findLongestPath(desiredLen);
			
		if (path != null && path.size() > 0) {
			int actualLength = path.size();
			if (actualLength > 1 && actualLength < minLength) {
				minLength = actualLength;
			}
			
			String word = null;
			if (actualLength > 1) {
				word = dictionary.findRandomWord(actualLength);
				
				Word w = new Word();
				w.word = word;
				w.id = wordId;
				
				words.add(w);
				
			} else {
				word = "#";
				numBlanks++;
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

				setPathIsFree(path);
			}
		} else {
			System.out.println("Could not find a free start position");
			isFull = true;
		}
		
		return isFull;
	}

	private List<MatrixPosition> findLongestPath(int desiredLen) {
		List<MatrixPosition> result = null;

		int tries = 0;
		for (int l=0; l<= desiredLen && tries < MAX_TRIES; l++, tries++) {
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
						cells[currPosition.getRow()][currPosition.getCol()].setReserved(true);
						
						actualLength++;
					} else {
						break;
					}
				}
				
				System.out.println("Found a path of length "+actualLength+" starting from position ("+startPosition.getRow()+", "+startPosition.getCol()+")");
				
				if (result == null || path.size() > result.size()) {
					result = path;
				}
				
				setPathIsFree(path);
				
				if (path.size() >= desiredLen) {
					break;
				}
			} else {
				break;
			}
		}
		
		return result;
	}

	private void setPathIsFree(List<MatrixPosition> path) {
		for (int l=0; l<path.size(); l++) {
			MatrixPosition pos = path.get(l);
			
			cells[pos.getRow()][pos.getCol()].setReserved(false);;
		}
	}

	private MatrixPosition getNextCell(MatrixPosition startPosition, Direction d) {
		MatrixPosition p = new MatrixPosition(startPosition.getRow() + d.dy, startPosition.getCol() + d.dx);
		return isValid(p) && isFree(p) ? p : null;
	}

	private boolean isValid(MatrixPosition p) {
		return p.getRow() >= 0 && p.getRow() < cells.length && p.getCol() >= 0 && p.getCol() < cells[p.getRow()].length;
	}

	private boolean isFree(MatrixPosition p) {
		return isFree(p.getRow(), p.getCol());
	}

	private boolean isFree(int row, int col) {
		return cells[row][col].getLetter() == null && !cells[row][col].isReserved();
	}

	private MatrixPosition findFreePosition() {
		List<MatrixPosition> freePositions = new ArrayList<>();
		
		boolean found = false;
		for (int i=0; i<cells.length; i++) {
			ZigZagCell[] row = cells[i];
			for (int j=0; j<row.length; j++) {
				if (isFree(i, j)) {
					freePositions.add(new MatrixPosition(i, j));
				}
			}
		}
		
		return freePositions.isEmpty() ? null : freePositions.get((int)Math.floor(Math.random()*freePositions.size()));
	}
	
	public List<Word> getWords() {
		return words;
	}

	public int getMinLength() {
		return minLength;
	}
	
	public String dump(boolean dumpPaths) {
		StringBuilder s = new StringBuilder();
		
		s.append("=");
		for (int j=0; j<cells[0].length; j++) {
			s.append("===");
		}
		s.append("==\n");
		
		s.append("[");
		for (int j=0; j<cells[0].length; j++) {
			s.append("   ");
		}
		s.append(" ]\n");

		for (int i=0; i<cells.length; i++) {
			ZigZagCell[] row = cells[i];

			s.append("[");

			for (int j=0; j<row.length; j++) {
				if (dumpPaths) {
					s.append(" "+Utils.padWitZeros(row[j].getWordId(), 2));
				} else {
					s.append(" "+row[j].getLetter().toUpperCase()+" ");
				}
			}
			
			s.append(" ]\n");

			s.append("[");
			for (int j=0; j<cells[0].length; j++) {
				s.append("   ");
			}
			s.append(" ]\n");
		}

		s.append("=");
		for (int j=0; j<cells[0].length; j++) {
			s.append("===");
		}
		s.append("==\n\n");
		
		
		return s.toString();
	}

	public int getNumBlanks() {
		return numBlanks;
	}
}
