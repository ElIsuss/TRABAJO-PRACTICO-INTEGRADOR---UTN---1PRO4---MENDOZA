package integrado.prog2.dao;

import integrado.prog2.config.DatabaseConnectionPool;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public void crear(Usuario usuario) {
        // Validación del criterio de aceptación: mail único
        if (existeMail(usuario.getMail())) {
            throw new RuntimeException("Error: El e-mail '" + usuario.getMail() + "' ya está registrado.");
        }

        String sql = "INSERT INTO usuarios (nombre, apellido, mail, celular, contrasenia, rol) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getMail());
            pstmt.setString(4, usuario.getCelular());
            pstmt.setString(5, usuario.getContrasenia());
            pstmt.setString(6, usuario.getRol().name()); // Guardamos el ENUM como String (ADMIN o USUARIO)

            pstmt.executeUpdate();

            // Sincronizamos el ID autogenerado gracias a que pusimos la variable como 'protected' en Base
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getLong(1));
                }
            }

            System.out.println("Usuario registrado con éxito. ID: " + usuario.getId());

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el usuario: " + e.getMessage());
        }
    }

    @Override
    public Usuario leer(Long id) {
        String sql = "SELECT id, nombre, apellido, mail, celular, contrasenia, rol FROM usuarios WHERE id = ? AND eliminado = false";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario(
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("mail"),
                            rs.getString("celular"),
                            rs.getString("contrasenia"),
                            Rol.valueOf(rs.getString("rol")) // Convertimos el String de la BD de vuelta al ENUM
                    );
                    usuario.setId(rs.getLong("id"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al leer el usuario con ID " + id + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, mail = ?, celular = ?, contrasenia = ?, rol = ? WHERE id = ? AND eliminado = false";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getMail());
            pstmt.setString(4, usuario.getCelular());
            pstmt.setString(5, usuario.getContrasenia());
            pstmt.setString(6, usuario.getRol().name());
            pstmt.setLong(7, usuario.getId());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                System.out.println("No se pudo actualizar. El usuario no existe o fue eliminado.");
            } else {
                System.out.println("Usuario actualizado exitosamente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Long id) {
        // BAJA LÓGICA: Marcamos eliminado = true en lugar de hacer un DELETE físico
        String sql = "UPDATE usuarios SET eliminado = true WHERE id = ?";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                System.out.println("No se encontró el usuario con ID: " + id);
            } else {
                System.out.println("Usuario eliminado (lógicamente) con éxito.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar lógicamente el usuario: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, apellido, mail, celular, contrasenia, rol FROM usuarios WHERE eliminado = false";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("mail"),
                        rs.getString("celular"),
                        rs.getString("contrasenia"),
                        Rol.valueOf(rs.getString("rol"))
                );
                usuario.setId(rs.getLong("id"));
                lista.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar los usuarios: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean existeMail(String mail) {
        String sql = "SELECT 1 FROM usuarios WHERE mail = ? AND eliminado = false";
        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mail);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Si devuelve true, es porque ya existe una fila con ese mail
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al verificar el e-mail: " + e.getMessage());
        }
    }
}