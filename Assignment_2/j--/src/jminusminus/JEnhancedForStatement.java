package jminusminus;

import java.util.ArrayList;
import static jminusminus.CLConstants.GOTO;

public class JEnhancedForStatement extends JStatement {

	JFormalParameter param;
	JExpression expression;
	JStatement statement;


	JBasicForStatement basicForStatement;



	protected JEnhancedForStatement(int line, JFormalParameter param, JExpression expression, JStatement statement)
	{
		super(line);
		this.param = param;
		this.expression = expression;
		this.statement = statement;

		// casting enhanced one into basic one

		//ForInit (Iterator i + temporary variable  (...) = tab[i])
		//ArrayList<JStatement> forInit = new ArrayList<JStatement>();
		JVariable index = new JVariable(line, "blah"+Math.random()); // need to be random
		JVariable fparam = new JVariable(line, param.name());
		ArrayList<JVariableDeclarator> vars = new ArrayList<JVariableDeclarator>(2);
		vars.add(new JVariableDeclarator(line, index.name(), Type.INT, new JLiteralInt(line, "0"), index));
		vars.add(new JVariableDeclarator(line, param.name(), param.type(), null, fparam));
		//forInit.add(new JVariableDeclaration(line, new ArrayList<String>(), vars));
		JVariableDeclaration declaration = new JVariableDeclaration(line, new ArrayList<String>(), vars);
		JForInitVarDeclaration init = new JForInitVarDeclaration(line, declaration);
		//Condition
		JExpression condition = new JLogicalNotOp(line, new JEqualOp(line, index, new JFieldSelection(line, expression, "length")));

		//forUpdate (Update iterator i & temporary variable )
		ArrayList<JStatement> forUpdate = new ArrayList<JStatement>();
		// incrementing i ...
		forUpdate.add(new JPreIncrementOp(line, index));
		((JExpression) forUpdate.get(0)).isStatementExpression = true;
		// assigning temporary variable
		forUpdate.add(new JAssignOp(line, fparam, new JArrayExpression(line, expression, index)));
		((JExpression) forUpdate.get(1)).isStatementExpression = true;

		this.basicForStatement = new JBasicForStatement(line,init , condition, forUpdate, statement);
	}

	public JStatement analyze(Context context) {
		basicForStatement = (JBasicForStatement) basicForStatement.analyze(context);
		return this;
	}

	public void codegen(CLEmitter output) {
		String test = output.createLabel();
		String out  = output.createLabel();
		this.basicForStatement.getInit().codegen(output);
		output.addLabel(test);
		this.basicForStatement.getCondition().codegen(output, out, false);

		this.basicForStatement.getForUpdate().get(1).codegen(output); // need to assign element before updating i
		statement.codegen(output);
		this.basicForStatement.getForUpdate().get(0).codegen(output);
		output.addBranchInstruction(GOTO, test);
		output.addLabel(out);
	}
	
	
	public void writeToStdOut(PrettyPrinter p){		
        p.printf("<JEnhancedForStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<JFormalParameter>\n");
        p.indentRight();
        param.writeToStdOut(p);
        p.indentLeft();
        p.printf("</JFormalParameter>\n");
        p.printf("<JExpression>\n");
        p.indentRight();
        expression.writeToStdOut(p);
        p.indentLeft();
        p.printf("</JExpression>\n");
        p.printf("<JStatement>\n");
        p.indentRight();
        statement.writeToStdOut(p);
        p.indentLeft();
        p.printf("</JStatement>\n");
        p.indentLeft();
        p.printf("</JEnhancedForStatement>\n");

	}

}
