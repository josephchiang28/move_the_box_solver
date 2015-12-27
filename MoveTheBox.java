import java.util.ArrayList;

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
//			System.out.println(swapPair);
//			System.out.println(boardNext);
			boardNext.reachSteadyState();
//			System.out.println(boardNext);
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
		ArrayList<ArrayList<SwapPair>> solution = mtb.solve(board, 4);
//		End Hamburg lvl 24
		
		System.out.println(solution.size());
		System.out.println(solution);
		 
		 
	}

}
