package bg;

public class Queen {
	
	Bishop bishop;
	Rook rook;
	int queen_row;
	int queen_column;

	public Queen(int row, int column) {
		queen_row = row;
		queen_column = column;
		bishop = new Bishop(row, column);
		rook = new Rook(row, column);
	}

	public boolean check_queen(Block[][] board, int row1, int column1, int row2, int column2) {
		int row_diff = Math.abs(row1 - row2);
		int col_diff = Math.abs(column1 - column2);
		boolean valid = false;
		if (row1 != row2 && column1 != column2 && row_diff == col_diff) {
			valid = bishop.check_bishop(board, row1, column1, row2, column2);
		} else if (row1 == row2 && column1 != column2) { // calls upon the rook class
			valid = rook.check_rook(board, row1, column1, row2, column2);
		} else if (column1 == column2 && row1 != row2) {
			valid = rook.check_rook(board, row1, column1, row2, column2);
		}
		return valid;
	}

	public void show_moves(Block[][] board, int row, int column) {
		boolean possible_rook = false;
		boolean possible_bishop = false;
		System.out.println("Valid moves:");
		
		possible_rook = rook.show_moves(board, row, column, true);
		possible_bishop = bishop.show_moves(board, row, column, true);
		if (!(possible_rook) && !(possible_bishop)) {
			System.out.println("NONE!");
		}
	}
}
