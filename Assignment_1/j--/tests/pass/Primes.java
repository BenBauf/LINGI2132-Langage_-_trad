package pass;

public class Primes {
	
	public int[] primes (int n){
		
		boolean isPrime = true;
        
        for (int i = 2; i <= n; i++) {
            isPrime = true;
            
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            
            if (isPrime) {
                System.out.println(i);
            }
        }
        return null;
	}
	
}