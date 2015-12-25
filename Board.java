import java.util.ArrayList;
import java.util.HashMap;

public class Board {

	public static final int WIDTH = 7;
	public static final int HEIGHT = 9;
	private static final HashMap<Integer,int[]> DIRECTIONS = new HashMap<Integer,int[]>() 
		{{ put(0, new int[] {1, 0});     
		   put(90, new int[] {0, 1});
		   put(180, new int[] {-1, 0});
		   put(270, new int[] {0, -1}); }};
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
		for (int x = 0; x < oldBoard.grid.length; x++) {
			System.arraycopy(oldBoard.grid[x], 0, grid[x], 0, HEIGHT);
		}
	}
	
	public Box getBox(int x, int y) {
		try {
			return grid[x][y];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
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
				if (boxEast != null && boxCenter.type != boxEast.type && (boxCenter.type > 0 || boxEast.type > 0)) {
					// Add horizontal swaps
					possibleSwaps.add(new SwapPair(boxCenter, boxEast));
				}
				if (boxNorth != null && boxCenter.type != boxNorth.type && (boxCenter.type > 0 || boxNorth.type > 0)) {
					// Add vertical swaps
					possibleSwaps.add(new SwapPair(boxCenter, boxNorth));
				}
			}
		}
		return possibleSwaps;
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
	}

}
