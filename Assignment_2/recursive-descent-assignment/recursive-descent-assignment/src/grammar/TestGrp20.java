package grammar;

import java.util.Arrays;

import junit.framework.TestCase;

/**
 * @author pschaus
 * add some tests
 */
public class TestGrp20 extends TestCase {
	public void test() {
		Parser parser = new Parser();
		boolean expectedOK = true;
		boolean ok=true;
		//print num = num
		Integer [] sentence = {6,8,9,8};
		ok = parser.parse(sentence);
	    if (ok != expectedOK) {
	      System.out.println(Arrays.toString(sentence));
	    }
		assertTrue(ok == expectedOK);	
		
		//begin (print num = num) ; (print num = num) end
		Integer [] sentence2 = {4,6,8,9,8,7,6,8,9,8,5};
		ok = parser.parse(sentence2);
	    if (ok != expectedOK) {
	      System.out.println(Arrays.toString(sentence2));
	    }
		assertTrue(ok == expectedOK);	
		//print (num = num) then ...
		expectedOK = false;
		Integer [] sentence3 = {6, 8, 9, 8, 2, 6, 8, 9, 8, 3, 6, 8, 9, 8};
		ok = parser.parse(sentence3);
	    if (ok != expectedOK) {
	      System.out.println(Arrays.toString(sentence3));
	    }
		assertTrue(ok == expectedOK);	
		
	}
}
