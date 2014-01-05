package chess; 

public class King extends ChessPiece {

	public King(int x, int y, Color color) {
		super(x, y, color);
	}

	public boolean isInCheck(Board board) {
		return false;
	}
	
}