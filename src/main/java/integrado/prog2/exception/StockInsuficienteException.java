package integrado.prog2.exception;

public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String message) {
        super(message);
    }

    //para cuando no hay stock
}
