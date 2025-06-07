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
import com.zeroc.Ice.Current;
import Demo.SuscriberPrx;
import Demo.Publisher;
import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PublisherI implements Publisher {

 private final Map<String, SuscriberPrx> suscribers = new HashMap<>();
 private final Queue<Integer> results = new ConcurrentLinkedQueue<>();
 private final AtomicInteger pendingWorkers = new AtomicInteger(0);

 @Override
 public void addSuscriber(String name, SuscriberPrx suscriber, Current current) {
  System.out.println("Nuevo suscriber: " + name);
  suscribers.put(name, suscriber);
 }

 @Override
 public void removeSuscriber(String name, Current current) {
  suscribers.remove(name);
  System.out.println("Se ha eliminado suscriber: " + name);
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

  int currentMin = min;
  pendingWorkers.set(nodes);
  results.clear();  // limpiar resultados anteriores

  List<CompletableFuture<Void>> tasks = new ArrayList<>();

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
    pendingWorkers.decrementAndGet(); // descontamos uno
    continue;
   }

   System.out.println("Asignando a " + workerName + " el rango " + start + " a " + end);

   // onUpdateAsync devuelve un CompletableFuture<int[]>
   CompletableFuture<Void> task = suscriber
           .onUpdateAsync(start, end)
           .thenAccept(perfects -> {
            for (int n : perfects) {
             results.add(n);
            }
            int remaining = pendingWorkers.decrementAndGet();
            if (remaining == 0) {
             System.out.println("Todos los resultados han sido reunidos.");
             System.out.println("Perfectos: " + results);
            }
           })
           .exceptionally(ex -> {
            System.err.println("Error en worker " + workerName + ": " + ex.getMessage());
            int remaining = pendingWorkers.decrementAndGet();
            if (remaining == 0) {
             System.out.println("Finalizó con errores. Resultados parciales: " + results);
            }
            return null;
           });

   tasks.add(task);
  }

  // Bloqueamos solo hasta que todas las tareas terminen (puedes eliminar esto si quieres 100% async)
  try {
   CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).get();
  } catch (Exception e) {
   e.printStackTrace();
  }

  // Convertir resultados a int[]
  int[] finalArray = results.stream().mapToInt(Integer::intValue).toArray();
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

 public int[] notifySuscriber(String name, int min, int max){
  SuscriberPrx suscriber = suscribers.get(name);
  return suscriber.onUpdate(min, max);
 }
}