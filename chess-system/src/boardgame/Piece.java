package boardgame;

public class Piece {
	
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null; // Posi��o de uma pe�a rec�m criada vai ser nulo;
	}

	protected Board getBoard() { // protected p ser acessado somente pelo pacote tabuleiro, e por nenhuma classe fora;
		return board;
	}
}
