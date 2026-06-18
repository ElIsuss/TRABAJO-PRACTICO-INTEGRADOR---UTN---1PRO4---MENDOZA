package integrado.prog2.service;

import integrado.prog2.dao.UsuarioDAO;
import integrado.prog2.dao.UsuarioDAOImpl;
import integrado.prog2.entities.Usuario;
import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    // Dependencia del DAO
    private final UsuarioDAO usuarioDAO;

    // Constructor donde inicializamos el DAO
    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    public void registrarUsuario(Usuario usuario) {
        // Regla de Negocio: Validar campos obligatorios antes de llamar al DAO
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del usuario es obligatorio.");
        }
        if (usuario.getMail() == null || usuario.getMail().trim().isEmpty()) {
            throw new RuntimeException("El e-mail es obligatorio.");
        }

        // El control de duplicado de mail ya lo hace el DAO adentro de crear()
        usuarioDAO.crear(usuario);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("El ID provisto no es válido.");
        }
        Usuario usuario = usuarioDAO.leer(id);
        if (usuario == null) {
            throw new RuntimeException("No se encontró ningún usuario activo con el ID: " + id);
        }
        return usuario;
    }

    @Override
    public void modificarUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            throw new RuntimeException("No se puede actualizar un usuario sin su ID de base de datos.");
        }
        // Primero verificamos que exista y esté activo
        buscarPorId(usuario.getId());

        usuarioDAO.actualizar(usuario);
    }

    @Override
    public void darDeBaja(Long id) {
        // Verificamos que exista antes de intentar borrarlo lógicamente
        buscarPorId(id);
        usuarioDAO.eliminar(id);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioDAO.listar();
    }
}