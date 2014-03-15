package lingi2132;


public class Generator {
	private String outputDir;
	public Generator(String outputDir) {
		this.outputDir = outputDir;
	}
	public void generateClass() {
		
	}
	public static void main(String [] args) {
		Generator gen = new Generator(args[0]);
		gen.generateClass();
	}
}