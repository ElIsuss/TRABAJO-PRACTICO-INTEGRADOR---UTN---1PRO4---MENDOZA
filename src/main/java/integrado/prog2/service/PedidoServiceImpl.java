package integrado.prog2.service;

import integrado.prog2.dao.PedidoDAO;
import integrado.prog2.dao.PedidoDAOImpl;
import integrado.prog2.entities.Pedido;
import integrado.prog2.exception.EntityNotFoundException;
import integrado.prog2.exception.PedidoInvalidoException;
import integrado.prog2.exception.ValidationException;
import java.util.List;

public class PedidoServiceImpl implements PedidoService {

    private final PedidoDAO pedidoDAO;

    public PedidoServiceImpl() {
        this.pedidoDAO = new PedidoDAOImpl();
    }

    @Override
    public void procesarPedido(Pedido pedido) {
        if (pedido.getUsuario() == null) {
            throw new PedidoInvalidoException("No se puede procesar un pedido sin un usuario asociado.");
        }
        if (pedido.getDetallesPedidos() == null || pedido.getDetallesPedidos().isEmpty()) {
            throw new PedidoInvalidoException("El pedido debe contener al menos un producto en el detalle.");
        }
        if (pedido.getFormaPago() == null) {
            throw new PedidoInvalidoException("Debe especificar una forma de pago válida.");
        }
        if (pedido.getEstado() == null) {
            throw new PedidoInvalidoException("Debe especificar un estado inicial para el pedido.");
        }

        // TODO: Si tuvieras la lógica acá, podrías verificar stock de productos y lanzar StockInsuficienteException

        pedidoDAO.crear(pedido);
    }

    @Override
    public Pedido buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("El ID de pedido provisto no es válido.");
        }
        Pedido pedido = pedidoDAO.leer(id);
        if (pedido == null) {
            throw new EntityNotFoundException("No se encontró ningún pedido activo con el ID: " + id);
        }
        return pedido;
    }

    @Override
    public void cambiarEstadoFormaPago(Pedido pedido) {
        if (pedido.getId() == null) {
            throw new ValidationException("No se puede actualizar un pedido sin su ID.");
        }
        buscarPorId(pedido.getId());

        pedidoDAO.actualizarEstadoFormaPago(pedido);
    }

    @Override
    public void cancelarPedido(Long id) {
        buscarPorId(id);
        pedidoDAO.eliminar(id);
    }

    @Override
    public List<Pedido> listarTodos() {
        return pedidoDAO.listarTodos();
    }
}