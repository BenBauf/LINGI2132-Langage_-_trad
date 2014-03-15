package pass;

public class Ternary{
	
	public int ternary(int a, int b){
		return (a>b)? a:b;
	}
	
	public char ternary(char a, char b){
		return (a==b)? a:b;
	}
		
	public int ternary(boolean condition, int a, int b){
		return condition? a:b;
	}
}
