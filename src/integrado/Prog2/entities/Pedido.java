package integrado.Prog2.entities;

import integrado.Prog2.enums.Estado;
import integrado.Prog2.enums.FormaPago;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;




//MEJORAR EL TO STRING




public class Pedido extends Base {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido>detallePedidos = new ArrayList<>();

    public Pedido(FormaPago formaPago, Usuario usuario){
        super();
        setFecha(LocalDate.now());
        setEstado(Estado.CANCELADO);            /////////////REVISAR
        setTotal(0.0);
        setFormaPago(formaPago);
        setUsuario(usuario);
        if(this.usuario != null){
            this.usuario.addPedido(this);
        }
    }


    //METODOS
    public void addDetallePedido(Integer cantidad, Producto producto){

        if (findDetallePedidoByProducto(producto) != null){
            System.out.println("NO SE PERMITEN PRODUCTOS DUPLICADOS");
            return;
        }


        DetallePedido detallePedidoCreado = new DetallePedido(cantidad,producto);


        if (detallePedidoCreado.getValido()){
            detallePedidos.add(detallePedidoCreado);
            validarPedido();

        } else {
            System.out.println("NO SE PUDO AÑADIR EL ULTIMO DETALLE AL PEDIDO...");
        }
    }

    public void calcularTotal(){
        Double aux = 0.0;
        for (DetallePedido detallePedidoAux: detallePedidos){
            aux += detallePedidoAux.getSubtotal();
        }
        setTotal(aux);
    }

    public void validarPedido(){
        if (detallePedidos.isEmpty()){
            setEstado(Estado.CANCELADO);         ////////////REVISAR
            setTotal(0.0);
        } else {
            setEstado(Estado.PENDIENTE);        ///////////////REVISAR
            calcularTotal();
        }
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto){

        for(DetallePedido detalle : detallePedidos){

            if(detalle.getProducto().equals(producto)){
                return detalle;
            }
        }
        return null;
    }


    public void deleteDetallePedidoByProducto(Producto producto){

        DetallePedido detalle = findDetallePedidoByProducto(producto);

        if(detalle != null){
            detallePedidos.remove(detalle);
            validarPedido();
            calcularTotal();
        }else{
            System.out.println("No existe un detalle para ese producto");
        }
    }


    //SETTERAS
    public void setFecha(LocalDate fecha) {         //CONSULTAR
        if(fecha != null){
            this.fecha = fecha;
        } else {
            System.out.println("FECHA INVALIDA");
            this.fecha = LocalDate.now();
        }
    }

    public void setEstado(Estado estado) {        //CONSULTAR
        if(estado != null){
            this.estado = estado;
        } else {
            System.out.println("ESTADO INVALIDO");
            this.estado = Estado.PENDIENTE;
        }
    }

    public void setTotal(Double total) {           //CONSULTAR
        if(total != null && total >= 0){
            this.total = total;
        } else {
            System.out.println("TOTAL INVALIDO");
            this.total = 0.0;
        }
    }

    public void setFormaPago(FormaPago formaPago) {       //CONSULTAR
        if(formaPago != null){
            this.formaPago = formaPago;
        } else {
            System.out.println("FORMA DE PAGO INVALIDA, DEFINIENDO EFECTIVO COMO PREDETERMINADO");
            this.formaPago = FormaPago.EFECTIVO;
        }
    }

    public void setUsuario(Usuario usuario) {        //CONSULTAR
        if(usuario != null){
            this.usuario = usuario;

        } else {
            System.out.println("USUARIO INVALIDO");
        }
    }

    //GETTERs
    public LocalDate getFecha() {
        return fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public Double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public List<DetallePedido> getDetallesPedidos() {
        return detallePedidos;
    }

    public List<DetallePedido> getDetallesPedido() {
        return detallePedidos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "Pedido # " + getId() +  "          Fecha:" + fecha +
                "\n================================" +
                "\nUsuario: " + usuario.getNombre() +
                "\nEstado: " + estado +
                "\nDetalle pedido: " + detallePedidos +
                "\n================================" +
                "\nForma de pago: " + formaPago +
                "\nTotal: " + total;

    }
}