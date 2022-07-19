package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
			boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
			Position p = new Position(0, 0);
		
		if (getColor() == Color.WHITE) {
				p.setValues(position.getRow() -1, position.getColumn());
				if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // significa que o pe�o pode ir p essa posi��o;
					mat[p.getRow()][p.getColumn()] = true;
				}
				p.setValues(position.getRow() -2, position.getColumn());
				Position p2 = new Position(position.getRow() - 1, position.getColumn()); // auxiliar p checar se pode andar p 2 casa;
				if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p2) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) // e pra essa tb, que � 2 linhas p cima, s� na primeira vez;
					mat[p.getRow()][p.getColumn()] = true;
			
				p.setValues(position.getRow() -1, position.getColumn() -1);
				if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // verifica se tem pe�a l�, para poder matar;
					mat[p.getRow()][p.getColumn()] = true;
				}
			
				p.setValues(position.getRow() -1, position.getColumn() +1);
				if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // verifica se tem pe�a l�, para poder matar;
					mat[p.getRow()][p.getColumn()] = true;
				}
		}
		else { // agora para a cor preta;
				p.setValues(position.getRow() +1, position.getColumn());
				if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // significa que o pe�o pode ir p essa posi��o;
						mat[p.getRow()][p.getColumn()] = true;
				}
				p.setValues(position.getRow() +2, position.getColumn());
				Position p2 = new Position(position.getRow() +1, position.getColumn()); // auxiliar p checar se pode andar p 2 casa;
				if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p2) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) // e pra essa tb, que � 2 linhas p cima, s� na primeira vez;
						mat[p.getRow()][p.getColumn()] = true;
				
				p.setValues(position.getRow() +1, position.getColumn() -1);
				if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // verifica se tem pe�a l�, para poder matar;
						mat[p.getRow()][p.getColumn()] = true;
				}
				
				p.setValues(position.getRow() +1, position.getColumn() +1);
				if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // verifica se tem pe�a l�, para poder matar;
						mat[p.getRow()][p.getColumn()] = true;
				}
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}