package junit;

import junit.framework.TestCase;
import java.lang.System;
import pass.ConditionalTestExpression;

public class ConditionTest extends TestCase
{
	private ConditionalTestExpression condition;
	
	protected void setUp() throws Exception
	{
		super.setUp();
		condition = new ConditionalTestExpression();
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testCondition()
	{
		this.assertEquals(condition.checkIf(0, 42), 42);
		this.assertEquals(condition.checkIf(43, 42), 43);
		this.assertEquals(condition.checkIfMultiTypes(43, 42), 43);
		this.assertEquals(condition.checkIfMultiTypes(0, 42), 'a');
	}
}
