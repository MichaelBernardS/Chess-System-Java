package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int moveCount;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
		// � precisa colocar um valor inteiro no construtor, pois ele j� inicia com o valor 0;
	}

	public Color getColor() {
		return color;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void increaseMoveCount() {
		moveCount++;
	}
	
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position); // position da classse Piece, convertendo pra classe chessPosition; m�todo est�tico fromposition converte para a posi��o de xadrez;
	}
	
	protected boolean isThereOpponentPiece(Position position) { // protected pq queremos que ela seja acess�vel somente por este pacote e pelas subclasses (pe�as);
		ChessPiece p = (ChessPiece)getBoard().piece(position); // downcasting p chesspiece p identificar;
		return p != null && p.getColor() != color;
	}	
}