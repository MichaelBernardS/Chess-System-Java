package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces; // declarado uma matriz de pe�as;
	
	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns]; // matriz de pe�as instanciada no construtor;
	}
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public Piece piece(int row, int column) {
		if (!positionExists(row, column)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position" + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece; // pegando a matriz na posi��o dada e atribuindo a pe�a informada;
		piece.position = position; // pe�a n�o est� mais na posi��o NULA, est� nesta posi��o informada; conseguimos acessar livremente a pe�a, pois a mesma foi declarada como protected no mesmo pacote;
	}
	
	public Piece removePiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		if (piece(position) == null) {
			return null;
		}
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
	private boolean positionExists(int row, int column) { 
		return row >= 0 && row < rows && column >= 0 && column < columns; // rows = altura do tabuleiro; columns = qtde de colunas do tabuleiro;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) { // antes de testar o thereisapiece ele j� testa se a posi��o existe, se n�o existir ele j� para e lan�a uma exce��o;
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null;
	}
}