package jminusminus;

import static jminusminus.CLConstants.GOTO;

import java.util.ArrayList;

public class JBasicForStatement extends JStatement {

	/*ArrayList<JStatement> forInit;
	private ArrayList<JStatement> forUpdate;
	JExpression expression;
	JStatement statement;
	JBlock block;
	*/
	private JForInitExpression init;
	private JExpression condition;
	private JStatement body;
	private ArrayList<JStatement> forUpdate;
	private LocalContext context;
	
	/*protected JBasicForStatement(int line, JForInitExpression forInit, JExpression expression, ArrayList<JStatement> forUpdate, JStatement statement) 
	{
		super(line);
		this.init = forInit;
		this.condition = condition;
		this.forUpdate = forUpdate;
		this.body = body;
	}*/
	
	public JBasicForStatement(int line, JForInitExpression forInit, JExpression condition, ArrayList<JStatement> forUpdate, JStatement body) {
		super(line);
		this.init = forInit;
		this.condition = condition;
		this.forUpdate = forUpdate;
		this.body = body;
	}


	/*protected JBasicForStatement(int line, ArrayList<JStatement> forInit, JExpression expression, ArrayList<JStatement> forUpdate, JStatement statement) 
	{
		super(line);


		this.forInit = forInit;
		this.forUpdate = forUpdate;
		this.statement = statement;
		this.expression = expression;
	}*/

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
			for(JStatement jstatement : forUpdate)
				jstatement = (JStatement) jstatement.analyze(this.context);
		}


		body = (JStatement) body.analyze(this.context);
		return this;
	}


	public void codegen(CLEmitter output) {
		String test = output.createLabel();
        String out = output.createLabel();
        
		if(init != null){
			init.codegen(output);
		}

        
        output.addLabel(test);
        if (condition != null){
        	condition.codegen(output, out, false);
        }
        
        body.codegen(output);
        for(JStatement jstatement: forUpdate)
        	jstatement.codegen(output);
        
        output.addBranchInstruction(GOTO, test);
        output.addLabel(out);

	}

	public JForInitExpression getInit() {
		return this.init;
	}
	public ArrayList<JStatement> getForUpdate() {
		return this.forUpdate;
	}

	public JExpression getCondition() {
		return this.condition;
	}

	public JStatement getBidy() {
		return this.body;
	}
	public void writeToStdOut(PrettyPrinter p)
	{
        p.printf("<JForStatement line=\"%d\">\n", line());
        p.indentRight();
        if (init != null) {
	        p.printf("<ForInit>\n");
	        p.indentRight();
	        init.writeToStdOut(p);
	        p.indentLeft();
	        p.printf("</ForInit>\n");
        }
        if(condition != null ) {
	        p.printf("<JExpression>\n");
	        p.indentRight();
	        condition.writeToStdOut(p);
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
