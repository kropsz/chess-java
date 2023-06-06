package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    public Pawn(Board board, Color color) {
        super(board, color);

    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);
        if (getColor() == Color.WHITE) {
            p.setValues(position.getRows() - 1, position.getColumns());
            if (getBoard().positionExist(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRows()][p.getColumns()] = true;
            }
            p.setValues(position.getRows() - 2, position.getColumns());
            Position p2 = new Position(position.getRows() - 1, position.getColumns());
            if (getBoard().positionExist(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExist(p2)
                    && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                mat[p.getRows()][p.getColumns()] = true;
            }
            p.setValues(position.getRows() - 1, position.getColumns() - 1);
            if (getBoard().positionExist(p) && isThereOpponentPiece(p)) {
                mat[p.getRows()][p.getColumns()] = true;
            }
            p.setValues(position.getRows() - 1, position.getColumns() + 1);
            if (getBoard().positionExist(p) && isThereOpponentPiece(p)) {
                mat[p.getRows()][p.getColumns()] = true;
            }

        }
        else{
            p.setValues(position.getRows() + 1, position.getColumns());
            if (getBoard().positionExist(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRows()][p.getColumns()] = true;
            }
            p.setValues(position.getRows() + 2, position.getColumns());
            Position p2 = new Position(position.getRows() + 1, position.getColumns());
            if (getBoard().positionExist(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExist(p2)
                    && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                mat[p.getRows()][p.getColumns()] = true;
            }
            p.setValues(position.getRows() + 1, position.getColumns() - 1);
            if (getBoard().positionExist(p) && isThereOpponentPiece(p)) {
                mat[p.getRows()][p.getColumns()] = true;
            }
            p.setValues(position.getRows() + 1, position.getColumns() + 1);
            if (getBoard().positionExist(p) && isThereOpponentPiece(p)) {
                mat[p.getRows()][p.getColumns()] = true;
            }

        }

        return mat;

    }
}
