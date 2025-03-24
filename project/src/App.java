import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static List<Persona> personas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            switch (opcion) {
                case 1:
                    registrarJugador();
                    break;
                case 2:
                    registrarArbitro();
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 5);
    }

    private static void mostrarMenu() {
        System.out.println("1. Registrar Jugador");
        System.out.println("2. Registrar Árbitro");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void registrarJugador() {
        if (personas.size() >= 20) {
            System.out.println("No se pueden registrar más personas.");
            return;
        }
        System.out.print("Nombre del jugador: ");
        String nombre = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine(); 

        Jugador jugador = new Jugador(nombre, edad, personas.size() + 1);
        personas.add(jugador);
        System.out.println("Jugador registrado con éxito: " + jugador.getNombre());
    }

    private static void registrarArbitro() {
        if (personas.size() >= 20) {
            System.out.println("No se pueden registrar más personas.");
            return;
        }
        System.out.print("Nombre del árbitro: ");
        String nombre = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        System.out.print("Años de experiencia: ");
        int experiencia = scanner.nextInt();
        scanner.nextLine(); 
        Arbitro arbitro = new Arbitro(nombre, edad, personas.size() + 1, experiencia);
        personas.add(arbitro);
        System.out.println("Árbitro registrado con éxito: " + arbitro.getNombre());
    }

    /*
        Board b = new Board();

        b.fillMatrixWithRandom();
        b.fillWordList();
        b.putWordsInMatrix();
        b.quitWordInMatrix(b.wordList[0]);
        b.quitWordInMatrix(b.wordList[1]);
        b.quitWordInMatrix(b.wordList[2]);
        b.quitWordInMatrix(b.wordList[3]);
        b.quitWordInMatrix(b.wordList[4]);
        b.quitWordInMatrix(b.wordList[5]);
        b.quitWordInMatrix(b.wordList[6]);
        b.quitWordInMatrix(b.wordList[7]);
        b.printMatrix();
        System.out.println("\n\n");
     */
}