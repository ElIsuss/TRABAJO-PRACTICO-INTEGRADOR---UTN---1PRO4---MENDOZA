package integrado.prog2.exception;

public class PedidoInvalidoException extends RuntimeException {
    public PedidoInvalidoException(String message) {
        super(message);
    }

    //para cuando se intente crear un usuario invalido
}
