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
import java.util.List;
import java.util.ArrayList;

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
 public int[] sendNumbers(String name, int min, int max, int nodes, Current current) {
  System.out.println("Recibido desde el cliente: min=" + min + ", max=" + max + ", nodos=" + nodes);
  return distributeNodes(min, max, nodes);
 }

 public int[] distributeNodes(int min, int max, int nodes) {
  int cantidadNumeros = max - min + 1;
  int cantidadPorWorker = cantidadNumeros / nodes;
  int resto = cantidadNumeros % nodes;


  List<Integer> finalPerfectNumberList = new ArrayList<>();
  for (int i = 0; i < nodes; i++) {
   int start = min + i * cantidadPorWorker;
   int end = start + cantidadPorWorker - 1;

   if (resto > 0) {
    end += 1;
    min++;
    resto--;
   }
   System.out.println("Worker " + (i + 1) + " procesa el rango: " + start + " a " + end);

   int[] parcialNumbers = notifySuscriber("Worker" + (i + 1), start, end);

   for(int n = 0; n < parcialNumbers.length; n++){
    finalPerfectNumberList.add(parcialNumbers[n]);
   }
  }

  int[] finalPerfectNumberArray = new int[finalPerfectNumberList.size()];
  for(int n = 0; n < finalPerfectNumberList.size(); n++){
   finalPerfectNumberArray[n] = finalPerfectNumberList.get(n);
  }
  System.out.println("Los numeros perfectos han sido calculados!");
  return finalPerfectNumberArray;
 }


 @Override
 public void removeSuscriber(String name, Current current){
 suscribers.remove(name); 
System.out.println("Suscriber has been removed ");
 }
 public int[] notifySuscriber(String name, int min, int max){
  SuscriberPrx suscriber = suscribers.get(name); 
  return suscriber.onUpdate(min, max);
 }
 }