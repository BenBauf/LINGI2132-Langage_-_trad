package pass;

//
//
//
public class Primes {

    public int[] primes(int n) {
        int pos = 0;
        int[] save = new int[n];
        boolean isPrime = true;
        int i = 2;
        while (i <= n) {
            isPrime = true;
            int j = 2;
            int limit = i - 1;
            while (isPrime && j <= limit) {
                if (i % j == 0) {
                    isPrime = false;
                 }
                ++j;
            }
            if (isPrime) {
                save[pos] = i;
                ++pos;
            }
            ++i;
        }
        int[] retour = new int[pos];
        i = 0;
        pos = pos - 1;
        while (i <= pos) {
            retour[i] = save[i];
            ++i;
        }
        return retour;
    }
}
