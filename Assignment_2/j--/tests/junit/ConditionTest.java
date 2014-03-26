package junit;

import junit.framework.TestCase;
import pass.ConditionalExpression;

public class ConditionTest extends TestCase
{
	private ConditionalExpression condition;
	
	protected void setUp() throws Exception
	{
		super.setUp();
		condition = new ConditionalExpression();
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testDivide()
	{
		this.assertEquals(condition.checkIf(0, 42), 42);
		this.assertEquals(condition.checkIf(43, 42), 43);
	}
}
