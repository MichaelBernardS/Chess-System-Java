package application;

import chess.ChessMatch;	

public class Program {

	public static void main(String[] args) {
		
		ChessMatch chessMatch = new ChessMatch();
		UI.printBoard(chessMatch.GetPieces()); // função p imprimir as peças dessa partida; UI = User Interface;
		
	}
}