package integrado.prog2.entities;

import integrado.prog2.enums.Rol;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends integrado.prog2.entities.Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;
    private Rol rol;
    private List<integrado.prog2.entities.Pedido>pedidos = new ArrayList<>();

    public Usuario (String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol){
        super();
        setNombre(nombre);
        setApellido(apellido);
        setMail(mail);
        setCelular(celular);
        setContrasenia(contrasenia);
        setRol(rol);
    }


    //METODOS
    public void addPedido(integrado.prog2.entities.Pedido pedido){
        pedidos.add(pedido);
    }

    public integrado.prog2.entities.Pedido findPedidoById(Long id){

        for(integrado.prog2.entities.Pedido pedido : pedidos){

            if(pedido.getId().equals(id)){
                return pedido;
            }

        }

        return null;
    }

    public void deletePedidoById(Long id){

        integrado.prog2.entities.Pedido pedido = findPedidoById(id);

        if(pedido != null){
            pedidos.remove(pedido);
            pedido.setUsuario(null);
        }
    }


    //SETTERS
    public void setNombre(String nombre) {                               //CONSULTAR
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {                      //CONSULTAR
        this.apellido = apellido;
    }

    public void setMail(String mail) {                                //CONSULTAR
        this.mail = mail;
    }

    public void setCelular(String celular) {                          //CONSULTAR
        this.celular = celular;
    }

    public void setContrasenia(String contrasenia) {                //CONSULTAR
        this.contrasenia = contrasenia;
    }

    public void setRol(Rol rol) {                                   //CONSULTAR
        this.rol = rol;
    }


    //GETTERS
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getMail() {
        return mail;
    }

    public String getCelular() {
        return celular;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public List<integrado.prog2.entities.Pedido> getPedidos() {
        return pedidos;
    }
}
