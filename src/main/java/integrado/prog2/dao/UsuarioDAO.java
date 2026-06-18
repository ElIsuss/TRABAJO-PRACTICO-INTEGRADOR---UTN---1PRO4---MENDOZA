package integrado.prog2.dao;

import integrado.prog2.entities.Usuario;
import java.util.List;

public interface UsuarioDAO {
    void crear(Usuario usuario);
    Usuario leer(Long id);
    void actualizar(Usuario usuario);
    void eliminar(Long id); // Baja lógica (eliminado = true)
    List<Usuario> listar();
    boolean existeMail(String mail); // Para cumplir la regla del mail único
}