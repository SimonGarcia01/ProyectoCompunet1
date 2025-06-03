import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Escribe el rango de numeros");
        System.out.println("Escribe el numero minimo");
        int min = input.nextInt();
        input.nextLine(); // Consume newline
        System.out.println("Escribe el numero maximo");
        int max = input.nextInt();
        input.nextLine(); // Consume newline

        System.out.println("Escribe la cantidad de nodos (workers) a utilizar");
        int numWorkers = input.nextInt();
        input.nextLine(); // Consume newline

        calcularDivisiones(min, max, numWorkers);

    }

    public static void calcularDivisiones(int min, int max, int numWorkers) {
        int cantidadNumeros = max - min + 1;
        int[] workersRange = new int[numWorkers * 2];
        System.out.println("Cantidad de numeros en el rango: " + cantidadNumeros);

        if (cantidadNumeros <= 0) {
            System.out.println("El rango de numeros es invalido.");
            return;
        }

        if (numWorkers <= 0) {
            System.out.println("El numero de workers debe ser mayor que cero.");
            return;
        }

        if (numWorkers > cantidadNumeros) {
            System.out.println("El numero de workers no puede ser mayor que la cantidad de numeros en el rango.");
            return;
        }

        int cantidadPorWorker = cantidadNumeros / numWorkers;
        int resto = cantidadNumeros % numWorkers;

        System.out.println("Resto: " + resto);
        System.out.println("Cantidad por worker: " + cantidadPorWorker);

        for(int i = 0; i < numWorkers; i++) {
            int start = min + i * cantidadPorWorker;
            int end = start + cantidadPorWorker-1;

            if(resto > 0) {
                end += 1; // Distribuir el resto entre los primeros workers
                min ++;
                resto--;
            }

            System.out.println("Worker " + (i + 1) + " procesa el rango: " + start + " a " + end);
        }
    }
}