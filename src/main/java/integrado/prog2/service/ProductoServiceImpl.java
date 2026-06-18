package integrado.prog2.service;

import integrado.prog2.dao.ProductoDAO;
import integrado.prog2.dao.ProductoDAOImpl;
import integrado.prog2.entities.Producto;
import java.util.List;

public class ProductoServiceImpl implements ProductoService {

    private final ProductoDAO productoDAO;

    public ProductoServiceImpl() {
        this.productoDAO = new ProductoDAOImpl();
    }

    @Override
    public void guardarProducto(Producto producto) {
        // Reglas de Negocio básicas
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del producto no puede estar vacío.");
        }
        if (producto.getPrecio() == null || producto.getPrecio() < 0) {
            throw new RuntimeException("El precio del producto no puede ser negativo.");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new RuntimeException("El stock inicial no puede ser negativo.");
        }
        if (producto.getCategoria() == null) {
            throw new RuntimeException("El producto debe estar asociado obligatoriamente a una categoría.");
        }

        productoDAO.crear(producto);
    }

    @Override
    public Producto buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("El ID provisto no es válido.");
        }
        Producto producto = productoDAO.leer(id);
        if (producto == null) {
            throw new RuntimeException("No se encontró ningún producto activo con el ID: " + id);
        }
        return producto;
    }

    @Override
    public void modificarProducto(Producto producto) {
        if (producto.getId() == null) {
            throw new RuntimeException("No se puede modificar un producto que no tenga ID.");
        }
        // Validamos que exista previamente y esté activo antes de actualizar
        buscarPorId(producto.getId());

        if (producto.getPrecio() == null || producto.getPrecio() < 0) {
            throw new RuntimeException("El precio modificado no puede ser negativo.");
        }

        productoDAO.actualizar(producto);
    }

    @Override
    public void darDeBaja(Long id) {
        // Validamos existencia antes de hacer el borrado lógico
        buscarPorId(id);
        productoDAO.eliminar(id);
    }

    @Override
    public List<Producto> listarTodos() {
        return productoDAO.listar();
    }
}