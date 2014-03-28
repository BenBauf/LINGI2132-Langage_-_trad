package jminusminus;

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

	@Override
	public JExpression analyze(Context context) {
		testExpression = (JExpression) testExpression.analyze(context);
		thenExpression = (JExpression) thenExpression.analyze(context);
		elseExpression = (JExpression) elseExpression.analyze(context);
		testExpression.type().mustMatchExpected(line(),Type.BOOLEAN);
		type = thenExpression.type();
		return this;
	}
	
	@Override
	public void codegen(CLEmitter output) {
		String endLabel = output.createLabel();
        String elseLabel = output.createLabel();
        testExpression.codegen(output, elseLabel, false);
        
        thenExpression.codegen(output);
        output.addBranchInstruction(jminusminus.CLConstants.GOTO, endLabel);
        
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
