package packageOfClassToGenerate;

public class ClassToGenerate {

   public static int factorial(int n) {
       if (n <= 0) {
           return 1;
       } else {
           return (n * factorial(n - 1));
       }
   }
   
   public static int maximum(int [] tab) { // ou tab est un tableau de deux elements
	 if (tab[1] > tab[0])
		 return tab[1];
	 else return tab[0];
	}

   public static void main(String[] args) {
	   int n = Integer.parseInt(args[0]);
	   int a = Integer.parseInt(args[1]);
	   int b = Integer.parseInt(args[2]);
	   
	   int[] t = new int[2];
	   t[0]=a;
	   t[1]=b;
   		
	   System.out.println(ClassToGenerate.factorial(n));
   	   System.out.println(ClassToGenerate.maximum(t));
   }
}