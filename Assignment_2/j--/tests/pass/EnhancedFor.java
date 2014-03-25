package pass;
import java.lang.System;

public class EnhancedFor {

	public int loop1(){
		int[] t = { 3, 4, 5, 6, 7 };
		int i = 0;
		for(int a : t){
			i = i+1;
		}
		return i; //should be 5
	}

}