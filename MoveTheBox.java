import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

public class MoveTheBox {

	public int movesRequired;
	private HashMap<String, Integer> unsolvableBoards; // Key: unique board sequences, Value: moves left
	
	public MoveTheBox(int movesRequired) {
		this.movesRequired = movesRequired;
		unsolvableBoards = new HashMap<String, Integer>();
	}
	
	public ArrayList<SwapPair[]> solve(Board board, int movesLeft) {	
		String boardSequence = board.getBoardSequence();
		if (movesLeft <= 0 || board.getTotalBoxes() < 3
				|| (unsolvableBoards.containsKey(boardSequence) && movesLeft <= unsolvableBoards.get(boardSequence))) {
			// Returns empty ArrayList if board unsolvable
			return new ArrayList<SwapPair[]>();
		}
		ArrayList<SwapPair[]> curMoves = new ArrayList<SwapPair[]>();
		ArrayList<SwapPair[]> nextMoves;
		ArrayList<SwapPair> possibleSwaps = board.generateSwaps();
		int moveIndex = movesRequired - movesLeft;
		for (SwapPair swapPair: possibleSwaps) {
			Board boardNext = new Board(board);
			boardNext.swapBoxes(swapPair.x1, swapPair.y1, swapPair.x2, swapPair.y2);
			boardNext.reachSteadyState();
			if (boardNext.isComplete()) {
				SwapPair[] moves = new SwapPair[movesRequired];
				moves[moveIndex] = swapPair;
				curMoves.add(moves);
				return curMoves;
			}
			nextMoves = solve(boardNext, movesLeft - 1);
			if (!nextMoves.isEmpty()) {
				// Board is successfully solved in subsequent moves, add and store this swap
				for (SwapPair[] moves: nextMoves) {
					moves[moveIndex] = swapPair;
				}
				curMoves.addAll(nextMoves);
//				return curMoves; // Return here to generate first solution found (faster) instead of all possible
			}
		}
		if (curMoves.isEmpty() 
				&& (!unsolvableBoards.containsKey(boardSequence) || movesLeft > unsolvableBoards.get(boardSequence))) {
			unsolvableBoards.put(boardSequence, movesLeft);
		}
		return curMoves;
	}
	
	public static String getSolutionString(ArrayList<SwapPair[]> solution) {
		if (solution.isEmpty()) {
			return "[]";
		}
		StringBuffer sb = new StringBuffer("[");
		for (int i = 0; i < solution.size() - 1; i++) {
			sb.append(Arrays.toString(solution.get(i)));
			sb.append(", ");
		}
		sb.append(Arrays.toString(solution.get(solution.size() - 1)));
		sb.append("]");
		return sb.toString();
	}
	
	public static boolean verifyDistinctMoves(ArrayList<SwapPair[]> solution) {
		if (solution.isEmpty()) {
			System.out.println("Error: Empty Solution");
			return false;
		}
		boolean areDistinct = true;
		TreeSet<String> distinctMoves = new TreeSet<String>();
		for (SwapPair[] moves: solution) {
			String movesString = Arrays.toString(moves);
			if (distinctMoves.contains(movesString)) {
				areDistinct = false;
				break;
			}
			distinctMoves.add(movesString);
		}
//		System.out.println(distinctMoves); // Prints it in sorted order
//		System.out.println(distinctMoves.size());
		return areDistinct;
	}
	
	public static boolean verifySolution(Board board, ArrayList<SwapPair[]> solution) {
		if (solution.isEmpty()) {
			System.out.println("Error: Empty Solution");
			return false;
		}
		boolean allPass = true;
		for (SwapPair[] moves: solution) {
			Board boardTest = new Board(board);
			for (SwapPair swapPair: moves) {
				if (board.isComplete()) {
					allPass = false;
					System.out.print("Board completed with redundant moves: ");
					System.out.println(moves);
				}
				boardTest.swapBoxes(swapPair.x1, swapPair.y1, swapPair.x2, swapPair.y2);
				boardTest.reachSteadyState();
			}
			if (!boardTest.isComplete()) {
				allPass = false;
				System.out.print("Board not completed with moves: ");
				System.out.println(moves);
			}
		}
		return allPass;
	}
	
	public static void main(String[] args) {
		Board board = new Board(7, 9);
		long startTime, endTime;
		ArrayList<SwapPair[]> solution;
		
		// Simple test, moves = 1
//		MoveTheBox mtb = new MoveTheBox(1);
//		board.setBox(0, 0, '1');
//		board.setBox(1, 0, '2');
//		board.setBox(2, 0, '1');
//		board.setBox(0, 1, '2');
//		board.setBox(1, 1, '1');
//		board.setBox(2, 1, '2');
		// End simple test

		// Hamburg lvl 3, moves = 1
//		MoveTheBox mtb = new MoveTheBox(1);
//		board.setBox(2, 0, '1');
//		board.setBox(2, 1, '2');
//		board.setBox(2, 2, '1');
//		board.setBox(3, 0, '1');
//		board.setBox(3, 1, '2');
//		board.setBox(3, 2, '1');
//		board.setBox(4, 0, '3');
//		board.setBox(4, 1, '1');
//		board.setBox(4, 2, '3');
//		board.setBox(4, 3, '3');
//		board.setBox(4, 4, '1');
//		board.setBox(4, 5, '2');
		// End Hamburg lvl 3

		// Hamburg lvl 6, moves = 2
//		MoveTheBox mtb = new MoveTheBox(2);
//		board.setBox(2, 0, '1');
//		board.setBox(3, 0, '2');
//		board.setBox(3, 1, '3');
//		board.setBox(3, 2, '1');
//		board.setBox(3, 3, '2');
//		board.setBox(4, 0, '2');
//		board.setBox(4, 1, '3');
//		board.setBox(4, 2, '1');
//		board.setBox(4, 3, '4');
//		board.setBox(5, 0, '3');
//		board.setBox(5, 1, '4');
//		board.setBox(6, 0, '4');
		// End Hamburg lvl 6

		// Hamburg lvl 17, moves = 3
//		MoveTheBox mtb = new MoveTheBox(3);
//		board.setBox(0, 0, '1');
//		board.setBox(1, 0, '1');
//		board.setBox(1, 1, '2');
//		board.setBox(2, 0, '3');
//		board.setBox(2, 1, '2');
//		board.setBox(2, 2, '1');
//		board.setBox(2, 3, '3');
//		board.setBox(2, 4, '2');
//		board.setBox(3, 0, '1');
//		board.setBox(3, 1, '3');
//		board.setBox(3, 2, '4');
//		board.setBox(3, 3, '1');
//		board.setBox(3, 4, '2');
//		board.setBox(4, 0, '4');
//		board.setBox(4, 1, '1');
//		board.setBox(4, 2, '2');
//		board.setBox(5, 0, '4');
//		board.setBox(5, 1, '2');
//		board.setBox(5, 2, '1');
		// End Hamburg lvl 17
		 
		// Hamburg lvl 24, moves = 4
//		MoveTheBox mtb = new MoveTheBox(4);
//		board.setBox(2, 0, '1');
//		board.setBox(2, 1, '1');
//		board.setBox(2, 2, '2');
//		board.setBox(2, 3, '3');
//		board.setBox(3, 0, '3');
//		board.setBox(3, 1, '1');
//		board.setBox(3, 2, '2');
//		board.setBox(3, 3, '1');
//		board.setBox(3, 4, '3');
//		board.setBox(3, 5, '2');
//		board.setBox(4, 0, '3');
//		board.setBox(4, 1, '4');
//		board.setBox(4, 2, '1');
//		board.setBox(5, 0, '4');
//		board.setBox(5, 1, '1');
//		board.setBox(6, 0, '4');
		// End Hamburg lvl 24
		
		// Start Paris lvl 24, moves = 4
//		MoveTheBox mtb = new MoveTheBox(4);
//		board.setBox(0, 0, '1');
//		board.setBox(2, 0, '1');
//		board.setBox(2, 1, '2');
//		board.setBox(2, 2, '1');
//		board.setBox(2, 3, '3');
//		board.setBox(2, 4, '1');
//		board.setBox(3, 0, '2');
//		board.setBox(3, 1, '3');
//		board.setBox(3, 2, '3');
//		board.setBox(3, 3, '4');
//		board.setBox(4, 0, '4');
//		board.setBox(4, 1, '1');
//		board.setBox(4, 2, '2');
//		board.setBox(4, 3, '1');
//		board.setBox(4, 4, '4');
		// End Paris lvl 24
		
		// Start Paris lvl 24 modified, moves = 5
		MoveTheBox mtb = new MoveTheBox(5);
		board.setBox(0, 0, '1');
		board.setBox(3, 0, '1');
		board.setBox(3, 1, '2');
		board.setBox(3, 2, '1');
		board.setBox(3, 3, '3');
		board.setBox(3, 4, '1');
		board.setBox(4, 0, '2');
		board.setBox(4, 1, '3');
		board.setBox(4, 2, '3');
		board.setBox(4, 3, '4');
		board.setBox(5, 0, '4');
		board.setBox(5, 1, '1');
		board.setBox(5, 2, '2');
		board.setBox(5, 3, '1');
		board.setBox(5, 4, '4');
		// End Paris lvl 24 modified
		
		System.out.println(board.toString());
		startTime = System.currentTimeMillis();
		solution = mtb.solve(board, mtb.movesRequired);
		endTime = System.currentTimeMillis();
		
		System.out.println(String.format("Took %d milliseconds", endTime - startTime));
		System.out.println(String.format("Solution size: %d", solution.size()));
		System.out.println(MoveTheBox.getSolutionString(solution));
		System.out.println("Verify if all solutions are distinct");
		System.out.println(MoveTheBox.verifyDistinctMoves(solution));
		System.out.println("Verify if all solutions solve correctly");
		System.out.println(MoveTheBox.verifySolution(board, solution));
	}

}
