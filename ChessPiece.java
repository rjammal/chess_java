package chess;

public abstract class ChessPiece{
	
	int x;
	int y;
	Color color;

	public enum Color {BLACK, WHITE}


	protected ChessPiece(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Color getColor() {
		return color;
	}
	public String getColorString() {
		String result = "";
		switch (color) {
			case BLACK: 
			result = "Black";
			break;
			case WHITE: 
			result = "White";
			break;
		}
		return result;
	}

	public String toString() {
		return String.format("%s %s (%i, %i)", getColorString(), 
			                                   this.getClass().getName(), 
			                                   getX(), getY());
	}

	private void setX(int x) {
		this.x = x;
	}
	private void setY(int y) {
		this.y = y;
	}

	protected void move(int newX, int newY) {
		setX(newX);
		setY(newY);
	}
	protected final void tempMove(int newX, int newY) {
		setX(newX);
		setY(newY);
	}

	protected boolean isValidMove(int newX, int newY, Board board) {
		boolean xOnBoard = 0 <= newX && newX < Board.BOARD_SIZE;
		boolean yOnBoard = 0 <= newY && newY < Board.BOARD_SIZE;
		int x = getX();
		int y = getY();

		if (xOnBoard && yOnBoard) {

			//check if position changes
			if (newX == x && newY == y) {
				return false;
			}
			else if (board.isEmpty(newX, newY)) {
				return true;
			}
			else {
				ChessPiece pieceInNewLocation = board.getPiece(newX, newY);
				if (getColor() != pieceInNewLocation.getColor()) {
					return true;
				}
				else {return false; }
			}
		}
		else {return false;}

	}


}