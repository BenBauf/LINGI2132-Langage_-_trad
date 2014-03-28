package jminusminus;

import java.util.ArrayList;

public class JForInitStatement extends JForInitExpression{
	
	private ArrayList<JStatement> init;

	public JForInitStatement(int line, ArrayList<JStatement> expInit) {
		super(line);
		this.init = expInit;
	}

	@Override
	public JAST analyze(Context context) {
		for(int i = 0; i < init.size(); i++) {
			init.set(i, (JStatement) init.get(i).analyze(context));
		}
		return this;
	}

	@Override
	public void codegen(CLEmitter output) {
		for(JStatement statement:init){
			statement.codegen(output);
		}
	}

	@Override
	public void writeToStdOut(PrettyPrinter p) {
		p.printf("<JForInit>\n");
		p.indentRight();
		p.printf("<InitStatements>\n");
		for (JStatement e : init) {
			p.indentRight();
			e.writeToStdOut(p);
			p.indentLeft();
		}
		p.printf("</InitStatements>\n");
		p.indentLeft();
		p.printf("</JForInit>\n");
	}
}
