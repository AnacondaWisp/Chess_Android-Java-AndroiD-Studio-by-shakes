package bg;

import cs213.chess.android.AndroidChessActivity;

public class King {

	private char not_capturing;
	private char left_or_right;
	int king_row;
	int king_column;
	boolean moved;

	public King(int row, int column) {
		king_row = row;
		king_column = column;
		moved = false;
		not_capturing = 'n';
		left_or_right = 'n';
	}

	public boolean check_king(Block[][] board, int row1, int column1, int row2, int column2, boolean checking) {
		int row_diff = Math.abs(row1 - row2);
		int col_diff = Math.abs(column1 - column2);
		boolean valid = false;
		if (board[row2][column2].current_piece != null && board[row1][column1].current_piece.charAt(0) == board[row2][column2].current_piece.charAt(0)) {
			return valid;
		}
		if (row_diff == 0 && col_diff == 2) {
			valid = check_castling(board, row1, column1, row2, column2);
		} else if (row_diff == 0 && col_diff == 1) {
			valid = true;
		} else if (row_diff == 1 && col_diff == 0) {
			valid = true;
		} else if (row_diff == 1 && col_diff == 1) {
			valid = true;
		}
		if (valid && !(checking)) {
			if (!(moved)) {
				moved = true;
			}
		}
		return valid;
	}
	public boolean check_castling(Block[][] board, int row1, int column1, int row2, int column2) {
		int start = -1, end = -1;
		boolean valid = false;
		boolean castling = false;
		boolean rook = false;
		
		if (column2 > column1) {
			start = column1;
			end = column1+3;
			rook = rook_status(board, board[row1][column1].current_piece.charAt(0), 'r');
		} else {
			start = column2;
			end = column2+4;
			rook = rook_status(board, board[row1][column1].current_piece.charAt(0), 'l');
		}
		if (board[row1][column1].current_piece.charAt(0) == 'w') {
			castling = Control.white_castle;
		} else {
			castling = Control.black_castle;
		}
		if (!(castling) && !(moved) && !(rook)) {
			for (int i = start+1; i < end; i++) {
				System.out.println("CHECKING CASTLING: " + row1 + " " + i + " ending at: " + end);
				if (!(valid = Control.check_space(board[row1][i], not_capturing))) {
					break;
				}
			}
		}
		if (valid) {
			if (board[row1][column1].current_piece.charAt(0) == 'w') {
				Control.white_castle = true;
				update_castling(board, 'w');
			} else {
				Control.black_castle = true;
				update_castling(board, 'b');
			}
			AndroidChessActivity.castling = true;
		}
		return valid;
	}

	public boolean rook_status(Block[][] board, char owner, char left_right) {
		int row = -1, column = -1;
		if (owner == 'w') {
			row = 0;
		} else {
			row = 7;
		}
		
		if (left_right == 'l') {
			column = 0;
			left_or_right = 'l';
		} else {
			column = 7;
			left_or_right = 'r';
		}
		return ((Rook) board[row][column].piece_object).move_status();
	}
	public void update_castling(Block[][] board, char player) {
		if (player == 'w') {
			if (left_or_right == 'l') {
				Control.valid_move(board, "wR", 0, 0, 0, 3, 'Q');
			} else {
				Control.valid_move(board, "wR", 0, 7, 0, 5, 'Q');
			}
		} else {
			if (left_or_right == 'l') {
				Control.valid_move(board, "bR", 7, 0, 7, 3, 'Q');
			} else {
				Control.valid_move(board, "bR", 7, 7, 7, 5, 'Q');
			}
		}
	}

	public boolean show_moves(Block[][] board, int row, int column, boolean checking) {
		boolean possible = false;
		if (!(checking)) {
			System.out.println("Valid moves:");
		}
		
		if (!(moved)) {
			if (Chess.current_player.equals("White")) {
				if (check_king(board, row, column, 0, 6, true) && possible_check(board, row, column)) {
					possible = true;
					Control.print_move(row, column, 0, 6, checking);
				}
				if (check_king(board, row, column, 0, 2, true) && possible_check(board, row, column)) {
					possible = true;
					Control.print_move(row, column, 0, 2, checking);
				}
			} else {
				if (check_king(board, row, column, 7, 6, true) && possible_check(board, row, column)) {
					possible = true;
					Control.print_move(row, column, 7, 6, checking);
				}
				if (check_king(board, row, column, 7, 2, true) && possible_check(board, row, column)) {
					possible = true;
					Control.print_move(row, column, 7, 2, checking);
				}
			}
		}
		
		if (row+1 <= 7 && check_king(board, row, column, row+1, column, true) && possible_check(board, row+1, column)) { // up
			possible = true;
			Control.print_move(row, column, row+1, column, checking);
		}
		if (row-1 >= 0 && check_king(board, row, column, row-1, column, true) && possible_check(board, row-1, column)) { // down
			possible = true;
			Control.print_move(row, column, row-1, column, checking);
		}
		if (column+1 <= 7 && check_king(board, row, column, row, column+1, true) && possible_check(board, row, column+1)) { // right
			possible = true;
			Control.print_move(row, column, row, column+1, checking);
		}
		if (column-1 >= 0 && check_king(board, row, column, row, column-1, true) && possible_check(board, row, column-1)) { // left
			possible = true;
			Control.print_move(row, column, row, column-1, checking);
		}
		if (column+1 <= 7 && row+1 <=7 && check_king(board, row, column, row+1, column+1, true) && possible_check(board, row+1, column+1)) { // up right
			possible = true;
			Control.print_move(row, column, row+1, column+1, checking);
		}
		if (column+1 <= 7 && row-1 >= 0 && check_king(board, row, column, row-1, column+1, true) && possible_check(board, row-1, column+1)) { // down right
			possible = true;
			Control.print_move(row, column, row-1, column+1, checking);
		}
		if (column-1 >= 0 && row+1 <= 7 && check_king(board, row, column, row+1, column-1, true) && possible_check(board, row+1, column-1)) { // up left
			possible = true;
			Control.print_move(row, column, row+1, column-1, checking);
		}
		if (column-1 >= 0 && row-1 >= 0 && check_king(board, row, column, row-1, column-1, true) && possible_check(board, row-1, column-1)) { // down left
			possible = true;
			Control.print_move(row, column, row-1, column-1, checking);
		}
		if (!(checking) && !(possible)) {
			System.out.println("NONE!");
		}
		return possible;
	}
	public boolean possible_check(Block[][] board, int row, int column) {
		char opponent = 'n';
		boolean valid = true;
		if (Chess.next_player.equals("White")) {
			opponent = 'w';
		} else {
			opponent = 'b';
		}
		
		for (int i = board.length-1; i >= 0; i--) {
			for (int n = 0; n < board[i].length; n++) {
				if (board[i][n].current_piece != null && board[i][n].current_piece.charAt(0) == opponent &&
					Control.check_piece(board, board[i][n].current_piece, i, n, row, column, true))  {
					valid = false;
					break;
				}
			}
		}
		return valid;
	}
}
