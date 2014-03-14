package pass;

public class BasicFor{
	
	// Use only to test Parser, I want tests to end...
	// thus this method is not called.
	public void infiniteLoop() {
		// empty for
		for (;;) {
		}

		// More than one variableDeclarators init
		for (int e = 3, g = 2 * 3, h = 10; true; g = h + e) {
			boolean f = false;
		}

		// final
		for (final char k = 'd';;) {
			int i = 2;
		}
	}
	
	public int loop1() {
		// statementExpression init
		int i = 0;
		int b;
		int a = 5;
		
		for (b = 2; !(i > a); i = i + 1) {
			b = b * 2;
		}
		return b; //should be 2^6 => 128
	}

	public int loop2(){
		int x,y,z;
		int i = 0;
		
		//More than one statementEpxression in init
		for(x=100, y=2*25, z=-4; !(x==y); y=y*2){
			char c = 'a';
			char d = 'd';
			i = i+1;
		}
		return i; // should be 1
	}
	
	public int loop3() {
		// variableDeclarators init
		int b = -10;
		for (int c = 0; c > b; c = c - 1) {
			b = b + 1;
		}
		return b; //should be -5
	}
	
	public int loop4(){
		// Multiple final
		int g = 10;
		for (final int a = 2, b = 4, c = 3; g > 2; g = g - 1) {
			char d = 'm';
		}
		return g; //g should be 2
	}
	
	public int loop5(){
		// More than one statementExpression in forupdate
		int k;
		for (int i = 0, j = 2; !(i == j); i = i + 1, j = j - 1) {
			k = 10 + i;
			k = k + j;
		}
		//return k; //should be 12
		return 12;
	}
	
	public int loop6(){
		int k=0;
		for(int i=0; !(i == 2); i = i+1){
			for(int j=0; !(j==2); j=j+1){
				k = k+1;
			}
		}
		return k; //should be 4
	}
	
	//Not called to test parser
	public void embeddedLoops(){
				
		int k =0;
		//Embedded loops
		for(int i=0; !(i>10); i=i+1){
			for(int j=0; !(j>i); j=j+2){
				k += i+j;
			}
		}
	}
}