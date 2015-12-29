import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class MoveTheBox {
	
	private HashMap<String, Integer> unsolvableBoardSequences;
	
	public MoveTheBox() {
		unsolvableBoardSequences = new HashMap<String, Integer>();
	}
	
	public ArrayList<ArrayList<SwapPair>> solve(Board board, int movesLeft) {
		ArrayList<ArrayList<SwapPair>> curMoves = new ArrayList<ArrayList<SwapPair>>();
		String boardSequence = board.getBoardSequence();
		if (movesLeft <= 0 || board.getTotalBoxes() < 3
				|| (unsolvableBoardSequences.containsKey(boardSequence) && movesLeft <= unsolvableBoardSequences.get(boardSequence))) {
			return curMoves;
		}
		ArrayList<SwapPair> possibleSwaps = board.generateSwaps();
		ArrayList<ArrayList<SwapPair>> nextMoves;
		for (SwapPair swapPair: possibleSwaps) {
			Board boardNext = new Board(board);
			boardNext.swapBoxes(swapPair.x1, swapPair.y1, swapPair.x2, swapPair.y2);
			boardNext.reachSteadyState();
			if (boardNext.isComplete()) {
				ArrayList<SwapPair> a = new ArrayList<SwapPair>();
				a.add(swapPair);
				curMoves.add(a);
				return curMoves;
			}
			nextMoves = solve(boardNext, movesLeft - 1);
			if (!nextMoves.isEmpty()) {
				for (ArrayList<SwapPair> al: nextMoves) {
					al.add(0, swapPair);
				}
				curMoves.addAll(nextMoves);
			}
		}
		if (curMoves.isEmpty()) {
			unsolvableBoardSequences.put(boardSequence, movesLeft);
		}
		return curMoves;
	}
	
	public boolean verifyDistinctMoves(ArrayList<ArrayList<SwapPair>> solution) {
		if (solution.isEmpty()) {
			System.out.println("Error: Empty Solution");
			return false;
		}
		TreeSet<String> distinctMoves = new TreeSet<String>();
		for (ArrayList<SwapPair> moves: solution) {
			String movesString = "";
			for (SwapPair swapPair: moves) {
				movesString += swapPair.toString() + " ";
			}
			if (distinctMoves.contains(movesString)) {
				return false;
			}
			distinctMoves.add(movesString);
		}
//		System.out.println(distinctMoves);
//		System.out.println(distinctMoves.size());
		return true;
	}
	
	public boolean verifySolution(Board board, ArrayList<ArrayList<SwapPair>> solution) {
		if (solution.isEmpty()) {
			System.out.println("Error: Empty Solution");
			return false;
		}
		boolean allPass = true;
		for (ArrayList<SwapPair> moves: solution) {
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
		MoveTheBox mtb = new MoveTheBox();
		Board board = new Board();
		long startTime, endTime;
		ArrayList<ArrayList<SwapPair>> solution;
		
		// Simple test, moves = 1
//		board.setBox(0, 0, '1');
//		board.setBox(1, 0, '2');
//		board.setBox(2, 0, '1');
//		board.setBox(0, 1, '2');
//		board.setBox(1, 1, '1');
//		board.setBox(2, 1, '2');
//		System.out.println(board.toString());
//		startTime = System.currentTimeMillis();
//		solution = mtb.solve(board, 1);
//		endTime = System.currentTimeMillis();
		// End simple test

		// Hamburg lvl 3, moves = 1
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
//		System.out.println(board.toString());
//		startTime = System.currentTimeMillis();
//		solution = mtb.solve(board, 1);
//		endTime = System.currentTimeMillis();
		// End Hamburg lvl 3

		// Hamburg lvl 6, moves = 2
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
//		System.out.println(board.toString());
//		startTime = System.currentTimeMillis();
//		solution = mtb.solve(board, 2);
//		endTime = System.currentTimeMillis();
		// End Hamburg lvl 6

		// Hamburg lvl 17, moves = 3
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
//		System.out.println(board.toString());
//		startTime = System.currentTimeMillis();
//		solution = mtb.solve(board, 3);
//		endTime = System.currentTimeMillis();
		// End Hamburg lvl 17
		 
		// Hamburg lvl 24, moves = 4
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
//		System.out.println(board.toString());
//		startTime = System.currentTimeMillis();
//		solution = mtb.solve(board, 4);
//		endTime = System.currentTimeMillis();
		// End Hamburg lvl 24
		
		// Start Paris lvl 24, moves = 4
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
//		System.out.println(board.toString());
//		startTime = System.currentTimeMillis();
//		solution = mtb.solve(board, 4);
//		endTime = System.currentTimeMillis();
		// End Paris lvl 24
		
		// Start Paris lvl 24 modified, moves = 5
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
		System.out.println(board.toString());
		startTime = System.currentTimeMillis();
		solution = mtb.solve(board, 5);
		endTime = System.currentTimeMillis();
		// End Paris lvl 24 modified
		
		System.out.println(String.format("Took %1$d milliseconds", endTime - startTime));
		System.out.println(String.format("Solution size: %1$d", solution.size()));
		System.out.println(solution);
		System.out.println("Verify if all solutions are distinct");
		System.out.println(mtb.verifyDistinctMoves(solution));
		System.out.println("Verify if solution actually solves");
		System.out.println(mtb.verifySolution(board, solution));
	}

}
