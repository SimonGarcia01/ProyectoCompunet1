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
import java.util.concurrent.CompletableFuture;

public class PublisherI implements Demo.Publisher {
 private HashMap<String, SuscriberPrx> suscribers; 
public PublisherI(){
 suscribers = new HashMap<>(); 
}

 @Override
 public void addSuscriber(String name, SuscriberPrx suscriber, Current current){
 System.out.println("Nuevo suscriber: " + name);
 suscribers.put(name, suscriber);
}

 @Override
 public int[] sendNumbers(String name, int min, int max, int nodes, Current current) {
  System.out.println("Recibido desde el cliente: min=" + min + ", max=" + max + ", nodos=" + nodes);
  return distributeNodesAsync(min, max, nodes);
 }

 public int[] distributeNodesAsync(int min, int max, int nodes) {
  int total = max - min + 1;
  int chunk = total / nodes;
  int remainder = total % nodes;

  List<CompletableFuture<int[]>> futures = new ArrayList<>();

  int currentMin = min;

  for (int i = 0; i < nodes; i++) {
   int start = currentMin;
   int end = start + chunk - 1;
   if (remainder > 0) {
    end++;
    remainder--;
   }
   currentMin = end + 1;

   String workerName = "Worker" + (i + 1);
   SuscriberPrx suscriber = suscribers.get(workerName);

   if (suscriber == null) {
    System.out.println("Worker no encontrado: " + workerName);
    continue;
   }

   System.out.println("Asignando a " + workerName + " el rango " + start + " a " + end);

   CompletableFuture<int[]> future = suscriber
           .begin_onUpdate(start, end)
           .toCompletableFuture();

   futures.add(future);
  }

  List<Integer> result = new ArrayList<>();

  try {
   CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();

   for (CompletableFuture<int[]> future : futures) {
    int[] nums = future.get();
    for (int num : nums) {
     result.add(num);
    }
   }
  } catch (Exception e) {
   e.printStackTrace();
  }

  int[] finalArray = new int[result.size()];
  for (int i = 0; i < result.size(); i++) {
   finalArray[i] = result.get(i);
  }

  System.out.println("Todos los resultados se han reunido.");
  return finalArray;
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
System.out.println("Se ha eliminado suscriber" + name);
 }
 public int[] notifySuscriber(String name, int min, int max){
  SuscriberPrx suscriber = suscribers.get(name); 
  return suscriber.onUpdate(min, max);
 }
 }