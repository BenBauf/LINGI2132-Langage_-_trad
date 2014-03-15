package pass;

public class EnhancedFor {

	public int loop1(){
		int[] t = {1,2,3,4,5};
		int i = 0;
		for(int a : t){
			i = i+1;
		}
		return i; //should be 5
	}
	
	public int loop2(){
		char[] empty = {};
		int i = 0;
		for(char a:empty){
			empty[0] = 'b';
			i = i+1;
		}
		return i; //should be 0
	}
	
	public boolean loop3(){
		boolean[] bool_t = {true, false, true};
		
		int i=0;
		
		for(boolean b:bool_t){
			if(i==2){
				return b; //shouldbe true
			}
			i = i+1;
		}
		return !bool_t[2];
	}
	
	public int loop4(){
		
		int i = 0;
		char[] char_t = {'a','b'};
		boolean[] bool_t = {true, false, true};
		
		for(char a:char_t){
			for(boolean b:bool_t){
				i = i+1;
			}
		}
		return i; //should be 6*/
	}
}
