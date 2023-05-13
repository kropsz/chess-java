package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import boardgame.Board;
import boardgame.Position;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;


public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        
        while(true){
        try{
        UI.clearScreen();
        UI.printBoard(chessMatch.getPieces());
        System.out.println();
        System.out.print("Origem: ");
        ChessPosition source = UI.readChessPosition(sc);
        
        System.out.println();
        System.out.print("Destino: ");
        ChessPosition target = UI.readChessPosition(sc);

        ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
        }
        catch(ChessException e){
            System.out.println(e.getMessage());
            sc.nextLine();
        }
        catch(InputMismatchException e){
            System.out.println(e.getMessage());
            sc.nextLine();
        }

    }
        
    }

}
