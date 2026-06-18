package integrado.prog2.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Categoria extends Base {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<Producto> productos = new ArrayList<>();

    public Categoria() {}
    
    public Categoria (String nombre, String descripcion){
        super();
        setNombre(nombre);
        setDescripcion(descripcion);
    }

    public void addProducto(Producto p){
        productos.add(p);
    }



    //SETTERS
    public void setNombre(String nombre) {                                //CONSULTAR
        if (nombre != null && !nombre.trim().isEmpty()){
            this.nombre = nombre;
        } else {
            System.out.println("NO SE PUDO DEFINIR NOMBRE...");
            this.nombre = "NOMBRE DE CATEGORIA INVALIDO";
        }

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {                             //CONSULTAR
        if (descripcion != null && !descripcion.trim().isEmpty()){
            this.descripcion = descripcion;
        } else {
            System.out.println("NO SE PUDO DEFINIR DESCRIPCION...");
            this.descripcion = "DESCIPCION DE CATEGORIA INVALIDA";
        }
    }
    
    //GETTERS
    public String getNombre() {
        return nombre;
    }
    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Producto> getProducto() {
        return productos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(nombre, categoria.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }

    @Override
    public String toString() {
        return "Categoria: " + nombre +
                "\nDescipcion: " + descripcion +
                "\nProductos: " + productos.size() +
                "\n";

    }
}
