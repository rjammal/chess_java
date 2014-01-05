package chess;

import java.lang.reflect.Constructor;

public class Pawn extends ChessPiece {

	boolean notYetMoved = true;
	boolean enpassant = false;
	
	protected Pawn(int x, int y, Color color) {
		super(x, y, color);
	}

	protected void move(int newX, int newY) {
		if (Math.abs(getY() - y) == 2){
			enpassant = true;
		}
		super.move(newX, newY);
		notYetMoved = false;
	}

	protected boolean isValidMove(int newX, int newY, Board board) {

		if (!super.isValidMove(newX, newY, board)) {
			return false;
		}

		int x = getX();
		int y = getY();
		int spaceAhead;
		int spaceTwoAhead;
		boolean validY;

		boolean movingForward = x == newX;
		if (getColor() == Color.BLACK) {
			spaceAhead = y + 1;
			spaceTwoAhead = y + 2;
		} else {
			spaceAhead = y - 1;
			spaceTwoAhead = y - 2;
		}

		if (movingForward) {
			if (!board.isEmpty(x, spaceAhead)) {
				return false;
			}
			if (notYetMoved) {
				validY = newY == spaceAhead || 
				         newY == spaceTwoAhead;
			} else {
				validY = newY == spaceAhead;
			}
			return validY;
		} else {
			// check all capture scenarios
			if (Math.abs(x - newX) > 1 ||
				newY != spaceAhead) {
				return false;
			}

			if (board.isEmpty(newX, newY)) {

				// check for enpassant
				boolean enpassantPiece = checkEnpassant(board, newX);
				return enpassantPiece;
			} else {
				ChessPiece otherPiece = board.getPiece(newX, newY);
				boolean differentColors = getColor() != otherPiece.getColor();
				return differentColors && newY == spaceAhead;
			}
		}
	}

	protected boolean checkEnpassant(Board board, int newX) {
		int y = getY();
		if (!board.isEmpty(newX, y)) {
			ChessPiece adjacentPiece = board.getPiece(newX, y);
			if (!(adjacentPiece instanceof Pawn)) {
				return false;
			} else {
				return adjacentPiece.getColor() != getColor() &&
				       ((Pawn)adjacentPiece).enpassant;
			}
		}
		return false;
	}

	protected boolean inPromotionSpace() {
		if (getColor() == Color.WHITE) {
			return getY() == Board.FRONT_ROW;
		} else {
			return getY() == Board.BACK_ROW;
		}
	}
/*
	protected void promote(Board board, Class<? extends ChessPiece> newClass) {
		if (inPromotionSpace()) {
			Integer x = getX();
			Integer y = getY();
			Color color = getColor();
			board.removePiece(this);
			Constructor<? extends ChessPiece> constructor = newClass.getConstructor(Integer.class, Integer.class, ChessPiece.Color.class);
			ChessPiece newPiece = constructor.newInstance(x, y, color);
			board.addPiece(newPiece);
		}
	}
*/
}