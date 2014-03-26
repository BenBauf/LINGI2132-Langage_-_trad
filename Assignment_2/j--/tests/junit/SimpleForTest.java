package junit;

import junit.framework.TestCase;
import pass.SimpleFor;

public class SimpleForTest extends TestCase {
	private SimpleFor test;

	protected void setUp() throws Exception {
		super.setUp();
		test = new SimpleFor();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSimpleFor() {
		this.assertEquals(test.loop1(), 128);
		this.assertEquals(test.loop2(), 1);
		this.assertEquals(test.loop3(), -5);
		this.assertEquals(test.loop4(), 2);
		this.assertEquals(test.loop5(), 12);
		
	}
}