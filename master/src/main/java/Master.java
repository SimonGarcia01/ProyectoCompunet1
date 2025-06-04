import java.io.*;
import com.zeroc.Ice.*;

import java.util.Scanner;

public class Master {
  public static void main(String[] args) {
      try (Communicator cummunicator = Util.initialize(args, "properties.cfg")){
      ObjectAdapter adapter = cummunicator.createObjectAdapter("services"); 
      PublisherI publisher = new PublisherI();
      adapter.add(publisher, Util.stringToIdentity("Publisher")); 
      adapter.activate();

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
        System.out.println("Escribe el rango de numeros");
        System.out.println("Escribe el numero minimo");
        String min = reader.readLine();
        //input.nextLine(); // Consume newline
        System.out.println("Escribe el numero maximo");
        String max = reader.readLine();
        //input.nextLine(); // Consume newline
        
        System.out.print("Nombre del worker: ");
        String name = reader.readLine();

        publisher.notifySuscriber(name, min, max); 
      cummunicator.waitForShutdown(); 
      reader.close();
     }
     catch (IOException e) {
          e.printStackTrace(); 
     }
  }
}