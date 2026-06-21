package integrado.prog2.entities;

import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.enums.Rol;
import integrado.prog2.service.*;

import java.util.List;
import java.util.Scanner;

public class MenuCRUDS {

    private final Scanner sc = new Scanner(System.in);
    
    private final CategoriaService categoriaService = new CategoriaServiceImpl();
    private final ProductoService productoService = new ProductoServiceImpl();
    private final UsuarioService usuarioService = new UsuarioServiceImpl();
    private final PedidoService pedidoService = new PedidoServiceImpl();

    // === METODOS AUXILIARES ===
    private String leerString(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private int leerInt(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debe ingresar un número entero válido.");
            }
        }
    }

    private long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ERROR:Debe ingresar un número válido.");
            }
        }
    }

    private double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ERROR:Debe ingresar un número decimal válido.");
            }
        }
    }

    private boolean confirmar(String mensaje) {
        System.out.print(mensaje + " (S/N): ");
        String resp = sc.nextLine().trim().toUpperCase();
        return resp.equals("S") || resp.equals("SI");
    }

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
            opcion = leerInt("Seleccione: ");

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

    // === LISTAR ===
    private void listar(int modulo) {
        switch (modulo) {
            case 1 -> {
                System.out.println("\n--- LISTA DE CATEGORÍAS ---");
                List<Categoria> categorias = categoriaService.listarTodos();
                if (categorias.isEmpty()) {
                    System.out.println("No hay categorías registradas.");
                } else {
                    for (Categoria c : categorias) {
                        System.out.printf("ID: %d | Nombre: %s | Descripción: %s%n",
                                c.getId(), c.getNombre(), c.getDescripcion());
                    }
                }
            }
            case 2 -> {
                System.out.println("\n--- LISTA DE PRODUCTOS ---");
                List<Producto> productos = productoService.listarTodos();
                if (productos.isEmpty()) {
                    System.out.println("No hay productos registrados.");
                } else {
                    for (Producto p : productos) {
                        System.out.printf("ID: %d | %s | $%.2f | Stock: %d | Categoría: %s%n",
                                p.getId(), p.getNombre(), p.getPrecio(),
                                p.getStock(), p.getCategoria().getNombre());
                    }
                }
            }
            case 3 -> {
                System.out.println("\n--- LISTA DE USUARIOS ---");
                List<Usuario> usuarios = usuarioService.listarTodos();
                if (usuarios.isEmpty()) {
                    System.out.println("No hay usuarios registrados.");
                } else {
                    for (Usuario u : usuarios) {
                        System.out.printf("ID: %d | %s %s | Mail: %s | Rol: %s%n",
                                u.getId(), u.getNombre(), u.getApellido(),
                                u.getMail(), u.getRol());
                    }
                }
            }
            case 4 -> {
                System.out.println("\n--- LISTA DE PEDIDOS ---");
                List<Pedido> pedidos = pedidoService.listarTodos();
                if (pedidos.isEmpty()) {
                    System.out.println("No hay pedidos registrados.");
                } else {
                    for (Pedido p : pedidos) {
                        System.out.println("1");
                        System.out.println("-----------------------------------------------------");
                        System.out.println(p);
                        System.out.println("-----------------------------------------------------");
                    }
                }
            }
        }
    }

    // === CREAR ===
    private void crear(int modulo) {
        switch (modulo) {
            case 1 -> {
                System.out.println("\n--- CREAR CATEGORÍA ---");
                String nombre = leerString("Nombre: ");
                String descripcion = leerString("Descripción: ");

                Categoria c = new Categoria(nombre, descripcion);
                try {
                    categoriaService.guardarCategoria(c);
                    System.out.println("Categoría creada con ID: " + c.getId());
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 2 -> {
                System.out.println("\n--- CREAR PRODUCTO ---");
                String nombre = leerString("Nombre: ");
                double precio = leerDouble("Precio: ");
                String descripcion = leerString("Descripción: ");
                int stock = leerInt("Stock: ");
                String imagen = leerString("Imagen: ");
                long idCat = leerLong("ID de categoría: ");

                try {
                    Categoria categoria = categoriaService.buscarPorId(idCat);
                    Producto p = new Producto(nombre, precio, descripcion, stock, imagen, categoria);
                    productoService.guardarProducto(p);
                    System.out.println("Producto creado con ID: " + p.getId());
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 3 -> {
                System.out.println("\n--- CREAR USUARIO ---");
                String nombre = leerString("Nombre: ");
                String apellido = leerString("Apellido: ");
                String mail = leerString("Email: ");
                String celular = leerString("Celular: ");
                String pass = leerString("Contraseña: ");
                String rolStr = leerString("Rol (ADMIN / USUARIO): ");

                Rol rol;
                try {
                    rol = Rol.valueOf(rolStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Rol inválido. Se asigna USUARIO por defecto.");
                    rol = Rol.USUARIO;
                }

                Usuario u = new Usuario(nombre, apellido, mail, celular, pass, rol);
                try {
                    usuarioService.registrarUsuario(u);
                    System.out.println("Usuario creado con ID: " + u.getId());
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 4 -> {
                System.out.println("\n--- CREAR PEDIDO ---");
                long idUsuario = leerLong("ID Usuario: ");

                try {
                    Usuario usuario = usuarioService.buscarPorId(idUsuario);

                    String formaPagoStr = leerString("Forma de pago (EFECTIVO / TARJETA / TRANSFERENCIA): ");
                    FormaPago formaPago;
                    try {
                        formaPago = FormaPago.valueOf(formaPagoStr.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Forma de pago inválida.");
                        return;
                    }

                    Pedido pedido = new Pedido(formaPago, usuario);

                    // Agregar productos al pedido
                    while (true) {
                        long idProducto = leerLong("ID Producto (0 para terminar): ");
                        if (idProducto == 0) break;

                        try {
                            Producto producto = productoService.buscarPorId(idProducto);
                            int cantidad = leerInt("Cantidad: ");
                            pedido.addDetallePedido(cantidad, producto);
                            System.out.println("Producto agregado al pedido.");
                        } catch (RuntimeException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }

                    // Guardar pedido con transacción
                    pedidoService.procesarPedido(pedido);
                    System.out.println("Pedido creado con ID: " + pedido.getId());

                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    // === EDITAR ===
    private void editar(int modulo) {
        switch (modulo) {
            case 1 -> {
                long id = leerLong("ID categoría: ");
                try {
                    Categoria c = categoriaService.buscarPorId(id);
                    System.out.println("Datos actuales: " + c.getNombre() + " | " + c.getDescripcion());

                    String nuevoNombre = leerString("Nuevo nombre (Enter para mantener): ");
                    String nuevaDesc = leerString("Nueva descripción (Enter para mantener): ");

                    if (!nuevoNombre.isEmpty()) c.setNombre(nuevoNombre);
                    if (!nuevaDesc.isEmpty()) c.setDescripcion(nuevaDesc);

                    categoriaService.modificarCategoria(c);
                    System.out.println("Categoría actualizada.");
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 2 -> {
                long id = leerLong("ID producto: ");
                try {
                    Producto p = productoService.buscarPorId(id);
                    System.out.println("Datos actuales:");
                    System.out.println("  Nombre: " + p.getNombre());
                    System.out.println("  Precio: $" + p.getPrecio());
                    System.out.println("  Descripción: " + p.getDescripcion());
                    System.out.println("  Stock: " + p.getStock());
                    System.out.println("  Imagen: " + p.getImagen());
                    System.out.println("  Categoría ID: " + p.getCategoria().getId());

                    String nuevoNombre = leerString("Nuevo nombre (Enter para mantener): ");
                    if (!nuevoNombre.isEmpty()) p.setNombre(nuevoNombre);

                    double nuevoPrecio = leerDouble("Nuevo precio (0 para mantener): ");
                    if (nuevoPrecio > 0) p.setPrecio(nuevoPrecio);

                    String nuevaDesc = leerString("Nueva descripción (Enter para mantener): ");
                    if (!nuevaDesc.isEmpty()) p.setDescripcion(nuevaDesc);

                    int nuevoStock = leerInt("Nuevo stock (-1 para mantener): ");
                    if (nuevoStock >= 0) p.setStock(nuevoStock);

                    String nuevaImagen = leerString("Nueva imagen (Enter para mantener): ");
                    if (!nuevaImagen.isEmpty()) p.setImagen(nuevaImagen);

                    long nuevaCategoria = leerLong("Nuevo ID de categoría (0 para mantener): ");
                    if (nuevaCategoria > 0) {
                        Categoria cat = categoriaService.buscarPorId(nuevaCategoria);
                        p.setCategoria(cat);
                    }

                    productoService.modificarProducto(p);
                    System.out.println("Producto actualizado.");
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 3 -> {
                long id = leerLong("ID usuario: ");
                try {
                    Usuario u = usuarioService.buscarPorId(id);
                    System.out.println("Datos actuales:");
                    System.out.println("  Nombre: " + u.getNombre());
                    System.out.println("  Apellido: " + u.getApellido());
                    System.out.println("  Mail: " + u.getMail());
                    System.out.println("  Celular: " + u.getCelular());
                    System.out.println("  Rol: " + u.getRol());

                    String nuevoNombre = leerString("Nuevo nombre (Enter para mantener): ");
                    if (!nuevoNombre.isEmpty()) u.setNombre(nuevoNombre);

                    String nuevoApellido = leerString("Nuevo apellido (Enter para mantener): ");
                    if (!nuevoApellido.isEmpty()) u.setApellido(nuevoApellido);

                    String nuevoMail = leerString("Nuevo mail (Enter para mantener): ");
                    if (!nuevoMail.isEmpty()) u.setMail(nuevoMail);

                    String nuevoCelular = leerString("Nuevo celular (Enter para mantener): ");
                    if (!nuevoCelular.isEmpty()) u.setCelular(nuevoCelular);

                    String nuevaPass = leerString("Nueva contraseña (Enter para mantener): ");
                    if (!nuevaPass.isEmpty()) u.setContrasenia(nuevaPass);

                    String nuevoRol = leerString("Nuevo rol (ADMIN/USUARIO, Enter para mantener): ");
                    if (!nuevoRol.isEmpty()) {
                        try {
                            u.setRol(Rol.valueOf(nuevoRol.toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Rol inválido, se mantiene el anterior.");
                        }
                    }

                    usuarioService.modificarUsuario(u);
                    System.out.println("Usuario actualizado.");
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 4 -> {
                long id = leerLong("ID pedido: ");
                try {
                    Pedido pedido = pedidoService.buscarPorId(id);
                    System.out.println("Datos actuales:");
                    System.out.println("  Estado: " + pedido.getEstado());
                    System.out.println("  Forma de pago: " + pedido.getFormaPago());

                    String nuevoEstadoStr = leerString("Nuevo estado (PENDIENTE/CONFIRMADO/TERMINADO/CANCELADO, Enter para mantener): ");
                    if (!nuevoEstadoStr.isEmpty()) {
                        try {
                            pedido.setEstado(Estado.valueOf(nuevoEstadoStr.toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Estado inválido, se mantiene el anterior.");
                        }
                    }

                    String nuevaFormaPagoStr = leerString("Nueva forma de pago (EFECTIVO/TARJETA/TRANSFERENCIA, Enter para mantener): ");
                    if (!nuevaFormaPagoStr.isEmpty()) {
                        try {
                            pedido.setFormaPago(FormaPago.valueOf(nuevaFormaPagoStr.toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Forma de pago inválida, se mantiene la anterior.");
                        }
                    }

                    pedidoService.cambiarEstadoFormaPago(pedido);
                    System.out.println("Pedido actualizado.");
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    // === ELIMINAR ===
    private void eliminar(int modulo) {
        long id = leerLong("ID a eliminar: ");

        if (!confirmar("¿Está seguro de eliminar este registro?")) {
            System.out.println("Operación cancelada.");
            return;
        }

        switch (modulo) {
            case 1 -> {
                try {
                    categoriaService.darDeBaja(id);
                    System.out.println("Categoría eliminada lógicamente.");
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 2 -> {
                try {
                    productoService.darDeBaja(id);
                    System.out.println("Producto eliminado lógicamente.");
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 3 -> {
                try {
                    usuarioService.darDeBaja(id);
                    System.out.println("Usuario eliminado lógicamente.");
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 4 -> {
                try {
                    pedidoService.cancelarPedido(id);
                    System.out.println("Pedido eliminado lógicamente.");
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}