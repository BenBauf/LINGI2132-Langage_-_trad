package lingi2132;

import java.util.ArrayList;

import jminusminus.CLConstants;
import jminusminus.CLEmitter;



public class Generator {
	private String outputDir;
	public Generator(String outputDir) {
		this.outputDir = outputDir;
	}
	public void generateClass() {
		CLEmitter output = new CLEmitter(true);
		output.destinationDir(outputDir);
		ArrayList<String> mods = new ArrayList<>();
		mods.add("public"); // Add static ? -_-
		output.addClass(mods , "ClassToGenerate", "java/lang/Object", null, false);
		
		output.addMethod(mods, "factorial", "(I)I", null, false);
		output.addBranchInstruction(CLConstants.IFLE, "ns0");
		output.addNoArgInstruction(CLConstants.IRETURN);
		
		output.addMethod(mods, "maximum", "(I[])I", null, false);
		output.addNoArgInstruction(CLConstants.IRETURN);
		
		output.addMethod(mods, "main", "(S[])", null, false);
		output.addField(mods, "n", "I", false, 0); // Add Integer.parseInt...
		output.addField(mods, "a", "I", false, 0);
		output.addField(mods, "b", "I", false, 0);
		
		output.addArrayInstruction(CLConstants.NEWARRAY, "I");
		
		
		output.addNoArgInstruction(CLConstants.RETURN);
		
		output.write();
		
	}
	public static void main(String [] args) {
		Generator gen = new Generator(args[0]);
		gen.generateClass();
	}
}