package integrado.prog2;

import integrado.prog2.entities.MenuCRUDS;
import integrado.prog2.exception.OpcionInvalidaException;

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

            try {
                // Validamos que sea un número entero
                if (!sc.hasNextInt()) {
                    sc.next(); // Limpiamos el búfer
                    throw new OpcionInvalidaException("¡Debe ingresar un número entero!");
                }

                opcion = sc.nextInt();

                // Validamos que esté dentro del rango del menú
                if (opcion < 0 || opcion > 4) {
                    throw new OpcionInvalidaException("¡Esa opción no existe en el menú!");
                }

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

            } catch (OpcionInvalidaException e) {
                System.out.println(e.getMessage());
                opcion = -1; // Forzamos a que el bucle continúe si hubo error
            }

        } while (opcion != 0);

        sc.close();
    }
}