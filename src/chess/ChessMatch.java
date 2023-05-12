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

    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition()); // coloca uma nova peça no tabuleiro
    }
    
    private void initialSetup(){
        placeNewPiece('b', 6,new Rook(board, Color.WHITE));
        placeNewPiece('e', 8, new King(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.BLACK));
    }
}
