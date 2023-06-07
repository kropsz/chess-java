package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position); // pega a peça na posição
        return p == null || p.getColor() != getColor(); // retorna se a peça é nula ou se a cor é diferente
    }

    private boolean testRookCastling(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);

        // acima
        p.setValues(position.getRows() - 1, position.getColumns());
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // abaixo
        p.setValues(position.getRows() + 1, position.getColumns());
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // esquerda
        p.setValues(position.getRows(), position.getColumns() - 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // direita
        p.setValues(position.getRows(), position.getColumns() + 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // noroeste
        p.setValues(position.getRows() - 1, position.getColumns() - 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // nordeste
        p.setValues(position.getRows() - 1, position.getColumns() + 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // sudoeste
        p.setValues(position.getRows() + 1, position.getColumns() - 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // sudeste
        p.setValues(position.getRows() + 1, position.getColumns() + 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // #specialmove castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            // #specialmove castling kingside rook
            Position posT1 = new Position(position.getRows(), position.getColumns() + 3);
            if (testRookCastling(posT1)) {
                Position p1 = new Position(position.getRows(), position.getColumns() + 1);
                Position p2 = new Position(position.getRows(), position.getColumns() + 2);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
                    mat[position.getRows()][position.getColumns() + 2] = true;
                }
            }
            // #specialmove castling queenside rook
            Position posT2 = new Position(position.getRows(), position.getColumns() - 4);
            if (testRookCastling(posT2)) {
                Position p1 = new Position(position.getRows(), position.getColumns() - 1);
                Position p2 = new Position(position.getRows(), position.getColumns() - 2);
                Position p3 = new Position(position.getRows(), position.getColumns() - 3);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
                    mat[position.getRows()][position.getColumns() - 2] = true;
                }
            }
        }

        return mat;
    }

}
