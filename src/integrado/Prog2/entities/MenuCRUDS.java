package integrado.Prog2.entities;

import java.util.Scanner;

public class MenuCRUDS {

    private final Scanner sc = new Scanner(System.in);

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

            case 4:
                return "PEDIDOS";

            default:
                return "DESCONOCIDO";
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

            case 4:
                System.out.println("Listando pedidos...");
                break;
        }
    }

    private void crear(int modulo) {

        switch (modulo) {
            case 1:
                System.out.println("Creando categoría...");
                break;

            case 2:
                System.out.println("Creando producto...");
                break;

            case 3:
                System.out.println("Creando usuario...");
                break;

            case 4:
                System.out.println("Creando pedido...");
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

            case 4:
                System.out.println("Editando pedido...");
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

            case 4:
                System.out.println("Eliminando pedido...");
                break;
        }
    }
}