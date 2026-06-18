package integrado.prog2.dao;

import integrado.prog2.entities.Categoria;
import java.util.List;


public interface CategoriaDAO {
    void crear(Categoria categoria);
    Categoria leer(Long id);
    void actualizar(Categoria categoria);
    void eliminar(Long id);
    List<Categoria> listar();
    boolean existeNombre(String nombre);
}