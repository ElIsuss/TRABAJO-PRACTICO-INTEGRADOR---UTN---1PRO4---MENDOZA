package integrado.prog2.dao;

import integrado.prog2.config.DatabaseConnectionPool;
import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public void crear(Producto producto) {
        String sql = "INSERT INTO productos (nombre, precio, descripcion, stock, imagen, disponible, id_categoria) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn =DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setString(3, producto.getDescripcion()); // Corregido a getDescripcion()
            pstmt.setInt(4, producto.getStock());
            pstmt.setString(5, producto.getImagen());
            pstmt.setBoolean(6, producto.getDisponible());

            if (producto.getCategoria() != null) {
                pstmt.setLong(7, producto.getCategoria().getId());
            } else {
                throw new RuntimeException("Error: El producto debe estar asociado a una categoría válida.");
            }

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    producto.setId(rs.getLong(1));
                }
            }

            System.out.println("Producto guardado exitosamente en la base de datos con ID: " + producto.getId());

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el producto: " + e.getMessage());
        }
    }

    @Override
    public Producto leer(Long id) {
        String sql = "SELECT p.id, p.nombre, p.precio, p.descripcion, p.stock, p.imagen, p.disponible, " +
                "c.id AS id_cat, c.nombre AS nombre_cat, c.descripcion AS desc_cat " +
                "FROM productos p " +
                "INNER JOIN categorias c ON p.id_categoria = c.id " +
                "WHERE p.id = ? AND p.eliminado = false"; // Filtramos que no esté eliminado lógicamente

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // reconstruimos el objeto Categoria que necesita el producto
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getLong("id_cat"));
                    categoria.setNombre(rs.getString("nombre_cat"));
                    categoria.setDescripcion(rs.getString("desc_cat"));

                    // creamos el Producto pasándole los datos recuperados
                    Producto producto = new Producto(
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getString("descripcion"),
                            rs.getInt("stock"),
                            rs.getString("imagen"),
                            categoria
                    );
                    producto.setId(rs.getLong("id"));

                    return producto;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al leer el producto con ID " + id + ": " + e.getMessage());
        }
        return null; // Si no lo encuentra o está eliminado
    }

    @Override
    public void actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, descripcion = ?, stock = ?, imagen = ?, disponible = ?, id_categoria = ? WHERE id = ? AND eliminado = false";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setString(3, producto.getDescripcion());
            pstmt.setInt(4, producto.getStock());
            pstmt.setString(5, producto.getImagen());
            pstmt.setBoolean(6, producto.getDisponible());

            if (producto.getCategoria() != null) {
                pstmt.setLong(7, producto.getCategoria().getId());
            } else {
                throw new RuntimeException("Error: El producto debe estar asociado a una categoría válida.");
            }

            pstmt.setLong(8, producto.getId());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                System.out.println("No se pudo actualizar. El producto no existe o fue eliminado.");
            } else {
                System.out.println("Producto actualizado exitosamente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el producto: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Long id) {

        String sql = "UPDATE productos SET eliminado = true WHERE id = ?";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                System.out.println("No se encontró el producto con ID: " + id);
            } else {
                System.out.println("Producto eliminado (lógicamente) de manera exitosa.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar lógicamente el producto: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        // Traemos productos activos con un INNER JOIN para armar su Categoria correspondiente
        String sql = "SELECT p.id, p.nombre, p.precio, p.descripcion, p.stock, p.imagen, p.disponible, " +
                "c.id AS id_cat, c.nombre AS nombre_cat, c.descripcion AS desc_cat " +
                "FROM productos p " +
                "INNER JOIN categorias c ON p.id_categoria = c.id " +
                "WHERE p.eliminado = false";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getLong("id_cat"));
                categoria.setNombre(rs.getString("nombre_cat"));
                categoria.setDescripcion(rs.getString("desc_cat"));

                Producto producto = new Producto(
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getString("descripcion"),
                        rs.getInt("stock"),
                        rs.getString("imagen"),
                        categoria
                );
                producto.setId(rs.getLong("id"));

                lista.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar los productos: " + e.getMessage());
        }
        return lista;
    }
}