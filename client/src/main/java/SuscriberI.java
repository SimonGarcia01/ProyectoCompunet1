import com.zeroc.Ice.*;

import java.util.List;
import java.util.ArrayList;
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
  public int[] onUpdate(int min, int max, Current current){
      return new int[1];
  }   
}