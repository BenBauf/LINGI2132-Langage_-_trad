package junit;

import junit.framework.TestCase;
import pass.Primes;

public class PrimesTest extends TestCase{
    private Primes prime;
    
    protected void setUp() throws Exception {
        super.setUp();
        //prime = new Primes();
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testPrimes(){
    	new Primes().primes(3);
    	this.assertEquals(true,true);
        //this.assertEquals(modulo.modulo(42,16),10);
        //this.assertEquals(modulo.modulo(4,2),0);
        //this.assertEquals(modulo.modulo(127,3),1);
    }
}
