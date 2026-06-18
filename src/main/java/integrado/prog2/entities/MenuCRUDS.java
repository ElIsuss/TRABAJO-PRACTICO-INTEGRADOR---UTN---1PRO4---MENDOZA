package integrado.prog2.entities;

import java.util.List;
import java.util.Scanner;

public class MenuCRUDS {

    private final Scanner sc = new Scanner(System.in);

    private List<Categoria> categorias;
    private List<Producto> productos;
    private List<Usuario> usuarios;

    public MenuCRUDS(List<Categoria> categorias, List<Producto> productos, List<Usuario> usuarios) {
        this.categorias = categorias;
        this.productos = productos;
        this.usuarios = usuarios;
    }

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

            opcion = sc.nextInt();

            switch (opcion) {

                case 1:
                    listar(modulo);
                    break;

                case 2:
                    crear(modulo);
                    break;

                case 3:
                    editar(modulo);
                    break;

                case 4:
                    eliminar(modulo);
                    break;

                case 0:
                    System.out.println("Volviendo...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private String obtenerNombreModulo(int modulo) {

        switch (modulo) {
            case 1:
                return "CATEGORÍAS";

            case 2:
                return "PRODUCTOS";

            case 3:
                return "USUARIOS";

            default:
                return "";
        }
    }

    private void listar(int modulo) {

        switch (modulo) {
            case 1:
                System.out.println("Listando categorías...");
                break;

            case 2:
                System.out.println("Listando productos...");
                break;

            case 3:
                System.out.println("Listando usuarios...");
                break;
        }
    }

    private void crear(int modulo) {

        switch (modulo) {
            case 1:
                System.out.println("Creando categoría...");
                break;

            case 2:
                System.out.println("Creando producto...\"");
                break;

            case 3:
                System.out.println("Creando usuario...");
                break;
        }
    }

    private void editar(int modulo) {

        switch (modulo) {
            case 1:
                System.out.println("Editando categoría...");
                break;

            case 2:
                System.out.println("Editando producto...");
                break;

            case 3:
                System.out.println("Editando usuario...");
                break;
        }
    }

    private void eliminar(int modulo) {

        switch (modulo) {
            case 1:
                System.out.println("Eliminando categoría...");
                break;

            case 2:
                System.out.println("Eliminando producto...");
                break;

            case 3:
                System.out.println("Eliminando usuario...");
                break;
        }
    }
}