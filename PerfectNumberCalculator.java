import java.util.Scanner;

public class PerfectNumberCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a natural number: ");
        int number = scanner.nextInt();
        if (isPerfectNumber(number)) {
            System.out.println(number + " is a perfect number.");
        } else {
            System.out.println(number + " is not a perfect number.");
        }

        scanner.close();
    }

    public static boolean isPerfectNumber(int num) {
        boolean isPerfect = false;
        int sqrt = (int) Math.sqrt(num);
        int sumDivisors = 1;
        if (num > 1) {
            for(int n = 2; n <= sqrt; n++){
                if(num%n == 0){
                    sumDivisors += n;
                    int otherDivisor = num/n;
                    if(otherDivisor != n){
                        sumDivisors += otherDivisor;
                    }
                }
            }

            if(sumDivisors == num){
                isPerfect = true;
            }
        }

        return isPerfect;
    }
}

// https://www.geeksforgeeks.org/find-all-factors-of-a-natural-number/