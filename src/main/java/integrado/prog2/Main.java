package integrado.prog2;

import integrado.prog2.entities.MenuCRUDS;
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

            //PASAMOS UNA OPCION Y DEPENDIENDO DE LA OPCION QUE
            //ELIJAMOS TRABAJAREMOS CON ESE OBJETO
            switch (opcion) {

                case 1:
                    menu.mostrar(1);
                    break;

                case 2:
                    menu.mostrar(2);
                    break;

                case 3:
                    menu.mostrar(3);
                    break;

                case 4:
                    menu.mostrar(4);
                    break;

                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}