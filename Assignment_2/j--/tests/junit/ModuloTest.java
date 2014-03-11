package junit;

import junit.framework.TestCase;
import pass.Modulo;

public class ModuloTest extends TestCase {

    private Modulo modulo;
    
    protected void setUp() throws Exception {
        super.setUp();
        this.modulo = new Modulo();
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testModulo() {
        this.assertEquals(this.modulo.modulo(42, 16), 10);
        this.assertEquals(this.modulo.modulo(4, 2), 0);
        this.assertEquals(this.modulo.modulo(127, 3), 1);
    }
}
