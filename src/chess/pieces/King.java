package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);

    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position); // pega a peça na posição
        return p == null || p.getColor() != getColor(); // retorna se a peça é nula ou se a cor é diferente
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);

        // acima
        p.setValues(position.getRows() - 1, position.getColumns()); // posição acima da peça
        if (getBoard().positionExist(p) && canMove(p)) { // se a posição existe e pode mover
            mat[p.getRows()][p.getColumns()] = true; // marca a posição como true
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

        return mat;
    }

}
