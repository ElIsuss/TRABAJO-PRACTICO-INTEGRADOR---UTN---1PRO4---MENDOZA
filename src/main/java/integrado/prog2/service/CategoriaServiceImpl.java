package integrado.prog2.service;

import integrado.prog2.dao.CategoriaDAO;
import integrado.prog2.dao.CategoriaDAOImpl;
import integrado.prog2.entities.Categoria;
import java.util.List;

public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaDAO categoriaDAO;

    public CategoriaServiceImpl() {
        this.categoriaDAO = new CategoriaDAOImpl();
    }

    @Override
    public void guardarCategoria(Categoria categoria) {
        // 1. Validar que el nombre no esté vacío
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre de la categoría es obligatorio.");
        }

        // 2. Validar que el nombre no exista ya (unicidad)
        if (categoriaDAO.existeNombre(categoria.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con el nombre '" + categoria.getNombre() + "'.");
        }

        // 3. Delegar la creación al DAO
        categoriaDAO.crear(categoria);
    }

    @Override
    public Categoria buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("El ID provisto no es válido.");
        }

        Categoria categoria = categoriaDAO.leer(id);
        if (categoria == null) {
            throw new RuntimeException("No se encontró ninguna categoría activa con el ID: " + id);
        }
        return categoria;
    }

    @Override
    public void modificarCategoria(Categoria categoria) {
        if (categoria.getId() == null) {
            throw new RuntimeException("No se puede modificar una categoría sin su ID.");
        }

        // 1. Verificar que la categoría exista (y esté activa)
        Categoria existente = buscarPorId(categoria.getId());

        // 2. Validar que el nuevo nombre no esté vacío
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre de la categoría no puede quedar vacío.");
        }

        // 3. Si el nombre cambió, verificar que no exista otra categoría con ese nombre
        if (!existente.getNombre().equalsIgnoreCase(categoria.getNombre()) 
                && categoriaDAO.existeNombre(categoria.getNombre())) {
            throw new RuntimeException("Ya existe otra categoría con el nombre '" + categoria.getNombre() + "'.");
        }

        // 4. Delegar la actualización al DAO
        categoriaDAO.actualizar(categoria);
    }

    @Override
    public void darDeBaja(Long id) {
        // Verificar que la categoría existe antes de eliminarla
        buscarPorId(id);

        // Si la categoría tiene productos activos, podrías agregar una validación aquí.
        // Por ejemplo, si ProductoDAO tiene un método contarPorCategoria(id) > 0, 
        // lanzarías una excepción. Pero eso es opcional según la regla de negocio.
        
        categoriaDAO.eliminar(id);
    }

    @Override
    public List<Categoria> listarTodos() {
        return categoriaDAO.listar();
    }
}