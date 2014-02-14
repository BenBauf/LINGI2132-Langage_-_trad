package junit;

import pass.UnaryPlus;
import junit.framework.TestCase;


public class UnaryPlusTest extends TestCase {
    private UnaryPlus uplus;
    
    protected void setUp() throws Exception {
        super.setUp();
        this.uplus = new UnaryPlus();
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testUnaryPlus() {
        this.assertEquals(this.uplus.uplus(42), 42);
        this.assertEquals(this.uplus.uplus(-42), -42);
        this.assertEquals(this.uplus.uplus(0), 0);
    }
}
