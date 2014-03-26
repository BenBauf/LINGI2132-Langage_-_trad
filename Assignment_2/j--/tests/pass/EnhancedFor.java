package pass;
import java.lang.System;

public class EnhancedFor {

	public int enhancedForIntArray(int[] table){
		int i = 0;
		for(int a : table){
			i = i + a;
		}
		return i; 
	}
}