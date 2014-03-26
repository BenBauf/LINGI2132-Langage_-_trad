package pass;

import java.lang.System;

public class ConditionalTestExpression{
	
	public int checkIf(int x, int y){
		return (x>y)?x:y;
	}
	
	public int checkIfMultiTypes(int x, int y){
		return (x>y)?x:'a';
	}
	
}
