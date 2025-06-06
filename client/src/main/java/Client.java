import com.zeroc.Ice.*;
import java.io.*; 
import Demo.Suscriber;
import Demo.SuscriberPrx;
import Demo.PublisherPrx;

public class Client {
    public static void main(String[] args) {
    	try(Communicator communicator 
			= Util.initialize(args,
				"properties.cfg")) {

			Suscriber suscriber = new SuscriberI();

			ObjectAdapter adapter =
			communicator
			.createObjectAdapter("Suscriber");

			ObjectPrx proxys = adapter
			.add(suscriber,
				Util.stringToIdentity("NN"));

			adapter.activate();

			SuscriberPrx suscriberPrx =
			SuscriberPrx.checkedCast(proxys);

			PublisherPrx publisher =
			PublisherPrx.checkedCast(
				communicator
				.propertyToProxy(
					"publisher.proxy"));

			if(publisher == null){
			throw new Error("Bat Ice Proxy");
			}

			System.out.print("Ingrese el nombre del cliente: ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String name = reader.readLine();
			publisher.addSuscriber(name, suscriberPrx);

			boolean flag = false;
			int min = 0, max = 0, nodes = 0;
			while(!flag){
				System.out.print("Ingrese el número mínimo: ");
				min = Integer.parseInt(reader.readLine());

				System.out.print("Ingrese el número máximo: ");
				max = Integer.parseInt(reader.readLine());

				System.out.print("Ingrese la cantidad de nodos (workers): ");
				nodes = Integer.parseInt(reader.readLine());

				if (max-min + 1 <= 0) {
					System.out.println("El rango de numeros es invalido.");
				}
				else if (nodes <= 0) {
					System.out.println("El numero de workers debe ser mayor que cero.");
				}
				else if (nodes >= max-min + 1) {
					System.out.println("El numero de workers no puede ser mayor que la cantidad de numeros en el rango.");
				}
				else {
					flag = true;
				}
			}

			long start = System.currentTimeMillis();

			int[] foundPerfectNumbers = publisher.sendNumbers(name, min, max, nodes);

			long end = System.currentTimeMillis();

			System.out.print("Los numeros perfectos son:");
			for(int n = 0; n < foundPerfectNumbers.length; n++){
				System.out.print(" " + foundPerfectNumbers[n] + ",");
			}

			long totalTimeMillis = end - start;

			double totalTimeSeconds = totalTimeMillis / 1000.0;

			double totalTimeMinutes = totalTimeSeconds / 60.0;

			System.out.println("\nTiempo de ejecución:");
			System.out.printf(" - Milisegundos: %d ms%n", totalTimeMillis);
			System.out.printf(" - Segundos: %.3f s%n", totalTimeSeconds);
			System.out.printf(" - Minutos: %.3f min%n", totalTimeMinutes);

			System.out.println("Proceso Terminado (jijiji)");

			communicator.waitForShutdown();
    	} catch(IOException e){
            e.printStackTrace();
        }
    }
}