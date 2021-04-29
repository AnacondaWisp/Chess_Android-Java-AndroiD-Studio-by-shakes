package bg;

public class Knight {

	private char not_capturing;
	int knight_row;
	int knight_column;

	public Knight(int row, int column) {
		knight_row = row;
		knight_column = column;
		not_capturing = 'n';
	}
	public boolean check_knight(Block[][] board, int row1, int column1, int row2, int column2) {
		boolean valid = false;
		int row_diff = Math.abs(row1 - row2);
		int col_diff = Math.abs(column1 - column2);
		if (row_diff == 1 && col_diff == 2) {
			valid = true;
		} else if (row_diff == 2 && col_diff == 1) {
			valid = true;
		}
		if (valid && !(Control.check_space(board[row2][column2], not_capturing))) {
			valid = Control.check_space(board[row2][column2], board[row1][column1].current_piece.charAt(0));
		}
		return valid;
	}
	public void show_moves(Block[][] board, int row, int column) {
		System.out.println("Valid moves:");
		if ((column-2) >= 0 && (row+1) <= 7 && check_knight(board, row, column, row+1, column-2)) { // left up
			Control.print_move(row, column, row+1, column-2, false);
		}
		if ((column-2) >= 0 && (row-1) >= 0 && check_knight(board, row, column, row-1, column-2)) { // left down
			Control.print_move(row, column, row-1, column-2, false);
		}
		if ((column-1) >= 0 && (row+2) <= 7 && check_knight(board, row, column, row+2, column-1)) { // up left
			Control.print_move(row, column, row+2, column-1, false);
		}
		if ((column-1) >= 0 && (row-2) >= 0 && check_knight(board, row, column, row-2, column-1)) { // down left
			Control.print_move(row, column, row-2, column-1, false);
		}
		if ((column+1) <= 7 && (row+2) <= 7 && check_knight(board, row, column, row+2, column+1)) { // up right
			Control.print_move(row, column, row+2, column+1, false);
		}
		if ((column+1) <= 7 && (row-2) >= 0 && check_knight(board, row, column, row-2, column+1)) { // down right
			Control.print_move(row, column, row-2, column+1, false);
		}
		if ((column+2) <= 7 && (row+1) <= 7 && check_knight(board, row, column, row+1, column+2)) { // right up
			Control.print_move(row, column, row+1, column+2, false);
		}
		if ((column+2) <= 7 && (row-1) >= 0 && check_knight(board, row, column, row-1, column+2)) { // right down
			Control.print_move(row, column, row-1, column+2, false);
		}
	}
}
