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
        final int[] calcul = this.prime.primes(3);
        final int[] result = {2, 3};
        for (int i = 0; i < calcul.length; ++i)
            this.assertEquals(calcul[i], result[i]);
    }
}
