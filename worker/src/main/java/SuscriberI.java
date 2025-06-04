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
  public void onUpdate(String msg, Current current){
      System.out.println(msg);
  }   
}