package junit;

import junit.framework.TestCase;
import pass.*;

public class PrimesTest extends TestCase {
    private Primes prime;
    
    protected void setUp() throws Exception {
        super.setUp();
        this.prime = new Primes();
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testPrimes() {
        int[] calcul = this.prime.primes(3);
        final int[] result = {2, 3};
        for (int i = 0; i < result.length; ++i)
            this.assertEquals(calcul[i], result[i]);
        
        calcul = this.prime.primes(6);
        final int[] result2 = {2, 3, 5};
        for (int i = 0; i < result2.length; ++i)
            this.assertEquals(calcul[i], result2[i]);
        
        this.assertEquals(this.prime.primes(1).length, 0);
    }
}
