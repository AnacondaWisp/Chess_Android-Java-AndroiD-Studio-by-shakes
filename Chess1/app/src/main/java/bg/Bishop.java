package bg;
public class Bishop {

	int bishop_row;
	int bishop_column;
	private char not_capturing;
	

	public Bishop (int row, int column) {
		bishop_row = row;
		bishop_column = column;
		not_capturing = 'n';
	}

	public boolean check_bishop(Block[][] board, int row1, int column1, int row2, int column2) {
		int row_diff = Math.abs(row1 - row2);
		int col_diff = Math.abs(column1 - column2);
		int start_row = -1, end_row = -1, start_col = -1;
		boolean upward_slope = false;
		boolean valid = false;
		
		if (row1 != row2 && column1 != column2 && row_diff == col_diff) {
			if (row1 > row2 && column1 > column2) {
				start_row = row2;
				end_row = row1;
				start_col = column2;
				upward_slope = true;
			} else if (row1 > row2 && column1 < column2) {
				start_row = row1;
				end_row = row2;
				start_col = column1;
			} else if (row1 < row2 && column1 < column2) {
				start_row = row1;
				end_row = row2;
				start_col = column1;
				upward_slope = true;
			} else {
				start_row = row2;
				end_row = row1;
				start_col = column2;
			}
			valid = true;
		}
		if (valid) {
			if (row_diff == 1 && col_diff == 1) {

			} else if (upward_slope) {
				for (int i = start_row+1, n = start_col+1; i < end_row; i++, n++) {
					valid = Control.check_space(board[i][n], not_capturing);
					if (!(valid)) {	break;	}
				}
			} else {
				for (int i = start_row-1, n = start_col+1; i > end_row; i--, n++) {
					valid = Control.check_space(board[i][n], not_capturing);
					if (!(valid)) {	break;	}
				}
			}
		}
		if (valid && !(Control.check_space(board[row2][column2], not_capturing))) {
			valid = Control.check_space(board[row1][column1], board[row2][column2].current_piece.charAt(0));
		}
		return valid;
	}

	public boolean show_moves(Block[][] board, int row, int column, boolean queen) {
		boolean possible = false;
		if (!(queen)) {
			System.out.println("Valid moves:");
		}
		
		for (int i = row+1, n = column+1; i <= 7 && n <= 7; i++, n++) { // upper right
			if (check_bishop(board, row, column, i, n)) {
				Control.print_move(row, column, i, n, false);
				possible = true;
			}
		}
		for (int i = row-1, n = column+1; i >= 0 && n <= 7; i--, n++) { // lower right
			if (check_bishop(board, row, column, i, n)) {
				Control.print_move(row, column, i, n, false);
				possible = true;
			}
		}
		for (int i = row+1, n = column-1; i <= 7 && n >= 0; i++, n--) { // upper left
			if (check_bishop(board, row, column, i, n)) {
				Control.print_move(row, column, i, n, false);
				possible = true;
			}
		}
		for (int i = row-1, n = column-1; i >= 0 && n >= 0; i--, n--) { // lower left
			if (check_bishop(board, row, column, i, n)) {
				Control.print_move(row, column, i, n, false);
				possible = true;
			}
		}
		
		if (!(queen) && !(possible)) {
			System.out.println("NONE!");
		}
		return possible;
	}
}
