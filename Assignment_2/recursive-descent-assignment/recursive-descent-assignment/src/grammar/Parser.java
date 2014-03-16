package grammar;


import static grammar.Grammar.*;


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
	 * 
	 * @return true if the input is follows the syntax of the grammar, 
	 *         i.e. it corresponds to a valid derivation, false otherwise
	 */
	public boolean parse(Integer[] input) {	
		int soluce=parseS(input,0);
		return soluce!=-1&&soluce==input.length;
		//return true;
	}
	
	private int parseS(Integer[] input, int pos){
		if (!(size(input,pos,1))){
				return -1;
		}
		switch(input[pos]){
		case Grammar.IF:
			return parseIf(input,pos);
		case Grammar.BEGIN:
			return parseBegin(input,pos);
		case Grammar.PRINT:
			return parsePrint(input,pos);
		}
		return -1;
		/*int posIf=parseIf(input,pos);
		if(posIf==-1){
			int posB=parseBegin(input,pos);
			if(posB==-1){
				return parsePrint(input,pos);
			}
			else{
				return posB;
			}
		}
		else{
			return posIf;
		}*/
	}
	private int parseL(Integer[] input,int pos){
		if (!(size(input,pos,1))){
			return -1;
		}
		switch(input[pos]){
		case Grammar.END:
			return parseEnd(input,pos);
		case Grammar.SEMI:
			return parseSemi(input,pos);
		}
		return -1;
		/*int posEnd=parseEnd(input,pos);
		if(posEnd==-1){
			return parseSemi(input,pos);
		}
		else{
			return posEnd;
		}*/
	}
	
	private int parseE(Integer[] input,int pos){
		if(size(input,pos,3)&&input[pos].equals(Grammar.NUM)&&input[pos+1].equals(Grammar.EQ)&&input[pos+2].equals(Grammar.NUM)){
			return pos+3;
		}
		return -1;
	}
	
	private int parseIf(Integer[] input, int pos){
		if(size(input,pos,2)&&input[pos].equals(Grammar.IF)){
			int posE= parseE(input, pos+1);
			if(posE!=-1&&size(input,posE,2)&&input[posE].equals(Grammar.THEN)){
				int posS=parseS(input, posE+1);
				if(posS!=-1&&size(input,posS,2)&&input[posS].equals(Grammar.ELSE)){
					return parseS(input, posS+1);
				}
			}
		}
		return -1;
	}
	
	private int parseBegin(Integer[] input, int pos){
		if(size(input,pos,2)&&input[pos].equals(Grammar.BEGIN)){
			int posS=parseS(input, pos+1);
			if(posS!=-1){
				return parseL(input,posS);
			}
			
		}
		return -1;
	}
	
	private int parsePrint(Integer[] input,int pos){
		if(size(input,pos,2)&&input[pos].equals(Grammar.PRINT)){
			return parseE(input, pos+1);
		}
		return -1;
	}

	private int parseEnd(Integer[] input,int pos){
		if(size(input,pos,1)&&input[pos].equals(Grammar.END)){
			return pos+1;
		}
		return -1;
	}
	private int parseSemi(Integer[] input, int pos){
		if(size(input, pos,3)&&input[pos].equals(Grammar.SEMI)){
			int posS=parseS(input,pos+1);
			if(posS!=-1){
				return parseL(input,posS);
			}
		}
		return -1;
	}
	
	private boolean size(Integer[] input,int pos, int size){
		return input.length>=pos+size;
	}
	

}
