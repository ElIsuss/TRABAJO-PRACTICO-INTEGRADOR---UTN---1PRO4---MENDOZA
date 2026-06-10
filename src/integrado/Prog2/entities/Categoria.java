package integrado.Prog2.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Categoria extends Base {
    private String nombre;
    private String descipcion;
    private List<Producto> productos = new ArrayList<>();

    public Categoria (String nombre, String descipcion){
        super();
        setNombre(nombre);
        setDescipcion(descipcion);
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

    public void setDescipcion(String descipcion) {                             //CONSULTAR
        if (descipcion != null && !descipcion.trim().isEmpty()){
            this.descipcion = descipcion;
        } else {
            System.out.println("NO SE PUDO DEFINIR DESCRIPCION...");
            this.descipcion = "DESCIPCION DE CATEGORIA INVALIDA";
        }
    }


    //GETTERS
    public String getNombre() {
        return nombre;
    }

    public String getDescipcion() {
        return descipcion;
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
                "\nDescipcion: " + descipcion +
                "\nProductos: " + productos.size() +
                "\n";

    }
}
