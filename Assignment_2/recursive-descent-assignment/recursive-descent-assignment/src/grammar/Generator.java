package grammar;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static grammar.Grammar.*;

/**
 * Random generation of a valid derivation
 * @author pschaus
 * DO NOT MODIFY
 */
public class Generator {
	
	private final Random rand = new Random();
	
	public  Integer[] generate() {
		List<Integer> res = new LinkedList<Integer>();
		generateS(res);
		return res.toArray(new Integer[0]);
	}
	
	
	public void generateS(List<Integer> output) {
		int r = rand.nextInt(3);
		if (output.size() > 100) r = 2;
		switch (r) {
		case 0:
			// S -> if E then S else S
			output.add(IF);
			generateE(output);
			output.add(THEN);
			generateS(output);
			output.add(ELSE);
			generateS(output);
			break;
		case 1:
			// begin S L
			output.add(BEGIN);
			generateS(output);
			generateL(output);
			break;
		case 2:
			// print E
			output.add(PRINT);
			generateE(output);			
			break;			
		default:
			break;
		}
	}
	
	public void generateL(List<Integer> output) {
		int r = rand.nextInt(2);
		switch (r) {
		case 0:
			// L -> end
			output.add(END);
			break;
		case 1:
			// L -> ; S L
			output.add(SEMI);
			generateS(output);
			generateL(output);
			break;		
		default:
			break;
		}		

	}
	
	public void generateE(List<Integer> output) {
		// E -> num = num
		output.add(NUM);
		output.add(EQ);
		output.add(NUM);
	}
}
