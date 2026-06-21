package integrado.prog2.dao;

import integrado.prog2.config.DatabaseConnectionPool;
import integrado.prog2.entities.DetallePedido;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {

    @Override
    public void crear(Pedido pedido) {
        // 1. Nos aseguramos de calcular el total del pedido antes de guardarlo
        pedido.calcularTotal();

        String sqlPedido = "INSERT INTO pedidos (fecha, total, estado, forma_pago, id_usuario) VALUES (?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalles_pedido (cantidad, subtotal, id_pedido, id_producto) VALUES (?, ?, ?, ?)";
        String sqlStock = "UPDATE productos SET stock = stock - ? WHERE id = ? AND stock >= ?";

        Connection conn = null;
        try {
            conn = DatabaseConnectionPool.getConnection();
            // ¡REQUISITO TPI! Desactivamos el auto-commit para manejar la transacción manualmente
            conn.setAutoCommit(false);

            // 2. Insertar la cabecera del Pedido
            try (PreparedStatement pstmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                pstmtPedido.setDate(1, Date.valueOf(pedido.getFecha()));
                pstmtPedido.setDouble(2, pedido.getTotal());
                pstmtPedido.setString(3, pedido.getEstado().name());
                pstmtPedido.setString(4, pedido.getFormaPago().name());

                if (pedido.getUsuario() != null) {
                    pstmtPedido.setLong(5, pedido.getUsuario().getId());
                } else {
                    throw new SQLException("El pedido debe tener un usuario asociado.");
                }

                pstmtPedido.executeUpdate();

                // Recuperamos el ID autogenerado del pedido
                try (ResultSet rs = pstmtPedido.getGeneratedKeys()) {
                    if (rs.next()) {
                        pedido.setId(rs.getLong(1));
                    }
                }
            }

            // 3. Insertar los Detalles del pedido y actualizar stock
            try (PreparedStatement pstmtDetalle = conn.prepareStatement(sqlDetalle);
                 PreparedStatement pstmtStock = conn.prepareStatement(sqlStock)) {

                for (DetallePedido detalle : pedido.getDetallesPedidos()) {

                    // Validamos stock en la base de datos antes de vender
                    pstmtStock.setInt(1, detalle.getCantidad());
                    pstmtStock.setLong(2, detalle.getProducto().getId());
                    pstmtStock.setInt(3, detalle.getCantidad()); // Condición: stock >= cantidad

                    int filasStock = pstmtStock.executeUpdate();
                    if (filasStock == 0) {
                        // Si no afectó filas, es porque el producto no tiene stock suficiente
                        throw new SQLException("Stock insuficiente para el producto: " + detalle.getProducto().getNombre());
                    }

                    // Si hay stock, insertamos el detalle
                    pstmtDetalle.setInt(1, detalle.getCantidad());
                    pstmtDetalle.setDouble(2, detalle.getSubtotal());
                    pstmtDetalle.setLong(3, pedido.getId()); // El ID que recuperamos arriba
                    pstmtDetalle.setLong(4, detalle.getProducto().getId());

                    pstmtDetalle.executeUpdate();
                }
            }

            // Si todo salió bien hasta acá, confirmamos los cambios en la BD
            conn.commit();
            System.out.println("Pedido y detalles guardados con éxito. Transacción completada. ID Pedido: " + pedido.getId());

        } catch (SQLException e) {
            // ¡REQUISITO TPI! Si algo falló (ej. falta de stock), deshacemos todo lo que se llegó a tocar
            if (conn != null) {
                try {
                    System.err.println("Error en la transacción. Aplicando Rollback...");
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Error al procesar el pedido de manera transaccional: " + e.getMessage());
        } finally {
            // Siempre cerramos la conexión principal de forma segura al terminar
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restauramos el comportamiento por defecto del pool
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Pedido leer(Long id) {

        String sql = """
        SELECT id, fecha, estado, total, forma_pago, id_usuario
        FROM pedidos
        WHERE id = ? AND eliminado = false
        """;

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    Usuario usuario = new UsuarioDAOImpl().leer(rs.getLong("id_usuario"));

                    Pedido pedido = new Pedido(
                            FormaPago.valueOf(rs.getString("forma_pago")),
                            usuario
                    );

                    pedido.setId(rs.getLong("id"));
                    pedido.setFecha(rs.getDate("fecha").toLocalDate());
                    pedido.setEstado(Estado.valueOf(rs.getString("estado")));
                    pedido.setTotal(rs.getDouble("total"));

                    return pedido;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al leer pedido", e);
        }

        return null;
    }

    @Override
    public void actualizarEstadoFormaPago(Pedido pedido) {
        String sql = "UPDATE pedidos SET estado = ?, forma_pago = ? WHERE id = ? AND eliminado = false";
        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pedido.getEstado().name());
            pstmt.setString(2, pedido.getFormaPago().name());
            pstmt.setLong(3, pedido.getId());

            pstmt.executeUpdate();
            System.out.println("Estado/Forma de pago actualizados con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el pedido: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "UPDATE pedidos SET eliminado = true WHERE id = ?";
        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            System.out.println("Pedido dado de baja lógicamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar lógicamente el pedido: " + e.getMessage());
        }
    }

    @Override
    public List<Pedido> listarTodos() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = """
        SELECT id, fecha, estado, total, forma_pago, id_usuario
        FROM pedidos
        WHERE eliminado = false
        """;

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

            while (rs.next()) {

                Long idUsuario = rs.getLong("id_usuario");
                Usuario usuario = usuarioDAO.leer(idUsuario);

                if (usuario == null) continue;

                Pedido pedido = new Pedido(
                        FormaPago.valueOf(rs.getString("forma_pago")),
                        usuario

                );

                pedido.setId(rs.getLong("id"));
                pedido.setFecha(rs.getDate("fecha").toLocalDate());
                pedido.setEstado(Estado.valueOf(rs.getString("estado")));
                pedido.setTotal(rs.getDouble("total"));

                pedidos.add(pedido);
                List<DetallePedido> detalles = listarDetallesPorPedido(pedido.getId());
                pedido.getDetallesPedidos().addAll(detalles);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar pedidos: " + e.getMessage());
        }

        return pedidos;
    }
    private List<DetallePedido> listarDetallesPorPedido(Long idPedido) {

        List<DetallePedido> detalles = new ArrayList<>();

        String sql = """
        SELECT cantidad, subtotal, id_producto
        FROM detalles_pedido
        WHERE id_pedido = ?
    """;

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, idPedido);

            try (ResultSet rs = pstmt.executeQuery()) {

                ProductoDAO productoDAO = new ProductoDAOImpl();

                while (rs.next()) {

                    Producto producto = productoDAO.leer(rs.getLong("id_producto"));

                    DetallePedido detalle = new DetallePedido(
                            rs.getInt("cantidad"),
                            producto
                    );

                    detalle.setSubtotal(rs.getDouble("subtotal"));

                    detalles.add(detalle);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar detalles", e);
        }

        return detalles;
    }
    }