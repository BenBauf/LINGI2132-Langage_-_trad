package jminusminus;

import static jminusminus.CLConstants.GOTO;

public class JConditionalExpression extends JExpression {

	private JExpression condition;

	private JExpression thenPart;

	private JExpression elsePart;

	public JConditionalExpression(int line, JExpression condition,
			JExpression thenPart, JExpression elsePart) {
		super(line);
		this.condition = condition;
		this.thenPart = thenPart;
		this.elsePart = elsePart;
	}

	@Override
	public JExpression analyze(Context context) {
		condition = (JExpression) condition.analyze(context);
		thenPart = (JExpression) thenPart.analyze(context);
		elsePart = (JExpression) elsePart.analyze(context);
		condition.type().mustMatchExpected(line(),Type.BOOLEAN);
		thenPart.type().mustMatchExpected(line(),elsePart.type());
		type = thenPart.type();
		return this;
	}

	@Override
	public void codegen(CLEmitter output) {
		String endLabel = output.createLabel();
        String elseLabel = output.createLabel();
        condition.codegen(output, elseLabel, false);
        
        thenPart.codegen(output);
        output.addBranchInstruction(GOTO, endLabel);
        
        output.addLabel(elseLabel);
        elsePart.codegen(output);
        output.addLabel(endLabel);
	}

	public void writeToStdOut(PrettyPrinter p) {
		p.printf("<JConditionalExpression line=\"%d\">\n", line());
		p.indentRight();
		p.printf("<TestExpression>\n");
		p.indentRight();
		condition.writeToStdOut(p);
		p.indentLeft();
		p.printf("</TestExpression>\n");
		p.printf("<ThenExpression>\n");
		p.indentRight();
		thenPart.writeToStdOut(p);
		p.indentLeft();
		p.printf("</ThenExpression>\n");
		p.printf("<ElseExpression>\n");
		p.indentRight();
		elsePart.writeToStdOut(p);
		p.indentLeft();
		p.printf("</ElseExpression>\n");
		p.indentLeft();
		p.printf("</JConditionalExpression>\n");
	}

}
