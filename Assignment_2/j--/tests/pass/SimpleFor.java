package pass;
import java.lang.System;

public class SimpleFor {
	
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

		int i = 0;
		for(int x=100, y=2*25, z=-4, w; !(x==y); y=y*2){			
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
		// More than one statementExpression in forupdate and no init
		int k=0;
		int i = 0, j = 2;
		for (; !(i == j); i = i + 1, j = j - 1) {
			k = 10 + i;
			k = k + j;
		}
		int z = 0;
		for(;;){
			if(z > 10){
				return k;
			}
			z= z + 10;
		}
		return k; //should be 12
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
}