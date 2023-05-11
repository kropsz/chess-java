package application;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;


public class Main {
    public static void main(String[] args) throws Exception {
        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPieces());
        
    }

}
