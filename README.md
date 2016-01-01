# Move The Box Solver

A Java program that solves a puzzle game similar to Move The Box (https://play.google.com/store/apps/details?id=ua.co.cts.sideup)

## Puzzle it solves:
The puzzle, Move The Box, that this program attempts to solve starts with a grid of various different boxes stacked on top of each other, and the objective is to clear all the boxes. Boxes can be cleared by swapping two adjacent boxes with each move resulting in 3 or more consecutive identical boxes in a row or column. Boxes will then drop to the bottom if necessary resulting in a stacked arrangement.

## To use the solver:
The `Board` class consists of a 2D x by y char array, which represents the box grid of the game. (0,0) is the bottom left corner of the grid and x increases to the right and y increases upwards. 
With the exception of `'0'` and `'!'`, which represents a empty and invalid spot respectively, you can represent each type of box with a certain char of your liking.
The solution returned by the `solve` method of MoveTheBox can be converted to a string with `MoveTheBox.getSolutionString(solution)`. The string contains a list of a list of moves. Each list is a sequence of swaps/moves that can solve the given puzzle. Each move is represented with a 4 digit number coordinate (assuming each dimension of the grid is of 9 or less) in the form of x1y1x2y2 where you swap the boxes of (x1, y1) and (x2, y2).

A full example that solves Move The Box level Hamburg level 17 is as follows:
```
Board board = new Board(7, 9);
board.setBox(0, 0, '1');
board.setBox(1, 0, '1');
board.setBox(1, 1, '2');
board.setBox(2, 0, '3');
board.setBox(2, 1, '2');
board.setBox(2, 2, '1');
board.setBox(2, 3, '3');
board.setBox(2, 4, '2');
board.setBox(3, 0, '1');
board.setBox(3, 1, '3');
board.setBox(3, 2, '4');
board.setBox(3, 3, '1');
board.setBox(3, 4, '2');
board.setBox(4, 0, '4');
board.setBox(4, 1, '1');
board.setBox(4, 2, '2');
board.setBox(5, 0, '4');
board.setBox(5, 1, '2');
board.setBox(5, 2, '1');
MoveTheBox mtb = new MoveTheBox(3);
ArrayList<SwapPair[]> solution = mtb.solve(board, mtb.movesRequired);
System.out.println(MoveTheBox.getSolutionString(solution));
```

For more examples please see the main method of `MoveTheBox.java`