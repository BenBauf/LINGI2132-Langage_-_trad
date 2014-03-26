package jminusminus;

import static jminusminus.CLConstants.GOTO;

public class JConditionalExpression extends JExpression {
	
	
	/** the test expression of the conditional expression */
	private JExpression testExpression;

	/** the then expression of the conditional expression */
	private JExpression thenExpression;

	/** the else expression of the conditional expression */
	private JExpression elseExpression;
	
	/**
     * Construct an AST node for a conditional expression, for example (a > b) ? a:b
     * 	
     * @param line
     *            line in which the conditional expression occurs in the
     *            source file.
     * @param testE
     *            the test expression of the conditional expression.
     * @param thenE
     *            the then expression of the conditional expression.
     * @param elseE
     *            the else expression of the conditional expression.
     */

	protected JConditionalExpression(int line, JExpression testE, JExpression thenE, JExpression elseE) {
		super(line);
		this.testExpression = testE;
		this.thenExpression = thenE;
		this.elseExpression = elseE;
	}
	
    /**
     * Analyzing an conditional expression means analyzing its test and the two parts then and else. 
     * We also check that the types of a conditional expression match.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

	@Override
	public JExpression analyze(Context context) {
		testExpression = (JExpression) testExpression.analyze(context);
		thenExpression = (JExpression) thenExpression.analyze(context);
		elseExpression = (JExpression) elseExpression.analyze(context);
		testExpression.type().mustMatchExpected(line(),Type.BOOLEAN);
		//thenExpression.type().mustMatchExpected(line(),elseExpression.type());
		type = thenExpression.type();
		return this;
	}
	
    /**
     * Branching code generation for conditional expression.
     * we have, first, the test branch, follow by the then and a goto(to doesn't execute the else part) and the else part.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

	@Override
	public void codegen(CLEmitter output) {
		String endLabel = output.createLabel();
        String elseLabel = output.createLabel();
        testExpression.codegen(output, elseLabel, false);
        
        thenExpression.codegen(output);
        output.addBranchInstruction(GOTO, endLabel);
        
        output.addLabel(elseLabel);
        elseExpression.codegen(output);
        output.addLabel(endLabel);
	}

	
	public void writeToStdOut(PrettyPrinter p) {
		p.printf("<JConditionalExpression line=\"%d\">\n", line());
		p.indentRight();
		p.printf("<TestExpression>\n");
		p.indentRight();
		testExpression.writeToStdOut(p);
		p.indentLeft();
		p.printf("</TestExpression>\n");
		p.printf("<ThenExpression>\n");
		p.indentRight();
		thenExpression.writeToStdOut(p);
		p.indentLeft();
		p.printf("</ThenExpression>\n");
		p.printf("<ElseExpression>\n");
		p.indentRight();
		elseExpression.writeToStdOut(p);
		p.indentLeft();
		p.printf("</ElseExpression>\n");
		p.indentLeft();
		p.printf("</JConditionalExpression>\n");
	}

}
