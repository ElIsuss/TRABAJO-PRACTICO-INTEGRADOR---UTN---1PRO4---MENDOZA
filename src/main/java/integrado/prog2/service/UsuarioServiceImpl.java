package integrado.prog2.service;

import integrado.prog2.dao.UsuarioDAO;
import integrado.prog2.dao.UsuarioDAOImpl;
import integrado.prog2.entities.Usuario;
import integrado.prog2.exception.EntityNotFoundException; // Importamos tus excepciones
import integrado.prog2.exception.ValidationException;
import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    public void registrarUsuario(Usuario usuario) {
        // Regla de Negocio: Validar campos obligatorios antes de llamar al DAO
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new ValidationException("El nombre del usuario es obligatorio.");
        }
        if (usuario.getMail() == null || usuario.getMail().trim().isEmpty()) {
            throw new ValidationException("El e-mail es obligatorio.");
        }

        usuarioDAO.crear(usuario);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("El ID provisto no es válido.");
        }
        Usuario usuario = usuarioDAO.leer(id);
        if (usuario == null) {
            throw new EntityNotFoundException("No se encontró ningún usuario activo con el ID: " + id);
        }
        return usuario;
    }

    @Override
    public void modificarUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            throw new ValidationException("No se puede actualizar un usuario sin su ID de base de datos.");
        }
        buscarPorId(usuario.getId());

        usuarioDAO.actualizar(usuario);
    }

    @Override
    public void darDeBaja(Long id) {
        buscarPorId(id);
        usuarioDAO.eliminar(id);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioDAO.listar();
    }
}