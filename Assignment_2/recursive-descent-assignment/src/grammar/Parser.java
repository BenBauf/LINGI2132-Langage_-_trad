package grammar;


import static grammar.Grammar.*;


/**
 * @author pschaus
 */
public class Parser {

	/**
	 * Grammar to parse
	 * S -> if E then S else S
	 * S -> begin S L
	 * S -> print E
	 * L -> end
	 * L -> ; S L
	 * E -> num = num
	 * 
	 * @return true if the input is follows the syntax of the grammar, 
	 *         i.e. it corresponds to a valid derivation, false otherwise
	 */
	public boolean parse(Integer[] input) {		
		return true;
		// TODO edit this class such that this method returns the correct result
	}
	

}
