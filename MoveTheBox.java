import java.util.ArrayList;
import java.util.TreeSet;

public class MoveTheBox {
	
	public ArrayList<ArrayList<SwapPair>> solve(Board board, int movesLeft) {
		ArrayList<ArrayList<SwapPair>> curMoves = new ArrayList<ArrayList<SwapPair>>();
		if (movesLeft <= 0 || board.getTotalBoxes() < 3) {
			return curMoves;
		}
		ArrayList<SwapPair> possibleSwaps = board.generateSwaps();
		ArrayList<ArrayList<SwapPair>> nextMoves;
		for (SwapPair swapPair: possibleSwaps) {
			Board boardNext = new Board(board);
			boardNext.swapBoxes(swapPair.box1, swapPair.box2);
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
		return curMoves;
	}
	
	public boolean verifyDistinctMoves(ArrayList<ArrayList<SwapPair>> allMoves) {
		TreeSet<String> distinctMoves = new TreeSet<String>();
		for (ArrayList<SwapPair> moves: allMoves) {
			String movesString = "";
			for (SwapPair swapPair: moves) {
				movesString += swapPair.toString();
				movesString += " ";
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
		boolean allPass = true;
		for (ArrayList<SwapPair> moves: solution) {
			Board boardTest = new Board(board);
			for (SwapPair swap: moves) {
				if (board.isComplete()) {
					allPass = false;
					System.out.print("Board completed with redundant moves: ");
					System.out.println(moves);
				}
				boardTest.swapBoxes(swap.box1, swap.box2);
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
		
//		Simple test, moves = 1
//		board.setBoxType(0, 0, 1);
//		board.setBoxType(1, 0, 2);
//		board.setBoxType(2, 0, 1);
//		board.setBoxType(0, 1, 2);
//		board.setBoxType(1, 1, 1);
//		board.setBoxType(2, 1, 2);
//		System.out.println(board.toString());
//		ArrayList<ArrayList<SwapPair>> solution = mtb.solve(board, 1);
//		End simple test

//		Hamburg lvl 3, moves = 1
//		board.setBoxType(2, 0, 1);
//		board.setBoxType(2, 1, 2);
//		board.setBoxType(2, 2, 1);
//		board.setBoxType(3, 0, 1);
//		board.setBoxType(3, 1, 2);
//		board.setBoxType(3, 2, 1);
//		board.setBoxType(4, 0, 3);
//		board.setBoxType(4, 1, 1);
//		board.setBoxType(4, 2, 3);
//		board.setBoxType(4, 3, 3);
//		board.setBoxType(4, 4, 1);
//		board.setBoxType(4, 5, 2);
//		System.out.println(board.toString());
//		ArrayList<ArrayList<SwapPair>> solution = mtb.solve(board, 1);
//		End Hamburg lvl 3

//		Hamburg lvl 6, moves = 2
//		board.setBoxType(2, 0, 1);
//		board.setBoxType(3, 0, 2);
//		board.setBoxType(3, 1, 3);
//		board.setBoxType(3, 2, 1);
//		board.setBoxType(3, 3, 2);
//		board.setBoxType(4, 0, 2);
//		board.setBoxType(4, 1, 3);
//		board.setBoxType(4, 2, 1);
//		board.setBoxType(4, 3, 4);
//		board.setBoxType(5, 0, 3);
//		board.setBoxType(5, 1, 4);
//		board.setBoxType(6, 0, 4);
//		System.out.println(board.toString());
//		ArrayList<ArrayList<SwapPair>> solution = mtb.solve(board, 2);
//		End Hamburg lvl 6

//		Hamburg lvl 17, moves = 3
//		board.setBoxType(0, 0, 1);
//		board.setBoxType(1, 0, 1);
//		board.setBoxType(1, 1, 2);
//		board.setBoxType(2, 0, 3);
//		board.setBoxType(2, 1, 2);
//		board.setBoxType(2, 2, 1);
//		board.setBoxType(2, 3, 3);
//		board.setBoxType(2, 4, 2);
//		board.setBoxType(3, 0, 1);
//		board.setBoxType(3, 1, 3);
//		board.setBoxType(3, 2, 4);
//		board.setBoxType(3, 3, 1);
//		board.setBoxType(3, 4, 2);
//		board.setBoxType(4, 0, 4);
//		board.setBoxType(4, 1, 1);
//		board.setBoxType(4, 2, 2);
//		board.setBoxType(5, 0, 4);
//		board.setBoxType(5, 1, 2);
//		board.setBoxType(5, 2, 1);
//		System.out.println(board.toString());
//		ArrayList<ArrayList<SwapPair>> solution = mtb.solve(board, 3);
//		End Hamburg lvl 17
		 
//		Hamburg lvl 24, moves = 4
		board.setBoxType(2, 0, 1);
		board.setBoxType(2, 1, 1);
		board.setBoxType(2, 2, 2);
		board.setBoxType(2, 3, 3);
		board.setBoxType(3, 0, 3);
		board.setBoxType(3, 1, 1);
		board.setBoxType(3, 2, 2);
		board.setBoxType(3, 3, 1);
		board.setBoxType(3, 4, 3);
		board.setBoxType(3, 5, 2);
		board.setBoxType(4, 0, 3);
		board.setBoxType(4, 1, 4);
		board.setBoxType(4, 2, 1);
		board.setBoxType(5, 0, 4);
		board.setBoxType(5, 1, 1);
		board.setBoxType(6, 0, 4);
		System.out.println(board.toString());
		long startTime = System.currentTimeMillis();
		ArrayList<ArrayList<SwapPair>> solution = mtb.solve(board, 4);
		long endTime = System.currentTimeMillis();
		System.out.println(String.format("Took %1$d milliseconds", endTime - startTime));
//		End Hamburg lvl 24
		
		System.out.println(solution.size());
		System.out.println(solution);
		
		System.out.println("Verify if all solution are distinct");
		System.out.println(mtb.verifyDistinctMoves(solution));
		System.out.println("Verify if solution actually solves");
		System.out.println(mtb.verifySolution(board, solution));
	}

}
