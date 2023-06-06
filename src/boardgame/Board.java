package boardgame;

public class Board {
    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new BoardException("Erro ao criar tabuleiro: é necessário que haja pelo menos 1 linha e 1 coluna");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece(int row, int column) {
        if (!positionExist(row, column)) {
            throw new BoardException("Posição não existe no tabuleiro");
        }
        return pieces[row][column];
    }

    public Piece piece(Position position) {
        if (!positionExist(position)) {
            throw new BoardException("Posição não existe no tabuleiro");
        }
        return pieces[position.getRows()][position.getColumns()];

    }

    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("Já existe uma peça na posição " + position);
        }
        pieces[position.getRows()][position.getColumns()] = piece;
        piece.position = position;
    }

    public Piece removePiece(Position position) {
        if (!positionExist(position)) {
            throw new BoardException("Posição não existe no tabuleiro");
        }
        if (piece(position) == null) {
            return null;
        }
        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRows()][position.getColumns()] = null;
        return aux;
    }

    private boolean positionExist(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExist(Position position) {
        return positionExist(position.getRows(), position.getColumns());
    }

    public boolean thereIsAPiece(Position position) {
        if (!positionExist(position)) {
            throw new BoardException("Posição não existe no tabuleiro");
        }
        return piece(position) != null;
    }
}