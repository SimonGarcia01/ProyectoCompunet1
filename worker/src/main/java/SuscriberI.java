import com.zeroc.Ice.*;
/**
      Definición                   Definición 
          en                          en
         Java                         Ice
          ^                            ^
          |                            |
  +-------------+             +----------------+
  | SuscriberI  | ---------|> | Demo.Suscriber |
  +-------------+             +----------------+
*/
public class SuscriberI implements Demo.Suscriber {
  @Override
  public void onUpdate(String min, String max, Current current){
      System.out.println(min);
      System.out.println(max);
  }   
}