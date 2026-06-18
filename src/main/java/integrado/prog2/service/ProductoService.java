package integrado.prog2.service;

import integrado.prog2.entities.Producto;
import java.util.List;

public interface ProductoService {
    void guardarProducto(Producto producto);
    Producto buscarPorId(Long id);
    void modificarProducto(Producto producto);
    void darDeBaja(Long id);
    List<Producto> listarTodos();
}