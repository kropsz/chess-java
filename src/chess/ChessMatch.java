package chess;

import boardgame.Board;

public class ChessMatch {
    
    private Board board;

    public ChessMatch() {
        board = new Board(8, 8); // tabuleiro de xadrez tem 8 linhas e 8 colunas
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
    
}
