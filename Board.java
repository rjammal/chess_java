package chess; 

import java.util.List;
import java.util.ArrayList;

public class Board {
	
	List<ChessPiece> chessPieces = new ArrayList<>();
	public static final int BOARD_SIZE = 8;
	public static final int FRONT_ROW = 0;
	public static final int BACK_ROW = 7;
	public static final int LEFT_COLUMN = 0;
	public static final int RIGHT_COLUMN = 7;

	protected Board() {
	//pawns
		for (int x = 0; x < BOARD_SIZE; x++) {
			chessPieces.add(new Pawn(x, FRONT_ROW + 1, ChessPiece.Color.BLACK));
			chessPieces.add(new Pawn(x, BACK_ROW - 1, ChessPiece.Color.WHITE));
		}

	}

	public String toString() {
		String boardString = "";
		for (ChessPiece piece : chessPieces) {
			boardString += piece.toString() + "\n";
		}
		return boardString;
	}

	protected List<ChessPiece> getAllPieces() {
		return chessPieces;
	}

	protected ChessPiece getPiece(int x, int y) {
		for (ChessPiece piece : getAllPieces()) {
			if (x == piece.getX() && y == piece.getY()) {
				return piece;
			}
		}
		return null;
	}

	public void removePiece(ChessPiece piece) {
		getAllPieces().remove(piece);
	}
	protected void addPiece(ChessPiece piece) {
		getAllPieces().add(piece);
	}

	protected void boardMove(ChessPiece piece, int newX, int newY) {
		ChessPiece.Color color = piece.getColor();
		King king = getKingOfColor(color);
		int x = piece.getX();
		int y = piece.getY();

		if (piece.isValidMove(newX, newY, this)) {
			ChessPiece capturedPiece = null;
			boolean boardEmpty = isEmpty(newX, newY); 
			if (!boardEmpty) {
				capturedPiece = getPiece(newX, newY);
			}
			piece.tempMove(newX, newY); 
			if (king.isInCheck(this)) {
				piece.tempMove(x, y);
				System.out.println("King is in check!");
			} else {
				piece.tempMove(x, y); 
				unsetEnpassant(piece);
				if (capturedPiece != null) {
					removePiece(capturedPiece);
				}
				if (piece instanceof Pawn) {
					boolean inEnpassant = ((Pawn)piece).checkEnpassant(this, newX);
					if (inEnpassant) {
						removePiece(getPiece(newX, y));
					}
				}
				piece.move(newX, newY);
			}
		}
	}

	private King getKingOfColor(ChessPiece.Color color) {
		for (ChessPiece piece : getAllPieces()) {
			if (piece instanceof King && piece.getColor() == color) {
				return (King)piece;
			}
		}
		return null;
	}
	private List<Pawn> getPawnsOfColor(ChessPiece.Color color) {
		List<Pawn> pawnList = new ArrayList<>();
		for (ChessPiece piece : getAllPieces()) {
			ChessPiece.Color pieceColor = piece.getColor();
			if (piece instanceof Pawn && color == pieceColor) {
				pawnList.add((Pawn)piece);
			}
		}
		return pawnList;
	}
	private void unsetEnpassant(ChessPiece piece) {
		ChessPiece.Color pieceColor = piece.getColor();
		List<Pawn> pawnList = getPawnsOfColor(pieceColor);
		if (pawnList.contains(piece) && ((Pawn)piece).notYetMoved) {
			pawnList.remove(piece);
		}
		for (Pawn pawn : pawnList) {
			pawn.enpassant = false;
		}
	}

	protected boolean isEmpty(int x, int y) {
		for (ChessPiece piece : getAllPieces()) {
			if (x == piece.getX() && y == piece.getY()) {
				return false;
			}
		}
		return true;
	}

}