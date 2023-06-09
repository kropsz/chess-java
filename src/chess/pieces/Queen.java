package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece{

    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "Q";
    }


    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // matriz de movimentos possíveis
        Position p = new Position(0, 0);
        // em cima
        p.setValues(position.getRows() - 1, position.getColumns());
        while (getBoard().positionExist(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
            p.setRows(p.getRows() - 1);
        }
        if (getBoard().positionExist(p) && isThereOpponentPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // esquerda
        p.setValues(position.getRows(), position.getColumns() - 1);
        while (getBoard().positionExist(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
            p.setColumns(p.getColumns() - 1);
        }
        if (getBoard().positionExist(p) && isThereOpponentPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // direita
        p.setValues(position.getRows(), position.getColumns() + 1);
        while (getBoard().positionExist(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
            p.setColumns(p.getColumns() + 1);
        }
        if (getBoard().positionExist(p) && isThereOpponentPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

        // baixo
        p.setValues(position.getRows() + 1, position.getColumns());
        while (getBoard().positionExist(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
            p.setRows(p.getRows() + 1);
        }
        if (getBoard().positionExist(p) && isThereOpponentPiece(p)) {
            mat[p.getRows()][p.getColumns()] = true;
        }

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
    

