package integrado.prog2.service;

import integrado.prog2.entities.Pedido;
import java.util.List;

public interface PedidoService {
    void procesarPedido(Pedido pedido); // Lanza la transacción
    Pedido buscarPorId(Long id);
    void cambiarEstadoFormaPago(Pedido pedido);
    void cancelarPedido(Long id); // Baja lógica
    List<Pedido> listarTodos();
}