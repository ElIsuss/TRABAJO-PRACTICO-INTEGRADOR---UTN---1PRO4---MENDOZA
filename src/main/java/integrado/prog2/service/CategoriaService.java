package integrado.prog2.service;

import integrado.prog2.entities.Categoria;
import java.util.List;

public interface CategoriaService {    
    void guardarCategoria(Categoria categoria);    
    Categoria buscarPorId(Long id);    
    void modificarCategoria(Categoria categoria);    
    void darDeBaja(Long id);    
    List<Categoria> listarTodos();
}