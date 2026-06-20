package integrado.prog2.exception;

public class OpcionInvalidaException extends RuntimeException {

    // Constructor vacío (usa un mensaje por defecto)
    public OpcionInvalidaException() {
        super("Error: La opción ingresada no es válida o no es un número entero.");
    }

    // Constructor que recibe un mensaje personalizado
    public OpcionInvalidaException(String mensaje) {
        super(mensaje);
    }
}