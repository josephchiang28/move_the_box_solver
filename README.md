# Move The Box Solver

A Java program that solves a puzzle game similar to Move The Box (https://play.google.com/store/apps/details?id=ua.co.cts.sideup)

## Puzzles it solve:
The puzzles, like Move The Box, that this program can solve starts with a grid of various different boxes stacked on top of each other. The objective is to clear all boxes with a limited number of moves. A move consists of swapping a box with an adjacent box or empty space. A set of boxes is cleared when there are 3 or more consecutive identical boxes in a row or column. If necessary, boxes will gravitate to the bottom after each move resulting in a stacked arrangement.

## To use the solver:
The `Board` class consists of a 2D x by y char array, which represents the box grid of the game. (0,0) is the bottom left corner of the grid and x increments to the right and y increments upwards. 

With the exception of `'0'` and `'!'`, which represent an empty and invalid spot respectively, you can represent each type of box with a certain char of your liking.

The solution returned by the `solve` method of MoveTheBox can be converted into a string with `MoveTheBox.getSolutionString(solution)`. The string contains a list of a sequence of moves. Each sequence is a specific order of moves/swaps that can solve the given puzzle, and there may be multiple sequences that do so. Each move is represented with a 4 digit number coordinate (assuming each dimension of the grid is of 9 or less) in the form of x1y1x2y2 where you swap the boxes of (x1, y1) and (x2, y2).

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