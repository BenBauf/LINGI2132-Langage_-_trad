package grammar;

/**
 * @author pschaus
 * DO NOT MODIFY
 */
public class Grammar {
	/**
	 * Grammar
	 * S -> if E then S else S
	 * S -> begin S L
	 * S -> print E
	 * L -> end
	 * L -> ; S L
	 * E -> num = num
	 */
	
	public final static int IF = 1;
	public final static int THEN = 2;
	public final static int ELSE = 3;
	public final static int BEGIN = 4;
	public final static int END = 5;
	public final static int PRINT = 6;
	public final static int SEMI = 7;
	public final static int NUM = 8;
	public final static int EQ = 9;
}
