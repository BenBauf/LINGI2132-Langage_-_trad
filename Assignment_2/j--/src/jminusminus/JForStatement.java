package jminusminus;

import jminusminus.JForInitExpression;
import static jminusminus.CLConstants.*;

import java.util.ArrayList;

public class JForStatement extends JStatement {

	private JForInitExpression init;
	private JExpression condition;
	private ArrayList<JStatement> forUpdate;
	private JStatement body;
	private LocalContext context;

	public JForStatement(int line, JForInitExpression forInit, JExpression condition, ArrayList<JStatement> forUpdate, JStatement body) {
		super(line);
		this.init = forInit;
		this.condition = condition;
		this.forUpdate = forUpdate;
		this.body = body;
	}

	@Override
	public JStatement analyze(Context context) {
		this.context = new LocalContext(context);
		if(init != null){
			init = (JForInitExpression) init.analyze(this.context);
		}
		if(condition != null){
			condition = (JExpression) condition.analyze(this.context);
			condition.type().mustMatchExpected(line(),Type.BOOLEAN);
		}
		if(forUpdate != null){
			for(int i = 0; i < forUpdate.size(); i++) {
				forUpdate.set(i, (JStatement) forUpdate.get(i).analyze(this.context));
			}
		}
		
		body = (JStatement) body.analyze(this.context);
		return this;
	}

	@Override
	public void codegen(CLEmitter output) {
		if(init != null){
			init.codegen(output);
		}
		
		String loopLabel = output.createLabel();
		String endLabel = output.createLabel();
        
		output.addLabel(loopLabel);
		
		if(condition != null){
			condition.codegen(output, endLabel, false);
		}
		
		if(forUpdate != null){
			for(JStatement statement:this.forUpdate){
				statement.codegen(output);
			}
		}
		
		body.codegen(output);
		output.addBranchInstruction(GOTO, loopLabel);
		output.addLabel(endLabel);
	}

	@Override
	public void writeToStdOut(PrettyPrinter p) {
		p.printf("<JForStatement line=\"%d\">\n", line());
		p.indentRight();
		if (init != null) {
			p.printf("<Init>\n");
			p.indentRight();
			init.writeToStdOut(p);
			p.indentLeft();
			p.printf("</Init>\n");
		}
		if (condition != null) {
			p.printf("<TestExpression>\n");
			p.indentRight();
			condition.writeToStdOut(p);
			p.indentLeft();
			p.printf("</TestExpression>\n");
		}
		if (forUpdate != null) {
			p.printf("<UpdateStatements>\n");
			for (JStatement e : forUpdate) {
				p.indentRight();
				e.writeToStdOut(p);
				p.indentLeft();
			}
			p.printf("</UpdateStatements>\n");
		}
		p.printf("<Body>\n");
		p.indentRight();
		body.writeToStdOut(p);
		p.indentLeft();
		p.printf("</Body>\n");
		p.indentLeft();
		p.printf("</JForStatement>\n");
	}

}
