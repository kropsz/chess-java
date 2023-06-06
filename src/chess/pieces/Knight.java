package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "N";
    }

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position); // pega a peça na posição
        return p == null || p.getColor() != getColor(); // retorna se a peça é nula ou se a cor é diferente
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);

        // noroeste
        p.setValues(position.getRows() - 1, position.getColumns() - 2);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // nordeste
        p.setValues(position.getRows() - 2, position.getColumns() - 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // sudeste
        p.setValues(position.getRows() - 2, position.getColumns() + 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // sudoeste
        p.setValues(position.getRows() - 1, position.getColumns() + 2);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // noroeste
        p.setValues(position.getRows() + 1, position.getColumns() + 2);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // nordeste
        p.setValues(position.getRows() + 2, position.getColumns() + 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // sudeste
        p.setValues(position.getRows() + 2, position.getColumns() - 1);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // sudoeste
        p.setValues(position.getRows() + 1, position.getColumns() - 2);
        if (getBoard().positionExist(p) && canMove(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        return mat;

    }
}
