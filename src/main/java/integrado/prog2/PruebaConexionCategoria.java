package integrado.prog2;

import integrado.prog2.dao.CategoriaDAO;
import integrado.prog2.dao.CategoriaDAOImpl;
import integrado.prog2.entities.Categoria;



public class PruebaConexionCategoria {
    public static void main(String[] args) {
        CategoriaDAO dao = new CategoriaDAOImpl();
        
        // Crear
        Categoria cat = new Categoria("Lácteos", "Leches, quesos, yogures y derivados");
        dao.crear(cat);
        System.out.println("Categoría creada con ID: " + cat.getId());
        System.out.println("---LISTAR---");
        // Listar
        dao.listar().forEach(System.out::println);
        System.out.println("---ACTUALIZAR---");
        // Actualizar
        cat.setNombre("LACTEOS(actualizado)");
        dao.actualizar(cat);
        dao.listar().forEach(System.out::println);
        System.out.println("---ELIMINANDO---");
        // Eliminar (soft)
        dao.eliminar(cat.getId());
        System.out.println("---CATEGORIA ELIMINADA DE LA BASE---");
        // Listar nuevamente (no debe aparecer la categoria creada en la base)
        dao.listar().forEach(System.out::println);
    }
}