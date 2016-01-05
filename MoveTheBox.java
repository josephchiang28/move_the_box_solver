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
	
	public ArrayList<SwapPair[]> solve(Board board, SwapPair prevSwap, int movesLeft) {	
		String boardSequence = board.getBoardSequence();
		if (movesLeft <= 0 || board.getTotalBoxes() < 3
				|| (unsolvableBoards.containsKey(boardSequence) && movesLeft <= unsolvableBoards.get(boardSequence))) {
			// Returns empty ArrayList if board unsolvable
			return new ArrayList<SwapPair[]>();
		}
		ArrayList<SwapPair[]> nextMoves, curMoves = new ArrayList<SwapPair[]>();
		int moveIndex = movesRequired - movesLeft;
		boolean isBoardChanged;
		Board boardNext;
		for (SwapPair swapPair: board.generateSwaps()) {
			if (swapPair.equals(prevSwap)) {
				continue;
			}
			boardNext = new Board(board);
			boardNext.swapBoxes(swapPair.x1, swapPair.y1, swapPair.x2, swapPair.y2);
			isBoardChanged = boardNext.reachSteadyState();
			if (boardNext.isComplete()) {
				SwapPair[] moves = new SwapPair[movesRequired];
				moves[moveIndex] = swapPair;
				curMoves.add(moves);
				return curMoves;
			}
			if (isBoardChanged) {
				nextMoves = solve(boardNext, null, movesLeft - 1);
			} else {
				nextMoves = solve(boardNext, swapPair, movesLeft - 1);
			}
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
	
	public static TreeSet<String> getSolutionTreeSet(ArrayList<SwapPair[]> solution) {
		TreeSet<String> distinctMoves = new TreeSet<String>();
		for (SwapPair[] moves: solution) {
			distinctMoves.add(Arrays.toString(moves));
		}
		return distinctMoves;
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
					System.out.println(Arrays.toString(moves));
					break;
				} else if (swapPair == null) {
					allPass = false;
					System.out.print("Contains a null swap: ");
					System.out.println(Arrays.toString(moves));
					break;
				} else {
					boardTest.swapBoxes(swapPair.x1, swapPair.y1, swapPair.x2, swapPair.y2);
					boardTest.reachSteadyState();
				}
			}
			if (!boardTest.isComplete()) {
				allPass = false;
				System.out.print("Board not completed with moves: ");
				System.out.println(Arrays.toString(moves));
			}
		}
		return allPass;
	}
	
	public static void main(String[] args) {
		MoveTheBox mtb;
		Board board;
		long startTime, endTime;
		ArrayList<SwapPair[]> solution;
		TreeSet<String> solutionTreeSet;
		String solutionStringSorted;
		String standardSolutionString;
		
		// Simple test, moves = 1
//		mtb = new MoveTheBox(1);
//		board = new Board(7, 9);
//		board.setBox(0, 0, '1');
//		board.setBox(1, 0, '2');
//		board.setBox(2, 0, '1');
//		board.setBox(0, 1, '2');
//		board.setBox(1, 1, '1');
//		board.setBox(2, 1, '2');
//		standardSolutionString = "[[1011]]";
		// End simple test

		// Hamburg lvl 3, moves = 1
//		mtb = new MoveTheBox(1);
//		board = new Board(7, 9);
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
//		standardSolutionString = "[[4142]]";
		// End Hamburg lvl 3

		// Hamburg lvl 6, moves = 2
//		mtb = new MoveTheBox(2);
//		board = new Board(7, 9);
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
//		standardSolutionString = "[[2333, 2021]]";
		// End Hamburg lvl 6

		// Hamburg lvl 17, moves = 3
//		mtb = new MoveTheBox(3);
//		board = new Board(7, 9);
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
//		standardSolutionString = "[[2131, 2223, 5051], [2131, 4142, 2223], [2131, 5152, 2223], [2223, 2131, 5051], [2223, 4142, 2131], [2223, 5152, 2131], [4142, 2131, 2223], [4142, 2223, 2131], [5152, 2131, 2223], [5152, 2223, 2131]]";
		// End Hamburg lvl 17
		 
		// Hamburg lvl 24, moves = 4
//		mtb = new MoveTheBox(4);
//		board = new Board(7, 9);
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
//		standardSolutionString = "[[1323, 2434, 3233, 2232], [1323, 3233, 2232, 2434], [1323, 3233, 2434, 2232], [2223, 2333, 1222, 2434], [2223, 2333, 2434, 1222], [2223, 2434, 2333, 1222], [2434, 1323, 3233, 2232], [2434, 1424, 3233, 2232], [2434, 2223, 2333, 1222], [2434, 3233, 1323, 2232], [2434, 3233, 1424, 2232], [3233, 1323, 2232, 2434], [3233, 1323, 2434, 2232], [3233, 2434, 1323, 2232], [3233, 2434, 1424, 2232]]";
		// End Hamburg lvl 24
		
		// Start Paris lvl 24, moves = 4
//		mtb = new MoveTheBox(4);
//		board = new Board(7, 9);
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
//		standardSolutionString = "[[1020, 3141, 4142, 4050], [1020, 4142, 3242, 4050], [1222, 3141, 3132, 4050], [1222, 4142, 3242, 4050], [1424, 3141, 4142, 4050], [3141, 1020, 4142, 4050], [3141, 1222, 3132, 4050], [3141, 1424, 4142, 4050], [3141, 3132, 1222, 4050], [3141, 4142, 1020, 4050], [3141, 4142, 1222, 4050], [3141, 4142, 1424, 4050], [4142, 1020, 3242, 4050], [4142, 1222, 3242, 4050], [4142, 3242, 1020, 4050], [4142, 3242, 1222, 4050], [4142, 3242, 4050, 1020]]";
		// End Paris lvl 24
		
		// Start Paris lvl 24 modified, moves = 5
		mtb = new MoveTheBox(5);
		board = new Board(7, 9);
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
		standardSolutionString = "[[0010, 2030, 4151, 5152, 5060], [0010, 2030, 5152, 4252, 5060], [0010, 2232, 4151, 4142, 5060], [0010, 2232, 5152, 4252, 5060], [0010, 2434, 4151, 5152, 5060], [0010, 4151, 2030, 5152, 5060], [0010, 4151, 2232, 4142, 5060], [0010, 4151, 2434, 5152, 5060], [0010, 4151, 4142, 2232, 5060], [0010, 4151, 5152, 2030, 5060], [0010, 4151, 5152, 2232, 5060], [0010, 4151, 5152, 2434, 5060], [0010, 5152, 2030, 4252, 5060], [0010, 5152, 2232, 4252, 5060], [0010, 5152, 4252, 2030, 5060], [0010, 5152, 4252, 2232, 5060], [0010, 5152, 4252, 5060, 2030], [2030, 0010, 4151, 5152, 5060], [2030, 0010, 5152, 4252, 5060], [2030, 4151, 0010, 5152, 5060], [2030, 4151, 5152, 0010, 5060], [2030, 4151, 5152, 5060, 0010], [2030, 5152, 0010, 4252, 5060], [2030, 5152, 4252, 0010, 5060], [2030, 5152, 4252, 5060, 0010], [2232, 0010, 4151, 4142, 5060], [2232, 0010, 5152, 4252, 5060], [2232, 4151, 0010, 4142, 5060], [2232, 4151, 4142, 0010, 5060], [2232, 4151, 5152, 0010, 5060], [2232, 4151, 5152, 5060, 0010], [2232, 5152, 0010, 4252, 5060], [2232, 5152, 4252, 0010, 5060], [2232, 5152, 4252, 5060, 0010], [2434, 0010, 4151, 5152, 5060], [2434, 4151, 0010, 5152, 5060], [2434, 4151, 5152, 0010, 5060], [2434, 5152, 4252, 5060, 0010], [4151, 0010, 2030, 5152, 5060], [4151, 0010, 2232, 4142, 5060], [4151, 0010, 2434, 5152, 5060], [4151, 0010, 4142, 2232, 5060], [4151, 0010, 5152, 2030, 5060], [4151, 0010, 5152, 2232, 5060], [4151, 0010, 5152, 2434, 5060], [4151, 2030, 0010, 5152, 5060], [4151, 2030, 5152, 0010, 5060], [4151, 2030, 5152, 5060, 0010], [4151, 2232, 0010, 4142, 5060], [4151, 2232, 4142, 0010, 5060], [4151, 2232, 5152, 0010, 5060], [4151, 2232, 5152, 5060, 0010], [4151, 2434, 0010, 5152, 5060], [4151, 2434, 5152, 0010, 5060], [4151, 4142, 0010, 2232, 5060], [4151, 4142, 2232, 0010, 5060], [4151, 5152, 0010, 2030, 5060], [4151, 5152, 0010, 2232, 5060], [4151, 5152, 0010, 2434, 5060], [4151, 5152, 2030, 0010, 5060], [4151, 5152, 2030, 5060, 0010], [4151, 5152, 2232, 0010, 5060], [4151, 5152, 2232, 5060, 0010], [4151, 5152, 2434, 0010, 5060], [5152, 0010, 2030, 4252, 5060], [5152, 0010, 2232, 4252, 5060], [5152, 0010, 4252, 2030, 5060], [5152, 0010, 4252, 2232, 5060], [5152, 0010, 4252, 5060, 2030], [5152, 2030, 0010, 4252, 5060], [5152, 2030, 4252, 0010, 5060], [5152, 2030, 4252, 5060, 0010], [5152, 2232, 0010, 4252, 5060], [5152, 2232, 4252, 0010, 5060], [5152, 2232, 4252, 5060, 0010], [5152, 2434, 4252, 5060, 0010], [5152, 4252, 0010, 2030, 5060], [5152, 4252, 0010, 2232, 5060], [5152, 4252, 0010, 5060, 2030], [5152, 4252, 2030, 0010, 5060], [5152, 4252, 2030, 5060, 0010], [5152, 4252, 2232, 0010, 5060], [5152, 4252, 2232, 5060, 0010], [5152, 4252, 2434, 5060, 0010], [5152, 4252, 5060, 0010, 2030], [5152, 4252, 5060, 2030, 0010], [5152, 4252, 5060, 2333, 0010]]";
		// End Paris lvl 24 modified
		
		System.out.println(board.toString());
		startTime = System.currentTimeMillis();
		solution = mtb.solve(board, null, mtb.movesRequired);
		endTime = System.currentTimeMillis();
		
		System.out.println(String.format("Took %d milliseconds", endTime - startTime));
		solutionTreeSet = getSolutionTreeSet(solution);
		solutionStringSorted = solutionTreeSet.toString();
		System.out.println("Sorted generated solution:");
		System.out.println(solutionStringSorted);
		System.out.println(String.format("Solution size: %d", solution.size()));
		System.out.println(String.format("Number of unsolvable boards pruned out: %d", mtb.unsolvableBoards.size()));
		System.out.println("Verify if generated solution matches standard solution:");
		System.out.println(standardSolutionString.equals(solutionStringSorted));
		System.out.println("Verify if all solutions are distinct:");
		System.out.println(solution.size() == solutionTreeSet.size());
		System.out.println("Verify if all solutions solve correctly:");
		System.out.println(verifySolution(board, solution));
	}

}
