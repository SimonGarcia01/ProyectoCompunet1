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
        
        System.out.print("Nombre del worker: ");
        String name = reader.readLine();

        //publisher.notifySuscriber(name, 0, 20);
      cummunicator.waitForShutdown(); 
      reader.close();
     }
     catch (IOException e) {
          e.printStackTrace(); 
     }
  }
}