package lingi2132;

import java.util.ArrayList;

import static jminusminus.CLConstants.*;
import jminusminus.CLEmitter;



public class Generator {
	private String outputDir;
	public Generator(String outputDir) {
		this.outputDir = outputDir;
	}
	public void generateClass() {
		CLEmitter output = new CLEmitter(true);
		output.destinationDir(outputDir);
		
		ArrayList<String> accessFlags = new ArrayList<>();
		
		accessFlags.add("public");
		accessFlags.add("super");
		output.addClass(accessFlags , "packageOfClassToGenerate/ClassToGenerate", "java/lang/Object", null, false);
		
		accessFlags.clear();
		accessFlags.add("public");
		
		
		output.addMethod(accessFlags, "<init>", "()V", null, false);
		output.addNoArgInstruction(ALOAD_0);
		output.addMemberAccessInstruction(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
		output.addNoArgInstruction(RETURN);
		
		accessFlags.add("static");
		
		output.addMethod(accessFlags, "factorial", "(I)I", null, false);
		output.addNoArgInstruction(ILOAD_0);
		output.addBranchInstruction(IFGT, "ifN");
		output.addNoArgInstruction(ICONST_1);
		output.addNoArgInstruction(IRETURN);
		output.addLabel("ifN");
		output.addNoArgInstruction(ILOAD_0);
		output.addNoArgInstruction(ILOAD_0);
		output.addNoArgInstruction(ICONST_1);
		output.addNoArgInstruction(ISUB);
		output.addMemberAccessInstruction(INVOKESTATIC, "packageOfClassToGenerate/ClassToGenerate", "factorial", "(I)I");
		output.addNoArgInstruction(IMUL);
		output.addNoArgInstruction(IRETURN);
		
		output.addMethod(accessFlags, "maximum", "([I)I", null, false);
		output.addNoArgInstruction(ALOAD_0);
		output.addNoArgInstruction(ICONST_1);
		output.addNoArgInstruction(IALOAD);
		output.addNoArgInstruction(ALOAD_0);
		output.addNoArgInstruction(ICONST_0);
		output.addNoArgInstruction(IALOAD);
		output.addBranchInstruction(IF_ICMPLE, "tabCompare");
		output.addNoArgInstruction(ALOAD_0);
		output.addNoArgInstruction(ICONST_1);
		output.addNoArgInstruction(IALOAD);
		output.addNoArgInstruction(IRETURN);
		output.addLabel("tabCompare");
		output.addNoArgInstruction(ALOAD_0);
		output.addNoArgInstruction(ICONST_0);
		output.addNoArgInstruction(IALOAD);
		output.addNoArgInstruction(IRETURN);
		
		output.addMethod(accessFlags, "main", "([Ljava/lang/String;)V", null, false);
		output.addNoArgInstruction(ALOAD_0);
		output.addNoArgInstruction(ICONST_0);
		output.addNoArgInstruction(AALOAD);
		output.addMemberAccessInstruction(INVOKESTATIC, "java/lang/Integer", "parseInt", "(Ljava/lang/String;)I");
		output.addNoArgInstruction(ISTORE_1);
		output.addNoArgInstruction(ALOAD_0);
		output.addNoArgInstruction(ICONST_1);
		output.addNoArgInstruction(AALOAD);
		output.addMemberAccessInstruction(INVOKESTATIC, "java/lang/Integer", "parseInt", "(Ljava/lang/String;)I");
		output.addNoArgInstruction(ISTORE_2);
		output.addNoArgInstruction(ALOAD_0);
		output.addNoArgInstruction(ICONST_2);
		output.addNoArgInstruction(AALOAD);
		output.addMemberAccessInstruction(INVOKESTATIC, "java/lang/Integer", "parseInt", "(Ljava/lang/String;)I");
		output.addNoArgInstruction(ISTORE_3);
		output.addNoArgInstruction(ICONST_2);
		output.addArrayInstruction(NEWARRAY, "I");
		output.addOneArgInstruction(ASTORE, 4);
		output.addOneArgInstruction(ALOAD, 4);
		output.addNoArgInstruction(ICONST_0);
		output.addNoArgInstruction(ILOAD_2);
		output.addNoArgInstruction(IASTORE);
		output.addOneArgInstruction(ALOAD, 4);
		output.addNoArgInstruction(ILOAD_3);
		output.addNoArgInstruction(IASTORE);
		output.addMemberAccessInstruction(GETSTATIC, "java/lan/System", "out", "Ljava/io/PrintStream;");
		output.addNoArgInstruction(ILOAD_1);
		output.addMemberAccessInstruction(INVOKESTATIC, "packageOfClassToGenerate/ClassToGenerate", "factorial", "(I)I");
		output.addMemberAccessInstruction(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V");
		output.addMemberAccessInstruction(GETSTATIC, "java/lan/System", "out", "Ljava/io/PrintStream;");
		output.addOneArgInstruction(ALOAD, 4);
		output.addMemberAccessInstruction(INVOKESTATIC, "packageOfClassToGenerate/ClassToGenerate", "maximum", "([I)I");
		output.addMemberAccessInstruction(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V");
		output.addNoArgInstruction(RETURN);
		
		
		output.write();
		
	}
	public static void main(String[] args) {
		Generator gen = new Generator(args[0]);
		gen.generateClass();
	}
}