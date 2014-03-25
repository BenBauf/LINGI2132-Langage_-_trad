package junit;

import junit.framework.TestCase;
import pass.SimpleFor;

public class SimpleForTest extends TestCase {
	private SimpleFor simpleFor;

	protected void setUp() throws Exception {
		super.setUp();
		simpleFor = new SimpleFor();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testDivide() {
		this.assertEquals(simpleFor.loop1(), 128);
		this.assertEquals(simpleFor.loop2(), 1);
		this.assertEquals(simpleFor.loop3(), -5);
		this.assertEquals(simpleFor.loop4(), 2);
		this.assertEquals(simpleFor.loop5(), 12);
		this.assertEquals(simpleFor.loop6(), 4);
		
	}
}