package integrado.prog2;

import integrado.prog2.entities.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        List<Categoria> listaCategorias = new ArrayList<>();
        List<Producto> listaProductos = new ArrayList<>();
        List<Usuario> listaUsuarios = new ArrayList<>();

        MenuCRUDS menu = new MenuCRUDS(listaCategorias, listaProductos, listaUsuarios);

        int opcion;

        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Gestión de Categorías");
            System.out.println("2. Gestión de Productos");
            System.out.println("3. Gestión de Usuarios");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                case 2:
                case 3:
                    menu.mostrar(opcion);
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