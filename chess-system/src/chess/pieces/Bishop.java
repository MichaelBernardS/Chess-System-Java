package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

	public Bishop(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // a matriz retorna falso;
		
		Position p = new Position(0, 0);
		
		// northwest
		p.setValues(position.getRow() - 1, position.getColumn() - 1); // position = posi��o da pe�a, atributo da classe Piece;
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() - 1); 
			}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // para comer a pe�a, se tiver;
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// norteast
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() +1); 
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { 
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// southeast
		p.setValues(position.getRow() +1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() + 1); 
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// southwest
		p.setValues(position.getRow() + 1 , position.getColumn() - 1); 
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() - 1); 
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { 
			mat[p.getRow()][p.getColumn()] = true;
		}
		return mat;
	}
}