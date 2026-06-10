package integrado.Prog2.entities;

import java.util.Objects;

public class Producto extends Base {
    private String nombre;
    private Double precio;
    private String descipcion;
    private Integer stock;
    private String imagen;
    private Boolean disponible;
    private Categoria categoria;

    public Producto (String nombre, Double precio,String descipcion, Integer stock, String imagen, Categoria categoria){
        super();
        setNombre(nombre);
        setPrecio(precio);
        setDescipcion(descipcion);
        setStock(stock);
        setImagen(imagen);
        validarDisponibilidad();
        setCategoria(categoria);
    }




    //METODOS
    private void validarDisponibilidad(){
        if (stock > 0){
            setDisponible(true);
        } else {
            setDisponible(false);
        }
    }

    private void reducirStock(Integer venta){
        Integer aux = 0;
        aux = stock - venta;
        setStock(aux);
        validarDisponibilidad();
    }

    public Boolean validarVenta(Integer venta){

        if(venta == null || venta <= 0){
            return false;
        }

        if(stock >= venta){
            reducirStock(venta);
            return true;
        }

        return false;
    }


    //SETTERS
    public void setNombre(String nombre) {                          //CONSULTAR
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        } else {
            System.out.println("NOMBRE DEL PRODUCTO INVALIDO");
            this.nombre = "NOMBRE INVALIDO";
        }
    }

    public void setPrecio(Double precio) {                                 //CONSULTAR
        if (precio != null && precio > 0) {
            this.precio = precio;
        } else {
            System.out.println("PRECIO INVALIDO");
            this.precio = 0.0;
        }
    }

    public void setDescipcion(String descipcion) {                       //CONSULTAR
        if (descipcion != null && !descipcion.trim().isEmpty()) {
            this.descipcion = descipcion;
        } else {
            System.out.println("DESCRIPCION INVALIDA");
            this.descipcion = "SIN DESCRIPCION";
        }
    }

    public void setStock(Integer stock) {                                //CONSULTAR
        if (stock != null && stock >= 0) {
            this.stock = stock;
        } else {
            System.out.println("STOCK INVALIDO");
            this.stock = 0;
        }
    }

    public void setImagen(String imagen) {                              //CONSULTAR
        if (imagen != null && !imagen.trim().isEmpty()) {
            this.imagen = imagen;
        } else {
            System.out.println("IMAGEN INVALIDA");
            this.imagen = "SIN IMAGEN";
        }
    }

    public void setDisponible(Boolean disponible) {                     //CONSULTAR
        if (disponible != null) {
            this.disponible = disponible;
        } else {
            System.out.println("DISPONIBILIDAD INVALIDA");
            this.disponible = false;
        }
    }

    public void setCategoria(Categoria categoria) {                  //CONSULTAR
        if (categoria != null) {
            this.categoria = categoria;
            categoria.addProducto(this);
        } else {
            System.out.println("CATEGORIA INVALIDA");
            this.categoria = null;
        }
    }


    //GETTERS
    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public Integer getStock() {
        return stock;
    }

    public String getImagen() {
        return imagen;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(nombre, producto.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }

    @Override
    public String toString() {
        return "\n================================" +
                "\nProducto: " + nombre +
                "\nId: " + getId() +
                "\nPrecio: " + precio +
                "\nDescipcion: " + descipcion +
                "\nStock: " + stock +
                "\nImagen: " + imagen +
                "\nDisponible: " + disponible +
                "\nCategoria: " + categoria.getNombre() +
                "\n================================";

    }
}