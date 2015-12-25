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
	private int[][] grid;
		   
	public Board() {
		totalBoxes = 0;
		grid = new int[WIDTH][HEIGHT];
	}
	
	public Board(Board oldBoard) {
		totalBoxes = oldBoard.totalBoxes;
		grid = new int[WIDTH][HEIGHT];
		for (int x = 0; x < oldBoard.grid.length; x++) {
			System.arraycopy(oldBoard.grid[x], 0, grid[x], 0, HEIGHT);
		}
	}
	
	public int getBox(int x, int y) {
		return grid[x][y];
	}
	
	public boolean isComplete() {
		return totalBoxes == 0;
	}
	
	public String toString() {
		String s = "+-------------+\n";
		for (int i=8; i>=0; i--) {
			s += "|";
			for (int j=0; j<7; j++) {
				s += getBox(j, i) + "|";
			}
			if (i != 0) {
				s += "\n|-+-+-+-+-+-+-|\n";
			}
		}
		s += "\n+-------------+";
		return s;
	}
	
	public static void main(String[] args) {
		Board a = new Board();
		a.grid[0][1] = 3;
		a.grid[5][5] = 2;
		System.out.println(a.toString());
		Board b = new Board(a);
		System.out.println(b.toString());
	}

}
