package junit;

import junit.framework.TestCase;
import pass.Ternary;

public class TernaryTest extends TestCase {
	
	private Ternary ternary;

	protected void setUp() throws Exception {
		super.setUp();
		ternary = new Ternary();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testTernary() {
		this.assertEquals(ternary.ternary(7,5), 5);
		this.assertEquals(ternary.ternary(10, 15), 15);
		this.assertEquals(ternary.ternary('a', 'b'), 'b');
		this.assertEquals(ternary.ternary('c', 'd'), 'd');
		this.assertEquals(ternary.ternary(true, 2,3), 2);
	}
}
