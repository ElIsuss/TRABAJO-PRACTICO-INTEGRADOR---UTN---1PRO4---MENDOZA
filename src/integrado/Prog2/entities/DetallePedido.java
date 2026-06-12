package integrado.prog2.entities;

import java.util.Objects;


//FALTA EL TO STRING


public class DetallePedido extends Base {
    private Integer cantidad;
    private Double subtotal;
    private Boolean valido;
    private Producto producto;

    public DetallePedido (Integer cantidad , Producto producto){
        super();
        setCantidad(cantidad);
        setProducto(producto);
        validar(producto);
    }


    //METODOS
    private void calcularSubtotal (){
        Double aux = 0.0;
        aux = producto.getPrecio() * cantidad;
        setSubtotal(aux);
    }

    private void validar(Producto producto) {
        if (producto != null){
            if (producto.validarVenta(cantidad)) {
                setValido(true);
                calcularSubtotal();
            } else {
                setValido(false);
                System.out.println("LA VENTA QUE INTENTA HACER ES INVALIDA, POR FALTA DE STOCK O PRODUCTO FALSO");
            }
        } else {
            System.out.println("NO SE ECONTRO PRODUCTO");
        }

    }

    //SETTERS
    public void setCantidad(Integer cantidad) {                         //CONSULTAR
        this.cantidad = cantidad;
    }

    public void setSubtotal(Double subtotal) {                        //CONSULTAR
        this.subtotal = subtotal;
    }

    public void setValido(Boolean valido) {                      //CONSULTAR
        this.valido = valido;
    }

    public void setProducto(Producto producto) {                   //CONSULTAR
        this.producto = producto;
    }


    //GETTERS
    public Integer getCantidad() {
        return cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Boolean getValido() {
        return valido;
    }

    public Producto getProducto() {
        return producto;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DetallePedido that = (DetallePedido) o;
        return Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(producto);
    }

    @Override
    public String toString() {
        return  "" +
                "\n|   DetallePedido   | " + getId() +
                "\n- Producto: " + producto.getNombre() +
                "\n- Cantidad: " + cantidad +
                "\n- Subtotal: " + subtotal +
                "";
    }
}
