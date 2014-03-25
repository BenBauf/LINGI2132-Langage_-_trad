package junit;

import junit.framework.TestCase;
import pass.EnhancedFor;

public class EnhancedForTest extends TestCase {
	private EnhancedFor enhancedFor;

	protected void setUp() throws Exception {
		super.setUp();
		enhancedFor = new EnhancedFor();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testDivide() {
		this.assertEquals(enhancedFor.loop1(), 5);
		
	}
}