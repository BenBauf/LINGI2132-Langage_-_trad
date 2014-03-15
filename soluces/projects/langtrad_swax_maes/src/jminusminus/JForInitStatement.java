package jminusminus;

import java.util.ArrayList;

public class JForInitStatement extends JForInit{
	
	private ArrayList<JStatement> expInit;

	public JForInitStatement(int line, ArrayList<JStatement> expInit) {
		super(line);
		this.expInit = expInit;
	}

	@Override
	public JAST analyze(Context context) {
		for(int i = 0; i < expInit.size(); i++) {
			expInit.set(i, (JStatement) expInit.get(i).analyze(context));
		}
		return this;
	}

	@Override
	public void codegen(CLEmitter output) {
		for(JStatement statement:expInit){
			statement.codegen(output);
		}
	}

	@Override
	public void writeToStdOut(PrettyPrinter p) {
		p.printf("<JForInit>\n");
		p.indentRight();
		p.printf("<InitStatements>\n");
		for (JStatement e : expInit) {
			p.indentRight();
			e.writeToStdOut(p);
			p.indentLeft();
		}
		p.printf("</InitStatements>\n");
		p.indentLeft();
		p.printf("</JForInit>\n");
	}
}
