package integrado.prog2.dao;

import integrado.prog2.entities.Producto;
import java.util.List;

public interface ProductoDAO {
    void crear(Producto producto);
    Producto leer(Long id);
    void actualizar(Producto producto);
    void eliminar(Long id);
    List<Producto> listar();
}