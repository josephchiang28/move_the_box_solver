import java.util.ArrayList;

public class Board {

	public static final int WIDTH = 7;
	public static final int HEIGHT = 9;
	private int totalBoxes;
	private Box[][] grid;
		   
	public Board() {
		totalBoxes = 0;
		grid = new Box[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y ++) {
				grid[x][y] = new Box(x, y, 0);
			}
		}
	}
	
	public Board(Board oldBoard) {
		totalBoxes = oldBoard.totalBoxes;
		grid = new Box[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				grid[x][y] = new Box(x, y, oldBoard.getBox(x, y).type);
			}
		}
	}
	
	public int getTotalBoxes() {
		return totalBoxes;
	}
	
	public Box getBox(int x, int y) {
		try {
			return grid[x][y];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public void setBoxType(int x, int y, int type) {
		Box box = getBox(x, y);
		if (box.type == type) {
			return;
		} else if (box.isEmpty() && type > 0) {
			totalBoxes++;
		} else if (!box.isEmpty() && type == 0) {
			totalBoxes--;
		}
		box.type = type;
	}
	
	public void swapBoxes(Box box1, Box box2) {
		int box1Type = box1.type;
		setBoxType(box1.x, box1.y, box2.type);
		setBoxType(box2.x, box2.y, box1Type);
	}
	
	public void swapBoxTypes(int x1, int y1, int x2, int y2) {
		int box1Type = getBox(x1, y1).type;
		setBoxType(x1, y1, getBox(x2, y2).type);
		setBoxType(x2, y2, box1Type);
	}
	
	public boolean isComplete() {
		return totalBoxes == 0;
	}
	
	public ArrayList<SwapPair> generateSwaps() {
		ArrayList<SwapPair> possibleSwaps = new ArrayList<SwapPair>();
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				Box boxCenter = getBox(x, y);
				Box boxEast = getBox(x + 1, y);
				Box boxNorth = getBox(x, y + 1);
				if (boxEast != null && boxCenter.type != boxEast.type && !(boxCenter.isEmpty() && boxEast.isEmpty())) {
					// Add horizontal swaps
					possibleSwaps.add(new SwapPair(boxCenter, boxEast));
				}
				if (boxNorth != null && boxCenter.type != boxNorth.type && !(boxCenter.isEmpty() && boxNorth.isEmpty())) {
					// Add vertical swaps
					possibleSwaps.add(new SwapPair(boxCenter, boxNorth));
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
					if (!getBox(x, y).isEmpty()) {
						if (y > bottom) {
							swapBoxTypes(x, bottom, x, y);
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
					Box boxStart = getBox(x, y);
					if (boxStart.isEmpty()) {
						continue;
					}
					int count = 0;
					int newY = y;
					Box boxNext = null;
					do {
						count++;
						boxNext = getBox(x, ++newY);
					} while (boxNext != null && boxStart.type == boxNext.type);
					if (count >= 3) {
						isBoardChanged = true;
						for (int i = y; i < y + count; i++) {
							boardNext.setBoxType(x, i, 0);
						}
					}
					y += count - 1;
				}
			}
			// Find consecutive horizontal boxes to pop
			for (int y = 0; y < HEIGHT; y++) {
				for (int x = 0; x < WIDTH; x++) {
					Box boxStart = getBox(x, y);
					if (boxStart.isEmpty()) {
						continue;
					}
					int count = 0;
					int newX = x;
					Box boxNext = null;
					do {
						count++;
						boxNext = getBox(++newX, y);
					} while (boxNext != null && boxStart.type == boxNext.type);
					if (count >= 3) {
						for (int i = x; i < x + count; i++) {
							isBoardChanged = true;
							boardNext.setBoxType(i, y, 0);
						}
					}
					y += count - 1;
				}
			}
			// Copy Grid
			grid = boardNext.grid;
			totalBoxes = boardNext.totalBoxes;
		}
	}
	
	public String toString() {
		String s = "+-------------+\n";
		for (int i=8; i>=0; i--) {
			s += "|";
			for (int j=0; j<7; j++) {
				s += getBox(j, i).type + "|";
			}
			if (i != 0) {
				s += "\n|-+-+-+-+-+-+-|\n";
			}
		}
		s += "\n+-------------+";
		return s;
	}
	
	public static void main(String[] args) {
		Board b = new Board();
		b.setBoxType(2, 0, 1);
		b.setBoxType(3, 0, 2);
		b.setBoxType(4, 0, 2);
		b.setBoxType(2, 1, 1);
		b.setBoxType(3, 1, 2);
		b.setBoxType(4, 1, 2);
		b.setBoxType(2, 2, 1);
		b.setBoxType(3, 2, 1);
		b.setBoxType(4, 2, 1);
		System.out.println(b.toString());
		System.out.println(b.totalBoxes);
		b.reachSteadyState();
		System.out.println(b.toString());
		System.out.println(b.totalBoxes);
		
		System.out.println("end");
	}

}
