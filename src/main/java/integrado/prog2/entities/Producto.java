package integrado.prog2.entities;
import java.util.Objects;

public class Producto extends Base {
    private String nombre;
    private Double precio;
    private String descripcion;
    private Integer stock;
    private String imagen;
    private Boolean disponible;
    private Categoria categoria;

    public Producto (String nombre, Double precio,String descripcion, Integer stock, String imagen, Categoria categoria){
        super();
        setNombre(nombre);
        setPrecio(precio);
        setDescripcion(descripcion);
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

    public Boolean validarVenta(Integer venta) {

        if (venta == null || venta <= 0) {
            return false;
        }

        if (stock >= venta) {
            reducirStock(venta);
            return true;
        }

        return false;
    }


        //SETTERS

    public void setId(Long id) {
        this.id = id;           //Como ahora el Id de Base es protected, Producto lo puede modificar directamente así
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;

        if (categoria != null) {
            categoria.addProducto(this);
        }
    }


    //GETTERS
    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
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
                "\nDescipcion: " + descripcion +
                "\nStock: " + stock +
                "\nImagen: " + imagen +
                "\nDisponible: " + disponible +
                "\nCategoria: " + categoria.getNombre() +
                "\n================================";

    }
}