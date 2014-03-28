package jminusminus;

public class JForInitVarDeclaration extends JForInitExpression{
	
	private JVariableDeclaration init;

	public JForInitVarDeclaration(int line, JVariableDeclaration declInit) {
		super(line);
		this.init = declInit;
	}

	@Override
	public JAST analyze(Context context) {
		init = (JVariableDeclaration) init.analyze(context);
		return this;
	}

	@Override
	public void codegen(CLEmitter output) {
		init.codegen(output);
	}

	@Override
	public void writeToStdOut(PrettyPrinter p) {
		p.printf("<JForInit>\n");
		p.indentRight();
		p.printf("<InitDeclarations>\n");
		p.indentRight();
		init.writeToStdOut(p);
		p.indentLeft();
		p.printf("</InitDeclarations>\n");
		p.indentLeft();
		p.printf("</JForInit>\n");
	}
}
