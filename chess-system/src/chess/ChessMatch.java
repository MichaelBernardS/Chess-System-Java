package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8, 8); // na hora q iniciar a partida, cria um tabuleiro 8 por 8;
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup(); // e inicia o initialsetup, que seriam as pe�as e as posi��es das mesmas;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i<board.getRows(); i++) {
			for (int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) { // fun��o para retornar os movimentos true da matriz;
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source); // essa opera��o vai ser respons�vel por validar a posi��o de origem; verificando se n�o existe uma pe�a na posi��o de origem;
		validateTargetPosition(source, target); // e a valida��o da posi��o de destino;
		Piece capturedPiece = makeMove(source, target);
		
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false; // condicional tern�ria para verificar se  a partida est� em cheque; se sim, � vdd, se n�o, � falso;
		
		if (testCheckMate(opponent(currentPlayer))) {
				checkMate = true;
		} 
		else {
			nextTurn(); // trocagem de turno;
		}
		
		return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if (capturedPiece != null) { // se for diferente de nulo quer dizer que capturou a pe�a, ent�o tem que remover da lista de pe�as do tabuleiro e colocar na outra lista;
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) { // fun��o para desfazer o movimento, caso o jogador realize um movimento que fa�a cheque em si mesmo;
		ChessPiece p = (ChessPiece)board.removePiece(target); // tira aquela pe�a que moveu do destino;
		p.decreaseMoveCount();
		board.placePiece(p, source); // devolver a pe�a pra posi��o de origem;
		
		if (capturedPiece != null) {	
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { // valida��o p verificar a cor da pe�a, para n�o poder mover a pe�a do advers�rio; downcasing p identificar q � do chesspiece;
			throw new ChessException("The chosen piece is not yours");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) { // verificando se tem algum movimento poss�vel; se n�o tiver nenhum movimento poss�vel, a msg aparecer�;
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {// testando se: para a pe�a de origem, a posi�ao de destino n � um movimento possivel, significa q n pode mexer p l�;
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	private void nextTurn() {
		turn ++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; // express�o condicional tern�ria; se o jogador atual for o color.white, ent�o agora ele vai ser o color.black, caso contr�rio, ele vai ser o color.white;
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE; // se esta cor passada como argumento for white, ent�o retorna o black, caso contr�rio, retornar� white;
	}
	
	private ChessPiece king(Color color) { // fun��o p achar o rei do oponente; fun��o para dar check;
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); // toda pe�a x, tal que (->) a cor dessa pe�a x seja da cor passada como argumento; por�m, a lista � Piece, e Piece n�o tem cor, ent�o faremos um downcasting para ChessPiece;
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board"); // se essa msg aparecer, significa que o programa est� quebrado, pois s� foi adicionada esta exce��o, para o compilador n�o reclamar e retornar algum valor, independente se achar ou n�o o rei na lista;
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition(); // puxar o rei no formato de matriz;
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) { // se a posi��o for true, � check;
				return true;
			}
		}
		return false; // testando o check, caso d� falso no la�o for inteiro, quer dizer que n�o est� em check;
	}
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) { // testando se ele n�o est� em cheque, signifca que tamb�m ele n�o est� em chequemate;
			return false;
		}
		List <Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); // forma de pegar tds as pe�as desta cor;
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i=0; i<board.getRows(); i++) {
				for (int j=0; j<board.getColumns(); j++) {
					if (mat[i][j]) { // l�gica: mover a pe�a pra posi��o p sair do check, se continuar ainda em check, quer dizer q � checkmate;
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color); // testar se o rei da minha cor, ainda est� em check;
						undoMove(source, target, capturedPiece); // desfazer o movimento que fez para testar se estava em check;
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); // opera��o de colocar pe�a, passando a posi��o nas coordenadas do xadrez e n�o da matriz;
		piecesOnTheBoard.add(piece); // toda vez que colocar uma pe�a no tabuleiro, adicionar na lista pe�as do tabuleiro tb;
	}
	
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	}
}