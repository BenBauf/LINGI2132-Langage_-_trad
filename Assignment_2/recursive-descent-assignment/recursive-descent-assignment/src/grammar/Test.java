package grammar;

import java.util.Arrays;
import java.util.Random;

import junit.framework.TestCase;

/**
 * @author pschaus
 * add some tests
 */
public class Test extends TestCase {
	public void test() {
		Parser parser = new Parser();
		String debug = "";
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
		for (int i = 0; i < 1000000; i++) {
			Random rand = new Random();
			Generator gen = new Generator();
			parser = new Parser();
			sentence = gen.generate();
			debug = "";
			expectedOK = true;
			if (rand.nextBoolean()) {
				int idx = rand.nextInt(sentence.length);
				int value = rand.nextInt(9);
				if (value != sentence[idx]) {
					debug = "changed "+idx+" into "+value+" instead of "+sentence[idx];
					sentence[idx] = value;
					expectedOK = false;
				}
				
			}
		    ok = parser.parse(sentence);
		    if (ok != expectedOK) {
		      System.out.println("problem:" + debug);
		      System.out.println(Arrays.toString(sentence));
		    }
			assertTrue(ok == expectedOK);			
		}
	}
}
