import java.util.ArrayList;

public class Board {

	public static final char BOX_EMPTY = '0';
	public static final char BOX_INVALID = '!';
	private int width;
	private int height;
	private int totalBoxes;
	private char[][] grid;
		   
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.totalBoxes = 0;
		grid = new char[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y ++) {
				// Initialize grid empty
				grid[x][y] = BOX_EMPTY;
			}
		}
	}
	
	public Board(Board oldBoard) {
		width = oldBoard.width;
		height = oldBoard.height;
		totalBoxes = oldBoard.totalBoxes;
		grid = new char[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				// Copy old grid to this grid
				grid[x][y] = oldBoard.getBox(x, y);
			}
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getTotalBoxes() {
		return totalBoxes;
	}
	
	public char[][] getGrid() {
		return grid;
	}
	
	public char getBox(int x, int y) {
		try {
			return grid[x][y];
		} catch (IndexOutOfBoundsException e) {
			return BOX_INVALID;
		}
	}
	
	public void setBox(int x, int y, char type) {
		char box = getBox(x, y);
		if (box == type || box == BOX_INVALID || type == BOX_INVALID) {
			return;
		} else if (box == BOX_EMPTY && type != BOX_EMPTY) {
			totalBoxes++;
		} else if (box != BOX_EMPTY && type == BOX_EMPTY) {
			totalBoxes--;
		}
		grid[x][y] = type;
	}
	
	public void swapBoxes(int x1, int y1, int x2, int y2) {
		char box1Type = getBox(x1, y1);
		setBox(x1, y1, getBox(x2, y2));
		setBox(x2, y2, box1Type);
	}
	
	public boolean isComplete() {
		return totalBoxes == 0;
	}
	
	// Returns current board/grid's unique sequence
	public String getBoardSequence() {
		StringBuffer sequence = new StringBuffer();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				sequence.append(getBox(x, y));
			}
		}
		return sequence.toString();
	}
	
	// Drop boxes if necessary to ensure no vertical gaps between boxes
	public boolean gravitateBoxes() {
		boolean isBoardChanged = false;
		int bottom;
		for (int x = 0; x < width; x++) {
			bottom = 0;
			for (int y = 0; y < height; y++) {
				if (getBox(x, y) != BOX_EMPTY) {
					if (y > bottom) {
						swapBoxes(x, bottom, x, y);
						isBoardChanged = true;
					}
					bottom++;
				}
			}
		}
		return isBoardChanged;
	}
	
	// Generates all possible swaps/moves for current board
	public ArrayList<SwapPair> generateSwaps() {
		ArrayList<SwapPair> possibleSwaps = new ArrayList<SwapPair>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				char boxCenter = getBox(x, y);
				char boxEast = getBox(x + 1, y);
				char boxNorth = getBox(x, y + 1);
				if (boxEast != BOX_INVALID && boxCenter != boxEast) {
					// Add horizontal swaps
					possibleSwaps.add(new SwapPair(x, y, x + 1, y));
				}
				if (boxNorth != BOX_INVALID && boxCenter != boxNorth) {
					// Add vertical swaps
					possibleSwaps.add(new SwapPair(x, y, x, y + 1));
				}
			}
		}
		return possibleSwaps;
	}
	
	// Cancels 3 or more consecutive identical boxes until board is steady
	public boolean reachSteadyState() {
		// Need to gravitate boxes first to make sure board is in valid state
		boolean isBoardChanged = gravitateBoxes();
		boolean areBoxesCanceled = true;
		char boxStart, boxNext;
		int xNext, yNext;
		Board boardNext;
		while (areBoxesCanceled && !isComplete()) {
			boardNext = new Board(this);
			areBoxesCanceled = false;
			// Find consecutive identical boxes to pop vertically
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					boxStart = getBox(x, y);
					if (boxStart == BOX_EMPTY || boxStart == BOX_INVALID) {
						break;
					}
					yNext = y;
					do {
						boxNext = getBox(x, ++yNext);
					} while (boxStart == boxNext);
					if (yNext - y >= 3) {
						isBoardChanged = true;
						areBoxesCanceled = true;
						for (int i = y; i < yNext; i++) {
							boardNext.setBox(x, i, BOX_EMPTY);
						}
					}
					// Skip to the box after consecutive identical boxes
					y = yNext - 1;
				}
			}
			// Find consecutive identical boxes to pop horizontally
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					boxStart = getBox(x, y);
					if (boxStart == BOX_EMPTY || boxStart == BOX_INVALID) {
						continue;
					}
					xNext = x;
					do {
						boxNext = getBox(++xNext, y);
					} while (boxStart == boxNext);
					if (xNext - x >= 3) {
						isBoardChanged = true;
						areBoxesCanceled = true;
						for (int i = x; i < xNext; i++) {
							boardNext.setBox(i, y, BOX_EMPTY);
						}
					}
					// Skip to the box after consecutive identical boxes
					x = xNext - 1;
				}
			}
			
			if (areBoxesCanceled) {
				grid = boardNext.grid;
				totalBoxes = boardNext.totalBoxes;
				gravitateBoxes();
			}
		}
		return isBoardChanged;
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer("+-------------+\n");
		for (int i = height - 1; i >= 0; i--) {
			s.append("|");
			for (int j = 0; j < width; j++) {
				s.append(getBox(j, i) + "|");
			}
			if (i != 0) {
				s.append("\n|-+-+-+-+-+-+-|\n");
			}
		}
		s.append("\n+-------------+");
		return s.toString();
	}
	
	public static void main(String[] args) {
		Board b = new Board(7, 9);
		b.setBox(2, 0, '1');
		b.setBox(3, 0, '2');
		b.setBox(4, 0, '2');
		b.setBox(2, 1, '1');
		b.setBox(3, 1, '2');
		b.setBox(4, 1, '2');
		b.setBox(2, 2, '1');
		b.setBox(3, 2, '1');
		b.setBox(4, 2, '1');
		b.setBox(2, 3, '3');
		b.setBox(3, 3, '1');
		b.setBox(4, 3, '3');
		System.out.println(b.toString());
		System.out.println(b.totalBoxes);
		b.reachSteadyState();
		System.out.println(b.toString());
		System.out.println(b.totalBoxes);
		System.out.println("end");
	}

}
