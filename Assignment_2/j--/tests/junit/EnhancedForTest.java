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

	public void testEnhancedFor() {
		this.assertEquals(enhancedFor.enhancedForIntArray(new int[]{ 3, 4, 5, 6, 7 }), 25);
		this.assertEquals(enhancedFor.enhancedForIntArray(new int[]{ 0, 0, 0, 0 }), 0);
		this.assertEquals(enhancedFor.enhancedForIntArray(new int[]{ }), 0);
		
	}
}