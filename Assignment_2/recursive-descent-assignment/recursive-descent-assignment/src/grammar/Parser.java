package grammar;


/**
 * @author pschaus, Benoit Baufays, Julien Colmonts
 * 
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
	 * @param input tab contains all input
	 * @return true if the input is follows the syntax of the grammar, 
	 *         i.e. it corresponds to a valid derivation, false otherwise
	 */
	public boolean parse(Integer[] input) {	
		int soluce = parseS(input, 0);
		return soluce != -1 && soluce == input.length;
	}

	/**
	 * Parse the S grammar
	 * @param input the flux of input
	 * @param pos the index where we begin the analyze
	 * @return the pos after the S expression, -1 otherwise
	 */
	private int parseS(Integer[] input, int pos){
		if (!(size(input, pos, 1))) {
				return -1;
		}
		switch(input[pos]) {
		case Grammar.IF:
			return parseIf(input, pos);
		case Grammar.BEGIN:
			return parseBegin(input, pos);
		case Grammar.PRINT:
			return parsePrint(input, pos);
		default:
				return -1;
		}
	}

	/**
	 * Parse the L grammar
	 * @param input the flux of input
	 * @param pos the index where we begin the analyze
	 * @return the pos after the L expression, -1 otherwise
	 */
	private int parseL(Integer[] input, int pos) {
		if (!(size(input, pos, 1))) {
			return -1;
		}
		switch(input[pos]) {
		case Grammar.END:
			return parseEnd(input, pos);
		case Grammar.SEMI:
			return parseSemi(input, pos);
		default:
			return -1;
		}
	}
	
	/**
	 * Parse the E grammar
	 * @param input the flux of input
	 * @param pos the index where we begin the analyze
	 * @return the pos after the E expression, -1 otherwise
	 */
	private int parseE(Integer[] input, int pos) {
		if (size(input, pos, 3) && input[pos].equals(Grammar.NUM) && input[pos+1].equals(Grammar.EQ) && input[pos+2].equals(Grammar.NUM)) {
			return pos + 3;
		}
		return -1;
	}
	
	/**
	 * Parse the If grammar
	 * @param input the flux of input
	 * @param pos the index where we begin the analyze
	 * @return the pos after the if expression, -1 otherwise
	 */
	private int parseIf(Integer[] input, int pos){
		if (size(input, pos, 2) && input[pos].equals(Grammar.IF)) {
			int posE = parseE(input, pos + 1);
			if (posE != -1 && size(input, posE, 2) && input[posE].equals(Grammar.THEN)) {
				int posS = parseS(input, posE + 1);
				if (posS != -1 && size(input, posS, 2) && input[posS].equals(Grammar.ELSE)) {
					return parseS(input, posS + 1);
				}
			}
		}
		return -1;
	}

	/**
	 * Parse the begin grammar
	 * @param input the flux of input
	 * @param pos the index where we begin the analyze
	 * @return the pos after the begin expression, -1 otherwise
	 */
	private int parseBegin(Integer[] input, int pos) {
		if (size(input, pos, 2) && input[pos].equals(Grammar.BEGIN)) {
			int posS = parseS(input, pos + 1);
			if (posS != -1) {
				return parseL(input, posS);
			}
		}
		return -1;
	}

	/**
	 * Parse the print grammar
	 * @param input the flux of input
	 * @param pos the index where we begin the analyze
	 * @return the pos after the print expression, -1 otherwise
	 */
	private int parsePrint(Integer[] input, int pos) {
		if (size(input, pos, 2) && input[pos].equals(Grammar.PRINT)) {
			return parseE(input, pos + 1);
		}
		return -1;
	}

	/**
	 * Parse the end grammar
	 * @param input the flux of input
	 * @param pos the index where we begin the analyze
	 * @return the pos after the end expression, -1 otherwise
	 */
	private int parseEnd(Integer[] input, int pos) {
		if (size(input, pos, 1) && input[pos].equals(Grammar.END)) {
			return pos + 1;
		}
		return -1;
	}
	
	/**
	 * Parse the Semi grammar
	 * @param input the flux of input
	 * @param pos the index where we begin the analyze
	 * @return the pos after the semi expression, -1 otherwise
	 */
	private int parseSemi(Integer[] input, int pos) {
		if (size(input, pos, 3) && input[pos].equals(Grammar.SEMI)) {
			int posS = parseS(input, pos + 1);
			if (posS != -1) {
				return parseL(input, posS);
			}
		}
		return -1;
	}

	/**
	 * Parse the size grammar
	 * @param input the flux of input
	 * @param pos the index where we begin the analyze
	 * @return the pos after the size expression, -1 otherwise
	 */
	private boolean size(Integer[] input, int pos, int size) {
		return input.length >= pos + size;
	}

}
