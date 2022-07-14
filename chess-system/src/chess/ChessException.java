package chess;

import boardgame.BoardException;

public class ChessException extends BoardException { // boardException para ficar mais simpels do programa tratar. Pois vai capturar Chess ou BoardException;
	private static final long serialVersionUID = 1L;
	
	public ChessException(String msg) {
		super(msg);
	}
}