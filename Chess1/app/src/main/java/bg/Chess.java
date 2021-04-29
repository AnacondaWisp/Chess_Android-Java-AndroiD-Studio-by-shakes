package bg;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import cs213.chess.android.AndroidChessActivity;

public class Chess {

	public static String current_player = "";
	public static String next_player = "";
	public static int turn = 0;
	
	static int row1, row2, column1, column2;
	static char promote = 'Q';
	
	// return values
	public static int valid = 0;
	public static int white_checkmate = 1;
	public static int black_checkmate = 2;
	public static int white_resign = 3;
	public static int black_resign = 4;
	public static int draw = 5;
	public static int invalid_piece = 6;
	public static int invalid_ownership = 7;
	public static int invalid_move = 8;
	public static int check = 9;
	
	public static int chess(Block[][] board, String command) throws IOException {
		
		/* +++++++++++++++++++++++++++++++++++++++++++++++++++
		FileInputStream file = new FileInputStream("test.txt");
		DataInputStream data = new DataInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(data));
		   +++++++++++++++++++++++++++++++++++++++++++++++++++ */
	
		//Board.print_board(board);
		
		if (turn%2 == 0) {
			current_player = "White";
			next_player = "Black";
		} else {
			current_player = "Black";
			next_player = "White";
		}
					
		System.out.println(current_player + "'s move: [" + command + "]");
		
		// checks for user input
		if (command.isEmpty()) {
			/* +++++++++++++++++++++++++
			command = reader.readLine();
			System.out.println(command);
			   +++++++++++++++++++++++++ */
		}
		
		// stores the user input
		String[] position = command.split(" ");
		
		// if user resigns the game
		if (position[0].equalsIgnoreCase("resign")) {
			/* +++++++++++++++++++++++++++++++
			System.out.println("+++++++++++");
			System.out.println(next_player + " wins");
			System.out.println("+++++++++++");
			+++++++++++++++++++++++++++++++ */
			if (next_player.equalsIgnoreCase("white")) {
				return black_resign;
			} else {
				return white_resign;
			}
		}
		
		// checks if user accepts the draw
		if (position[0].equalsIgnoreCase("draw") && Control.draw) {
			/* +++++++++++++++++++++++++++++++
			System.out.println("+++++++++++");
			System.out.println("It's a draw");
			System.out.println("+++++++++++");
			+++++++++++++++++++++++++++++++ */
			return draw;
		} else {
			Control.draw = false;
		}
		
		// attempts to get the desired piece
		String piece = Control.getPiece(board, position[0]);
		promote = 'Q';
		
		// checks if the piece exists
		if (piece == null) {
			System.out.println("Piece at \"" + position[0] + "\" does not exist!");
			return invalid_piece;
		}
		
		AndroidChessActivity.piece = piece;
		AndroidChessActivity.color = Control.getColor(board, position[0]);
		AndroidChessActivity.last_color = Control.getColor(board, position[1]);
		
		if (!Control.check_ownership(piece, turn)) {
			System.out.println("You do not own " + piece + "!");
			return invalid_ownership;
		}
		
		if (position.length == 3) {
			if (position[2].length() == 1 && Control.check_input(position[2].charAt(0))) { // checks if user is trying to promote
				promote = position[2].charAt(0);
			} else if (position[2].equals("draw?")) { // checks for draw
				Control.draw = true;
			}
		}
		
		row1 = Control.convert(position[0].charAt(1));
		column1 = Control.convert(position[0].charAt(0));
		row2 = Control.convert(position[1].charAt(1));
		column2 = Control.convert(position[1].charAt(0));
		
		if (!Control.valid_move(board, piece, row1, column1, row2, column2, promote)) {
			return invalid_move;
		}
		
		if (Control.white_en_passant && Chess.current_player.equals("Black")) {
			Control.white_en_passant = false;
		} else if (Control.black_en_passant && Chess.current_player.equals("White")) {
			Control.black_en_passant = false;
		}
		
		if (AndroidChessActivity.promote) {
			piece = Control.getPiece(board, position[1]);
		}
		
		if (Control.check(board, piece, row2, column2)) {
			if (next_player.equals("White")) {
				Control.white_check = true;
				if (!(((King) board[Control.black_king_row][Control.black_king_col].piece_object).show_moves(board, Control.black_king_row, Control.black_king_col, true))) {

					return black_checkmate;
				}
			} else {
				Control.black_check = true;
				if (!(((King) board[Control.white_king_row][Control.white_king_col].piece_object).show_moves(board, Control.white_king_row, Control.white_king_col, true))) {
					return white_checkmate;
				}
			}
			return check;
		}
		turn++;
		AndroidChessActivity.game_started = true;
		AndroidChessActivity.undone = false;
		return valid;
	}
}
