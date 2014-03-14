package junit;
import junit.framework.TestCase;
import pass.MultiLineComment;

public class MultiLineCommentTest extends TestCase {
	private MultiLineComment multiLineComment;

	protected void setUp() throws Exception {
		super.setUp();
		multiLineComment = new MultiLineComment();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testDivide() {
		this.assertEquals(multiLineComment.multiLineComment(), 2);
	}
}
