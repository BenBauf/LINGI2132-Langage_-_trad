package grammar;

import java.util.Arrays;
import java.util.Random;

import junit.framework.TestCase;

/**
 * @author pschaus
 * DO NOT MODIFY
 */
public class Test extends TestCase {
	public void test() {
		Parser parser = new Parser();
		String debug = "";
		boolean expectedOK = true;
		boolean ok=true;
		//print num = num
		Integer [] sentence = {6,8,9,8};
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
