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

      System.out.println("Esperando al Cliente y los Workers...");

      cummunicator.waitForShutdown();
     }
  }
}