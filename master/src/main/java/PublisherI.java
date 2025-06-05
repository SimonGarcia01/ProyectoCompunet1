/**
      Definición                   Definición 
          en                          en
         Java                         Ice
          ^                            ^
          |                            |
  +-------------+             +----------------+
  | PublisherI  | ---------|> | Demo.Publisher |
  +-------------+             +----------------+
*/
import com.zeroc.Ice.Current; // definido en Ice para sus métodos 
import java.util.HashMap;
import Demo.SuscriberPrx;
import Demo.PublisherPrx;

public class PublisherI implements Demo.Publisher {
 private HashMap<String, SuscriberPrx> suscribers; 
public PublisherI(){
 suscribers = new HashMap<>(); 
}

 @Override
 public void addSuscriber(String name, SuscriberPrx suscriber, Current current){
 System.out.println("New Suscriber: " + name);
 suscribers.put(name, suscriber);
}

 @Override
 public void sendNumbers(String name, int min, int max, int nodes, Current current) {
  System.out.println("Recibido desde el cliente: min=" + min + ", max=" + max + ", nodos=" + nodes);
  distributeNodes(min, max, nodes);
 }

 public void distributeNodes(int min, int max, int nodes) {
  int cantidadNumeros = max - min + 1;
  int cantidadPorWorker = cantidadNumeros / nodes;
  int resto = cantidadNumeros % nodes;

  for (int i = 0; i < nodes; i++) {
   int start = min + i * cantidadPorWorker;
   int end = start + cantidadPorWorker - 1;

   if (resto > 0) {
    end += 1;
    min++;
    resto--;
   }

   notifySuscriber("Worker" + (i + 1), start, end);
   System.out.println("Worker " + (i + 1) + " procesa el rango: " + start + " a " + end);
  }
 }


 @Override
 public void removeSuscriber(String name, Current current){
 suscribers.remove(name); 
System.out.println("Suscriber has been removed ");
 }
 public void notifySuscriber(String name, int min, int max){
  SuscriberPrx suscriber = suscribers.get(name); 
  suscriber.onUpdate(min, max);
 }
 }