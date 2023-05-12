package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
    
    private Board board;

    public ChessMatch() {
        board = new Board(8, 8); // tabuleiro de xadrez tem 8 linhas e 8 colunas
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
    
    private void initialSetup(){
        board.placePiece(new Rook(board, Color.WHITE), new Position(2,1));
        board.placePiece(new King(board,Color.BLACK ), new Position(0,4));
        board.placePiece(new King(board,Color.BLACK ), new Position(7,4));
    }
}
