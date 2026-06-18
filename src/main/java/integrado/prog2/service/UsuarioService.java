package integrado.prog2.service;

import integrado.prog2.entities.Usuario;
import java.util.List;

public interface UsuarioService {
    void registrarUsuario(Usuario usuario);
    Usuario buscarPorId(Long id);
    void modificarUsuario(Usuario usuario);
    void darDeBaja(Long id);
    List<Usuario> listarTodos();
}