package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

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

    private Board board;
    private int turn;
    private Color currentPlayer;
    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;

    public int getTurn() { // retorna o turno
        return turn;
    }

    public Color getCurrentPlayer() { // retorna a cor do jogador atual
        return currentPlayer;
    }

    public boolean getCheck() { // retorna se o rei do oponente está em check
        return check;
    }

    public boolean getCheckMate() { // retorna se o rei do oponente está em checkmate
        return checkMate;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
    }

    public ChessMatch() {
        board = new Board(8, 8); // tabuleiro de xadrez tem 8 linhas e 8 colunas
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public ChessPiece[][] getPieces() { // retorna uma matriz de peças de xadrez correspondente a partida
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()]; // matriz de peças de xadrez
        for (int i = 0; i < board.getRows(); i++) { // percorrer todas as linhas
            for (int j = 0; j < board.getColumns(); j++) { // percorrer todas as colunas
                mat[i][j] = (ChessPiece) board.piece(i, j); // downcasting
            }
        }
        return mat;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition(); // converte a posição de xadrez para posição de matriz
        validateSourcePosition(position); // valida a posição de origem
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition(); // converte a posição de xadrez para posição de matriz
        Position target = targetPosition.toPosition(); // converte a posição de xadrez para posição de matriz
        validateSourcePosition(source); // valida a posição de origem
        validadeTargetPosition(source, target); // valida a posição de destino
        Piece capturedPiece = makeMove(source, target); // faz o movimento
        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece); // desfaz o movimento
            throw new ChessException("Você não pode se colocar em check !! atenção");
        }

        ChessPiece movedPiece = (ChessPiece) board.piece(target); // pega a peça movida
        // #specialmove promotion
        promoted = null;
        if(movedPiece instanceof Pawn){
            if((movedPiece.getColor() == Color.WHITE && target.getRows() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRows() == 7)){
                promoted = (ChessPiece)board.piece(target);
                promoted = replacePromotedPiece("Q");
            }
        }
        check = (testCheck(opponent(currentPlayer))) ? true : false; // verifica se o rei do oponente está em check
        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn(); // passa o turno
        }

        // #specialmove en passant
        if (movedPiece instanceof Pawn && (target.getRows() == source.getRows() - 2 || target.getRows() == source.getRows() + 2)) { // se a peça movida for um peão e o alvo estiver a duas linhas de distância da origem
            enPassantVulnerable = movedPiece; // peça vulnerável ao en passant
        } else {
            enPassantVulnerable = null;
  
        }
        return (ChessPiece) capturedPiece; // retorna a peça capturada
    }

    public ChessPiece replacePromotedPiece(String typePiece){
        if(promoted == null){
            throw new IllegalStateException("Não existe peça para ser promovida");
        }
        if(!typePiece.equals("B") && !typePiece.equals("N") && !typePiece.equals("R") && !typePiece.equals("Q")){
            throw new InvalidParameterException("Tipo de peça inválida para promoção");
        }
        Position pos = promoted.getChessPosition().toPosition();
        Piece p = board.removePiece(pos);
        piecesOnTheBoard.remove(p);
        
        ChessPiece newPiece = newPiece(typePiece,promoted.getColor());
        board.placePiece(newPiece, pos);
        piecesOnTheBoard.add(newPiece);
        
        return newPiece;
    }

    private ChessPiece newPiece(String typePiece, Color color){
        if(typePiece.equals("B")) return new Bishop(board,color);
        if(typePiece.equals("N")) return new Knight(board,color);
        if(typePiece.equals("Q")) return new Queen(board,color);
        return new Rook(board,color);
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source); // remove a peça da posição de origem
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target); // remove a peça da posição de destino
        board.placePiece(p, target); // coloca a peça na posição de destino
        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece); // remove a peça da lista de peças no tabuleiro
            capturedPieces.add(capturedPiece); // adiciona a peça na lista de peças capturadas
        }

        // #specialmove castling kingside rook
        if (p instanceof King && target.getColumns() == source.getColumns() + 2) { // se a peça for um rei e o alvo estiver a duas colunas a direita
            Position sourceT = new Position(source.getRows(), source.getColumns() + 3); // posição de origem da torre
            Position targetT = new Position(source.getRows(), source.getColumns() + 1); // posição de destino da torre
            ChessPiece rook = (ChessPiece) board.removePiece(sourceT); // remove a torre da posição de origem
            board.placePiece(rook, targetT); // coloca a torre na posição de destino
            rook.increaseMoveCount(); // aumenta o contador de movimentos da torre
        }

        // #specialmove castling queenside rook
        if (p instanceof King && target.getColumns() == source.getColumns() - 2) {
            Position sourceT = new Position(source.getRows(), source.getColumns() - 4); // posição de origem da torre
            Position targetT = new Position(source.getRows(), source.getColumns() - 1); // posição de destino da torre
            ChessPiece rook = (ChessPiece) board.removePiece(sourceT); // remove a torre da posição de origem
            board.placePiece(rook, targetT); // coloca a torre na posição de destino
            rook.increaseMoveCount(); // aumenta o contador de movimentos da torre
        }

        // #specialmove en passant

        if (p instanceof Pawn) {
            if (source.getColumns() != target.getColumns() && capturedPiece == null) { // se a peça movida for um peão e a coluna de origem for diferente da coluna de destino e a peça capturada for nula                                                                  
                Position pawnPosition; // posição do peão
                if (p.getColor() == Color.WHITE) { // se a cor do peão for branca
                    pawnPosition = new Position(target.getRows() + 1, target.getColumns()); // posição do peão capturado é a posição de destino mais uma linha abaixo
                } else { // se a cor do peão for preta
                    pawnPosition = new Position(target.getRows() - 1, target.getColumns()); // posição do peão capturado é a posição de destino mais uma linha acima
                }
                capturedPiece = board.removePiece(pawnPosition); // remove a peça capturada
                capturedPieces.add(capturedPiece); // adiciona a peça na lista de peças capturadas
                piecesOnTheBoard.remove(capturedPiece); // remove a peça da lista de peças no tabuleiro
            }

        }
        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target); // remove a peça da posição de destino
        p.decreaseMoveCount();
        board.placePiece(p, source); // coloca a peça na posição de origem
        
        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target); // coloca a peça capturada na posição de destino
            capturedPieces.remove(capturedPiece); // remove a peça da lista de peças capturadas
            piecesOnTheBoard.add(capturedPiece); // adiciona a peça na lista de peças no tabuleiro
        }

        // #specialmove castling kingside rook
        if (p instanceof King && target.getColumns() == source.getColumns() + 2) { // se a peça for um rei e o alvo estiver a duas colunas a direita
            Position sourceT = new Position(source.getRows(), source.getColumns() + 3); // posição de origem da torre
            Position targetT = new Position(source.getRows(), source.getColumns() + 1); // posição de destino da torre
            ChessPiece rook = (ChessPiece) board.removePiece(targetT); // remove a torre da posição de destino
            board.placePiece(rook, sourceT); // coloca a torre na posição de origem
            rook.decreaseMoveCount(); // diminui o contador de movimentos da torre
        }

        // #specialmove castling queenside rook
        if (p instanceof King && target.getColumns() == source.getColumns() - 2) {
            Position sourceT = new Position(source.getRows(), source.getColumns() - 4); // posição de origem da torre
            Position targetT = new Position(source.getRows(), source.getColumns() - 1); // posição de destino da torre
            ChessPiece rook = (ChessPiece) board.removePiece(targetT); // remove a torre da posição de destino
            board.placePiece(rook, sourceT); // coloca a torre na posição de origem
            rook.decreaseMoveCount(); // diminui o contador de movimentos da torre
        }

        // #specialmove en passant
        if (p instanceof Pawn) {
            if (source.getColumns() != target.getColumns() && capturedPiece == enPassantVulnerable) { // se a peça movida for um peão e a coluna de origem for diferente da coluna de destino e a peça capturada for igual a peça vulnerável ao en passant
                ChessPiece pawn = (ChessPiece) board.removePiece(target); // remove o peão da posição de destino
                Position pawnPosition; // posição do peão
                if (p.getColor() == Color.WHITE) { // se a cor do peão for branca
                    pawnPosition = new Position(3, target.getColumns()); // posição do peão capturado é a posição de destino mais uma linha abaixo
                } else { // se a cor do peão for preta
                    pawnPosition = new Position(4, target.getColumns()); // posição do peão capturado é a posição de destino mais uma linha acima
                }
                board.placePiece(pawn, pawnPosition); // coloca o peão na posição de origem
            }
        }
    }

    private void validateSourcePosition(Position position) { // valida a posição de origem
        if (!board.thereIsAPiece(position)) { // se não existir peça na posição de origem
            throw new ChessException("Não existe peça na posição de origem"); // lança uma exceção
        }
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {  // se a cor da peça na posição de origem for diferente da cor do jogador atual
            throw new ChessException("A peça escolhida não é sua");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) { // se não existir movimentos possíveis para a peça na posição de origem
            throw new ChessException("Não existe movimentos possíveis para a peça escolhida");
        }
    }

    private void validadeTargetPosition(Position source, Position target) { // valida a posição de destino
        if (!board.piece(source).possibleMove(target)) { // se a peça na posição de origem não puder se mover para a posição de destino
            throw new ChessException("A peça escolhida não pode se mover para a posição de destino");
        }
    }

    private void nextTurn() { // passa o turno
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; // se a cor atual for branca, a próxima será preta, se não, será branca
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE; // se a cor for branca, retorna preta, se não, retorna branca
    }

    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
                .collect(java.util.stream.Collectors.toList()); // filtra a lista de peças no tabuleiro para pegar apenas as peças da cor passada
        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("Não existe o rei da cor " + color + " no tabuleiro");
    }

    private boolean testCheck(Color color) {
        Position kinPosition = king(color).getChessPosition().toPosition(); // pega a posição do rei
        List<Piece> opponentPieces = piecesOnTheBoard.stream()
                .filter(x -> ((ChessPiece) x).getColor() == opponent(color))
                .collect(java.util.stream.Collectors.toList()); // filtra a lista de peças no tabuleiro para pegar apenas as peças da cor adversária
        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves(); // pega a matriz de movimentos possíveis da peça
            if (mat[kinPosition.getRows()][kinPosition.getColumns()]) { // se a posição do rei estiver na matriz de movimentos possíveis da peça
                return true; // retorna que o rei está em check
            }
        }
        return false; // retorna que o rei não está em check
    }

    public boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
                .collect(java.util.stream.Collectors.toList());
        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
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
        board.placePiece(piece, new ChessPosition(column, row).toPosition()); // coloca uma nova peça no tabuleiro
        piecesOnTheBoard.add(piece); // adiciona a peça na lista de peças no tabuleiro
    }

    private void initialSetup() {

        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE,this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE,this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK,this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK,this));

    }
}
