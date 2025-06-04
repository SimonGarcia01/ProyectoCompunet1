import java.io.*;
import com.zeroc.Ice.*;

public class Master {
  public static void main(String[] args) {
      try (Communicator cummunicator = Util.initialize(args, "properties.cfg")){
      ObjectAdapter adapter = cummunicator.createObjectAdapter("services"); 
      PublisherI publisher = new PublisherI();
      adapter.add(publisher, Util.stringToIdentity("Publisher")); 
      adapter.activate(); 
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
      String msg = "";
      System.out.println("Envia un mensaje con el formato: NameSuscribe::Mensaje");
      while ((msg = reader.readLine()) != null ){ 
         if (!msg.contains("::")) {
             System.out.println("Formato incorrecto. Intente de nuevo.");
             continue;
          }
          String[] command = msg.split("::");
          publisher.notifySuscriber(command[0], command[1]);
      }        
      cummunicator.waitForShutdown(); 
      reader.close();
     }
     catch (IOException e) {
          e.printStackTrace(); 
     }
  }
}