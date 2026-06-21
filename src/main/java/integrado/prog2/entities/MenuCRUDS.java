package integrado.prog2.entities;

import integrado.prog2.dao.*;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.enums.Rol;

import java.util.List;
import java.util.Scanner;

public class MenuCRUDS {

    private final Scanner sc = new Scanner(System.in);

    private final CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
    private final ProductoDAO productoDAO = new ProductoDAOImpl();
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    private final PedidoDAO pedidoDAO = new PedidoDAOImpl();

    //LE ASIGBAMOS CON LO QUE VAMOS A TRABAJAR
    private String obtenerNombreModulo(int modulo) {
        return switch (modulo) {
            case 1 -> "CATEGORÍAS";
            case 2 -> "PRODUCTOS";
            case 3 -> "USUARIOS";
            case 4 -> "PEDIDOS";
            default -> "";
        };
    }

    //MENU PARA MOSTRAR LOS TRABAJOS QUE VAMOS A HACER
    public void mostrar(int modulo) {

        int opcion;

        do {
            System.out.println("\n=== " + obtenerNombreModulo(modulo) + " ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1 -> listar(modulo);
                case 2 -> crear(modulo);
                case 3 -> editar(modulo);
                case 4 -> eliminar(modulo);
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }


    // ===LISTAR===
    private void listar(int modulo) {

        switch (modulo) {

            case 1 -> {
                System.out.println("\n--- CATEGORÍAS ---");
                for (Categoria c : categoriaDAO.listar()) {
                    System.out.println( c.getId() + " - " + c.getNombre());
                }
            }

            case 2 -> {
                System.out.println("\n--- PRODUCTOS ---");
                for (Producto p : productoDAO.listar()) {
                    System.out.println(p.getId() + " - " + p.getNombre() + " - $" + p.getPrecio());
                }
            }

            case 3 -> {
                System.out.println("\n--- USUARIOS ---");
                for (Usuario u : usuarioDAO.listar()) {
                    System.out.println(u.getId() + " - " + u.getNombre() + " " + u.getApellido());
                }
            }
            case 4 -> {
                System.out.println("\n--- PEDIDOS ---");

                List<Pedido> pedidos = pedidoDAO.listarTodos();

                if (pedidos.isEmpty()) {
                    System.out.println("No hay pedidos registrados.");
                } else {
                    for (Pedido pedido : pedidos) {
                        System.out.println(pedido);
                        System.out.println("--------------------------------");
                    }
                }
            }
        }
    }

    // ==== CREAR ===
    private void crear(int modulo) {

        switch (modulo) {

            case 1 -> {
                System.out.println("\n--- CREAR CATEGORÍA ---");

                //ponemos nombre y descripcion
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();

                System.out.print("Descripción: ");
                String descripcion = sc.nextLine();

                //creamos categoria
                Categoria c = new Categoria(nombre,descripcion);

                categoriaDAO.crear(c);

                System.out.println("Categoría creada con ID: " + c.getId());
            }

            case 2 -> {
                System.out.println("\n--- CREAR PRODUCTO ---");


                //ponemos los valores
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();

                System.out.print("Precio: ");
                double precio = Double.parseDouble(sc.nextLine());

                System.out.print("Descripción: ");
                String descripcion = sc.nextLine();

                System.out.print("Stock: ");
                int stock = Integer.parseInt(sc.nextLine());

                System.out.print("Imagen: ");
                String imagen = sc.next();

                System.out.print("ID de categoría: ");
                Long idCat = Long.parseLong(sc.nextLine());

                Categoria categoria = categoriaDAO.leer(idCat);

                //si la categoria no existe no creamos el producto para evitar errores
                if (categoria == null) {
                    System.out.println("Categoría no existe");
                    return;
                }

                Producto p = new Producto(nombre, precio, descripcion, stock, imagen, categoria);

                productoDAO.crear(p);

                System.out.println("Producto creado con ID: " + p.getId());
            }

            case 3 -> {
                System.out.println("\n--- CREAR USUARIO ---");


                //parametros
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();

                System.out.print("Apellido: ");
                String apellido = sc.nextLine();

                System.out.print("Email: ");
                String mail = sc.nextLine();

                System.out.print("Celular: ");
                String celular = sc.next();

                System.out.print("Contraseña: ");
                String pass = sc.next();

                System.out.print("Rol (ADMIN / USUARIO): ");
                String rolStr = sc.nextLine();

                Rol rol;

                //si el rol es inavlido no creamos el usuario
                try {
                    rol = Rol.valueOf(rolStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Rol inválido. Se asigna USUARIO por defecto.");
                    rol = Rol.USUARIO;
                }

                Usuario u = new Usuario(nombre, apellido, mail, celular, pass, rol);

                usuarioDAO.crear(u);

                System.out.println("Usuario creado con ID: " + u.getId());
            }

            case 4 -> {
                System.out.println("\n--- CREAR PEDIDO ---");

                System.out.print("ID Usuario: ");
                Long idUsuario = sc.nextLong();

                Usuario usuario = usuarioDAO.leer(idUsuario);

                if (usuario == null) {
                    System.out.println("Usuario no encontrado.");
                    return;
                }

                System.out.print("Forma de pago (EFECTIVO / TARJETA / TRANSFERENCIA): ");
                String formaPagoStr = sc.nextLine();

                FormaPago formaPago;

                try {
                    formaPago = FormaPago.valueOf(formaPagoStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Forma de pago inválida.");
                    return;
                }

                Pedido pedido = new Pedido(formaPago, usuario);

                // =========================
                // AGREGAR PRODUCTOS AL PEDIDO
                // =========================

                while (true) {

                    System.out.print("\nID Producto (0 para terminar): ");
                    Long idProducto = Long.parseLong(sc.nextLine());

                    if (idProducto == 0) break;

                    Producto producto = productoDAO.leer(idProducto);

                    if (producto == null) {
                        System.out.println("Producto no encontrado.");
                        continue;
                    }

                    System.out.print("Cantidad: ");
                    int cantidad = Integer.parseInt(sc.nextLine());

                    try {
                        pedido.addDetallePedido(cantidad, producto);
                        System.out.println("Producto agregado al pedido.");
                    } catch (Exception e) {
                        System.out.println("Error al agregar producto: " + e.getMessage());
                    }
                }

                // =========================
                // GUARDAR PEDIDO
                // =========================

                pedidoDAO.crear(pedido);

                System.out.println("Pedido creado con ID: " + pedido.getId());
            }
        }
    }

    // ==== EDITAR ====
    private void editar(int modulo) {

        switch (modulo) {

            case 1 -> {
                System.out.print("ID categoría: ");
                Long id = Long.parseLong(sc.nextLine());

                Categoria c = categoriaDAO.leer(id);

                if (c != null) {
                    System.out.print("Nuevo nombre: ");
                    c.setNombre(sc.next());

                    System.out.print("Nueva descripción: ");
                    c.setDescripcion(sc.next());

                    categoriaDAO.actualizar(c);
                    System.out.println("Actualizada.");
                } else {
                    System.out.println("No existe.");
                }
            }

            case 2 -> {
                System.out.print("ID producto: ");
                Long id = Long.parseLong(sc.nextLine());

                Producto p = productoDAO.leer(id);

                if (p != null) {
                    System.out.print("Nuevo nombre: ");
                    p.setNombre(sc.next());

                    System.out.print("Nuevo precio: ");
                    p.setPrecio(sc.nextDouble());

                    productoDAO.actualizar(p);
                    System.out.println("Actualizado.");
                } else {
                    System.out.println("No existe.");
                }
            }

            case 3 -> {
                System.out.print("ID usuario: ");
                Long id = Long.parseLong(sc.nextLine());

                Usuario u = usuarioDAO.leer(id);

                if (u != null) {
                    System.out.print("Nuevo nombre: ");
                    u.setNombre(sc.next());

                    System.out.print("Nuevo apellido: ");
                    u.setApellido(sc.next());

                    usuarioDAO.actualizar(u);
                    System.out.println("Actualizado.");
                } else {
                    System.out.println("No existe.");
                }
            }

            case 4 -> {
                System.out.print("ID pedido: ");
                Long id = Long.parseLong(sc.nextLine());

                Pedido pedido = pedidoDAO.leer(id);

                if (pedido == null) {
                    System.out.println("Pedido no encontrado.");
                    return;
                }

                System.out.print("Nuevo estado (PENDIENTE / CONFIRMADO / TERMINADO / CANCELADO): ");
                Estado estado = Estado.valueOf(sc.next().toUpperCase());

                System.out.print("Nueva forma de pago (EFECTIVO / TARJETA / TRANSFERENCIA): ");
                FormaPago formaPago = FormaPago.valueOf(sc.next().toUpperCase());

                pedido.setEstado(estado);
                pedido.setFormaPago(formaPago);

                pedidoDAO.actualizarEstadoFormaPago(pedido);

                System.out.println("Pedido actualizado.");
            }
        }
    }

    // ==== ELIMINAR ====
    private void eliminar(int modulo) {

        System.out.print("ID a eliminar: ");
        Long id = Long.parseLong(sc.nextLine());

        switch (modulo) {

            case 1 -> categoriaDAO.eliminar(id);
            case 2 -> productoDAO.eliminar(id);
            case 3 -> usuarioDAO.eliminar(id);
            case 4 -> pedidoDAO.eliminar(id);
        }

        System.out.println("Eliminación realizada.");
    }
}