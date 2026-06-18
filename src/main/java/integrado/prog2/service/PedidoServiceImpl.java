package integrado.prog2.service;

import integrado.prog2.dao.PedidoDAO;
import integrado.prog2.dao.PedidoDAOImpl;
import integrado.prog2.entities.Pedido;
import java.util.List;

public class PedidoServiceImpl implements PedidoService {

    private final PedidoDAO pedidoDAO;

    public PedidoServiceImpl() {
        this.pedidoDAO = new PedidoDAOImpl();
    }

    @Override
    public void procesarPedido(Pedido pedido) {
        // Reglas de Negocio previas a la transacción
        if (pedido.getUsuario() == null) {
            throw new RuntimeException("No se puede procesar un pedido sin un usuario asociado.");
        }
        if (pedido.getDetallesPedidos() == null || pedido.getDetallesPedidos().isEmpty()) {
            throw new RuntimeException("El pedido debe contener al menos un producto en el detalle.");
        }
        if (pedido.getFormaPago() == null) {
            throw new RuntimeException("Debe especificar una forma de pago válida.");
        }
        if (pedido.getEstado() == null) {
            throw new RuntimeException("Debe especificar un estado inicial para el pedido.");
        }

        // Delegamos la persistencia atómica al DAO (quien maneja el commit y rollback)
        pedidoDAO.crear(pedido);
    }

    @Override
    public Pedido buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("El ID de pedido provisto no es válido.");
        }
        Pedido pedido = pedidoDAO.leer(id);
        if (pedido == null) {
            throw new RuntimeException("No se encontró ningún pedido activo con el ID: " + id);
        }
        return pedido;
    }

    @Override
    public void cambiarEstadoFormaPago(Pedido pedido) {
        if (pedido.getId() == null) {
            throw new RuntimeException("No se puede actualizar un pedido sin su ID.");
        }
        // Validamos que exista antes de modificarlo
        buscarPorId(pedido.getId());

        pedidoDAO.actualizarEstadoFormaPago(pedido);
    }

    @Override
    public void cancelarPedido(Long id) {
        // Validamos existencia antes del borrado lógico
        buscarPorId(id);
        pedidoDAO.eliminar(id);
    }

    @Override
    public List<Pedido> listarTodos() {
        return pedidoDAO.listarTodos();
    }
}