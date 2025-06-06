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
      System.out.println("Numero minimo: " + min);
      System.out.println("Numero maximo: " + max);

      List<Integer> perfectNumbers = new ArrayList<>();

      for(int n = min; n <= max; n++){
          if(PerfectNumberCalculator.isPerfectNumber(n)){
              perfectNumbers.add(n);
          }
      }

      int[] returnArray = new int[perfectNumbers.size()];


      for(int n = 0; n < perfectNumbers.size(); n++){
          returnArray[n] = perfectNumbers.get(n);
      }

      System.out.print("Los numeros perfectos en este rango son: ");
      for(int number: returnArray){
          System.out.println(" "+number+",");
      }

      return returnArray;
  }
}