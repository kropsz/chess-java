package boardgame;

public class BoardException extends RuntimeException {
    private static final long serialVersionUID = 1L; // esse atributo é padrão para exceções

    public BoardException(String msg) {
        super(msg);
    }
}
