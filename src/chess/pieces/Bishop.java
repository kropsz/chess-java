package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);

    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // matriz de movimentos possíveis
        Position p = new Position(0, 0);

        // noroeste
        p.setValues(position.getRows() - 1, position.getColumns() - 1);
        while (getBoard().positionExist(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
            p.setValues(p.getRows() - 1, p.getColumns() - 1);
        }
        if (getBoard().positionExist(p) && isThereOpponentPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // nordeste
        p.setValues(position.getRows() - 1, position.getColumns() + 1);
        while (getBoard().positionExist(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
            p.setValues(p.getRows() - 1, p.getColumns() + 1);
        }
        if (getBoard().positionExist(p) && isThereOpponentPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // sudeste
        p.setValues(position.getRows() + 1, position.getColumns() + 1);
        while (getBoard().positionExist(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
            p.setValues(p.getRows() + 1, p.getColumns() + 1);
        }
        if (getBoard().positionExist(p) && isThereOpponentPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // sudoeste
        p.setValues(position.getRows() + 1, position.getColumns() - 1);
        while (getBoard().positionExist(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
            p.setValues(p.getRows() + 1, p.getColumns() - 1);
        }
        if (getBoard().positionExist(p) && isThereOpponentPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        return mat;

    }

}
