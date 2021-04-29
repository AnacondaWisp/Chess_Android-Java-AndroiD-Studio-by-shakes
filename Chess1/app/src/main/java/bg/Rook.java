package bg;

public class Rook {

	private char not_capturing;
	private boolean moved;
	int rook_row;
	int rook_column;

	public Rook(int row, int column) {
		rook_row = row;
		rook_column = column;
		moved = false;
		not_capturing = 'n';
	}

	public boolean check_rook(Block[][] board, int row1, int column1, int row2, int column2) {
		int start = 0, end = 0;
		boolean valid = false;
		if (row1 == row2 && column1 != column2) {
			if (column1 > column2) {
				start = column2;
				end = column1;
			} else {
				start = column1;
				end = column2;
			}
			if (start+1 == end) {
				valid = true;
			} else {
				for (int i = start+1; i < end; i++) {
					valid = Control.check_space(board[row1][i], not_capturing);
					if (!(valid)) {
						break;
					}
				}
			}
			if (valid && !(Control.check_space(board[row2][column2], not_capturing))) {
				valid = Control.check_space(board[row1][column1], board[row2][column2].current_piece.charAt(0));
			}
		}
		else if (column1 == column2 && row1 != row2) {
			if (row1 > row2) {
				start = row2;
				end = row1;
			} else {
				start = row1;
				end = row2;
			}
			if (start+1 == end) {
				valid = true;
			} else {
				for (int i = start+1; i < end; i++) {
					valid = Control.check_space(board[i][column1], not_capturing);
					if (!(valid)) {
						break;
					}
				}
			}
			if (valid && !(Control.check_space(board[row2][column2], not_capturing))) {
				valid = Control.check_space(board[row1][column1], board[row2][column2].current_piece.charAt(0));
			}
		}
		if (!(moved) && valid) {
			moved = true;
		}
		return valid;
	}

	public boolean move_status() {
		return moved;
	}

	public boolean show_moves(Block[][] board, int row, int column, boolean queen) {
		boolean possible = false;
		if (!(queen)) {
			System.out.println("Valid moves:");
		}
		
		for (int i = row+1; i <= 7; i++) {
			if (check_rook(board, row, column, i, column)) {
				Control.print_move(row, column, i, column, false);
				possible = true;
			}
		}
		for (int i = row-1; i >= 0; i--) {
			if (check_rook(board, row, column, i, column)) {
				Control.print_move(row, column, i, column, false);
				possible = true;
			}
		}
		for (int i = column+1; i <= 7; i++) {
			if (check_rook(board, row, column, row, i)) {
				Control.print_move(row, column, row, i, false);
				possible = true;
			}
		}
		for (int i = column-1; i >= 0; i--) {
			if (check_rook(board, row, column, row, i)) {
				Control.print_move(row, column, row, i, false);
				possible = true;
			}
		}
		
		if (!(queen) && !(possible)) {
			System.out.println("NONE!");
		}
		return possible;
	}
}
