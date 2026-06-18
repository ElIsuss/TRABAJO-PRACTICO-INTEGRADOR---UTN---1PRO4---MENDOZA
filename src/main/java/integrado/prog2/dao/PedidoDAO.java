package integrado.prog2.dao;

import integrado.prog2.entities.Pedido;
import java.util.List;

public interface PedidoDAO {
    void crear(Pedido pedido); // Aquí va la lógica transaccional pesada (Manejo de Stock + Rollback)
    Pedido leer(Long id);
    void actualizarEstadoFormaPago(Pedido pedido); // Para cumplir con la HU de actualización de estado
    void eliminar(Long id); // Baja lógica (eliminado = true)
    List<Pedido> listarTodos();
}