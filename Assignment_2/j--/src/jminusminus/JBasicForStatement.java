package jminusminus;

import static jminusminus.CLConstants.GOTO;

import java.util.ArrayList;

public class JBasicForStatement extends JStatement {

	private JForInitExpression init;
	private JExpression test;
	private JStatement body;
	private ArrayList<JStatement> forUpdate;
	private LocalContext context;
	
	/**
     * Construct an AST node for a basic for statement 	
     */

	public JBasicForStatement(int line, JForInitExpression forInit, JExpression condition, ArrayList<JStatement> forUpdate, JStatement body) {
		super(line);
		this.init = forInit;
		this.test = condition;
		this.forUpdate = forUpdate;
		this.body = body;
	}

	public JStatement analyze(Context context) {
		this.context = new LocalContext(context);
		if(init != null){
			init = (JForInitExpression) init.analyze(this.context);
		}

		if(test != null){
			test = (JExpression) test.analyze(this.context);
			test.type().mustMatchExpected(line(),Type.BOOLEAN);
		}
		if(forUpdate != null){
			for(JStatement jstatement : forUpdate)
				jstatement = (JStatement) jstatement.analyze(this.context);
		}


		body = (JStatement) body.analyze(this.context);
		return this;
	}

	public void codegen(CLEmitter output) {
		String testS = output.createLabel();
        String out = output.createLabel();
        
		if(init != null){
			init.codegen(output);
		}
        
        output.addLabel(testS);
        if (test != null){
        	test.codegen(output, out, false);
        }
        
        body.codegen(output);
        if(forUpdate != null){
        	for(JStatement jstatement: forUpdate)
        		jstatement.codegen(output);
        }
        
        output.addBranchInstruction(GOTO, testS);
        output.addLabel(out);

	}

	public JForInitExpression getInit() {
		return this.init;
	}
	public ArrayList<JStatement> getForUpdate() {
		return this.forUpdate;
	}

	public JExpression getCondition() {
		return this.test;
	}

	public JStatement getBidy() {
		return this.body;
	}
	
	public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JForStatement line=\"%d\">\n", line());
        p.indentRight();
        if (init != null) {
	        p.printf("<ForInit>\n");
	        p.indentRight();
	        init.writeToStdOut(p);
	        p.indentLeft();
	        p.printf("</ForInit>\n");
        }
        if(test != null ) {
	        p.printf("<JExpression>\n");
	        p.indentRight();
	        test.writeToStdOut(p);
	        p.indentLeft();
	        p.printf("</JExpression>\n");
        }
        if (init != null){
	        p.printf("<ForUpdate>\n");
	        p.indentRight();
	        for(JStatement update : forUpdate)
	        	update.writeToStdOut(p);
	        p.indentLeft();
	        p.printf("</ForUpdate>\n");
        }
        p.printf("<JStatement>\n");
        p.indentRight();
        body.writeToStdOut(p);
        p.indentLeft();
        p.printf("</JStatement>\n");
        p.indentLeft();
        p.printf("</JForStatement>\n");

	}

}
