package com.zholdak.testing.tictactoe.service;

import java.util.List;

import com.zholdak.testing.tictactoe.model.Move;

/**
 * Some utility methods of the game logic
 *
 * @author Aleksey Zholdak (aleksey@zholdak.com) 2018-06-23 23:20
 */
public class TicTacToeLogic {

	/** What opponent's move should be first */
	private static final char FIRST_MOVE = 'X';

	/**
	 * Decide what opponent will be next
	 */
	public static char getNextOpponent(Character currentOpponent) {
		return currentOpponent == null ? FIRST_MOVE : (currentOpponent == 'X' ? 'O' : 'X');
	}

	/**
	 * Calculates X position of the board from position. Board coordinates begins from upper left corner.
	 */
	public static int getXfromPosition(int position) {
		return position % 3;
	}

	/**
	 * Calculates Y position of the board from position. Board coordinates begins from upper left corner.
	 */
	public static int getYfromPosition(int position) {
		return position / 3;
	}

	/**
	 * Build board from list of moves. Board coordinates begins from upper left corner.
	 */
	public static Character[][] buildBoard(List<Move> moves) {
		Character[][] board = new Character[3][3];
		moves.forEach(move -> {
			int position = move.getPosition();
			board[getXfromPosition(position)][getYfromPosition(position)] = move.getOpponent();
		});
		return board;
	}

	/**
	 * Takes the row and column coordinates of the last move made and checks to see if that move causes the player to
	 * win.
	 *
	 * @see <a href="https://stackoverflow.com/a/19579695">the algorithm is borrowed here</a>
	 */
	public static boolean isWinner(Character[][] board, int row, int col){
		Character player = board[row][col];

		boolean onDiagonal = (row == col) || (col == -1 * row + (board.length - 1));
		boolean horizontalWin = true, verticalWin = true;
		boolean diagonalWinOne = true, diagonalWinTwo = true;

		// Check the rows and columns
		for (int n = 0; n < board.length; n++) {
			if (!player.equals(board[row][n]))
				horizontalWin = false;
			if (!player.equals(board[n][col]))
				verticalWin = false;
		}

		// Only check diagonals if the move is on a diagonal
		if (onDiagonal) {
			// Check the diagonals
			for (int n = 0; n < board.length; n++) {
				if (!player.equals(board[n][n]))
					diagonalWinOne = false;
				if (!player.equals(board[n][-1 * n + (board.length - 1)]))
					diagonalWinTwo = false;
			}
		} else {
			diagonalWinOne = false;
			diagonalWinTwo = false;
		}

		return horizontalWin || verticalWin || diagonalWinOne || diagonalWinTwo;
	}

}
