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
 public void removeSuscriber(String name, Current current){
 suscribers.remove(name); 
System.out.println("Suscriber has been removed ");
 }
 public void notifySuscriber(String name, String min, String max){
  SuscriberPrx suscriber = suscribers.get(name); 
  suscriber.onUpdate(min, max);
 }
 }