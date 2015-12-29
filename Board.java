import java.util.ArrayList;

public class Board {

	public static final int WIDTH = 7;
	public static final int HEIGHT = 9;
	public static final char BOX_EMPTY = '0';
	public static final char BOX_INVALID = '!';
	private int totalBoxes;
	private char[][] grid;
		   
	public Board() {
		totalBoxes = 0;
		grid = new char[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y ++) {
				grid[x][y] = BOX_EMPTY;
			}
		}
	}
	
	public Board(Board oldBoard) {
		totalBoxes = oldBoard.totalBoxes;
		grid = new char[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				grid[x][y] = oldBoard.getBox(x, y);
			}
		}
	}
	
	public char[][] getGrid() {
		return grid;
	}
	
	public int getTotalBoxes() {
		return totalBoxes;
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
	
	public String getBoardSequence() {
		StringBuffer sequence = new StringBuffer("");
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				sequence.append(getBox(x, y));
			}
		}
		return sequence.toString();
	}
	
	public ArrayList<SwapPair> generateSwaps() {
		ArrayList<SwapPair> possibleSwaps = new ArrayList<SwapPair>();
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
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
	
	public void reachSteadyState() {
		boolean isBoardChanged = true;
		while (isBoardChanged) {
			
			// Drop boxes
			for (int x = 0; x < WIDTH; x++) {
				int bottom = 0;
				for (int y = 0; y < HEIGHT; y++) {
					if (getBox(x, y) != BOX_EMPTY) {
						if (y > bottom) {
							swapBoxes(x, bottom, x, y);
						}
						bottom++;
					}
				}
			}
			
			Board boardNext = new Board(this);
			isBoardChanged = false;
			// TODO: NEED TO ABSTRACT THE FOLLOWING
			// Find consecutive vertical boxes to pop
			for (int x = 0; x < WIDTH; x++) {
				for (int y = 0; y < HEIGHT; y++) {
					char boxStart = getBox(x, y);
					if (boxStart == BOX_EMPTY || boxStart == BOX_INVALID) {
						continue;
					}
					int yNext = y;
					char boxNext;
					do {
						boxNext = getBox(x, ++yNext);
					} while (boxStart == boxNext);
					if (yNext - y >= 3) {
						isBoardChanged = true;
						for (int i = y; i < yNext; i++) {
							boardNext.setBox(x, i, BOX_EMPTY);
						}
					}
					y = yNext - 1;
				}
			}
			// Find consecutive horizontal boxes to pop
			for (int y = 0; y < HEIGHT; y++) {
				for (int x = 0; x < WIDTH; x++) {
					char boxStart = getBox(x, y);
					if (boxStart == BOX_EMPTY || boxStart == BOX_INVALID) {
						continue;
					}
					int xNext = x;
					char boxNext;
					do {
						boxNext = getBox(++xNext, y);
					} while (boxStart == boxNext);
					if (xNext - x >= 3) {
						for (int i = x; i < xNext; i++) {
							isBoardChanged = true;
							boardNext.setBox(i, y, BOX_EMPTY);
						}
					}
					x = xNext - 1;
				}
			}
			// Copy Grid
			grid = boardNext.grid;
			totalBoxes = boardNext.totalBoxes;
		}
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer("+-------------+\n");
		for (int i = HEIGHT - 1; i >= 0; i--) {
			s.append("|");
			for (int j = 0; j < WIDTH; j++) {
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
		Board b = new Board();
		b.setBox(2, 0, '1');
		b.setBox(3, 0, '2');
		b.setBox(4, 0, '2');
		b.setBox(2, 1, '1');
		b.setBox(3, 1, '2');
		b.setBox(4, 1, '2');
		b.setBox(2, 2, '1');
		b.setBox(3, 2, '1');
		b.setBox(4, 2, '1');
		System.out.println(b.toString());
		System.out.println(b.totalBoxes);
		b.reachSteadyState();
		System.out.println(b.toString());
		System.out.println(b.totalBoxes);
		System.out.println("end");
	}

}
