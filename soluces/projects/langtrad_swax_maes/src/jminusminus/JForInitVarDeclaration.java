package jminusminus;

public class JForInitVarDeclaration extends JForInit{
	
	private JVariableDeclaration declInit;

	public JForInitVarDeclaration(int line, JVariableDeclaration declInit) {
		super(line);
		this.declInit = declInit;
	}

	@Override
	public JAST analyze(Context context) {
		declInit = (JVariableDeclaration)declInit.analyze(context);
		return this;
	}

	@Override
	public void codegen(CLEmitter output) {
		declInit.codegen(output);
	}

	@Override
	public void writeToStdOut(PrettyPrinter p) {
		p.printf("<JForInit>\n");
		p.indentRight();
		p.printf("<InitDeclarations>\n");
		p.indentRight();
		declInit.writeToStdOut(p);
		p.indentLeft();
		p.printf("</InitDeclarations>\n");
		p.indentLeft();
		p.printf("</JForInit>\n");
	}
}
