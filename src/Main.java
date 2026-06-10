import integrado.Prog2.entities.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        MenuCRUDS menu = new MenuCRUDS();

        int opcion;

        do {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                case 2:
                case 3:
                case 4:
                    menu.mostrar(opcion);
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}