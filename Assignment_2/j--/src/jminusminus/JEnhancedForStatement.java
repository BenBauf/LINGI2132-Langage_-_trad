package jminusminus;

import java.util.ArrayList;
import static jminusminus.CLConstants.GOTO;

public class JEnhancedForStatement extends JStatement {

	JFormalParameter param;
	JExpression expression;
	JStatement statement;


	JBasicForStatement basicForStatement;


	/**
     * Construct an AST node for a enhanced for statement
     */

	protected JEnhancedForStatement(int line, JFormalParameter param, JExpression expression, JStatement statement) {
		super(line);
		this.param = param;
		this.expression = expression;
		this.statement = statement;

		// le enhanced for étant un sucre syntaxique, on le transforme en un for basic
		
		//création de l'init qui sera l'index du tableau
		JVariable index = new JVariable(line, "variable"+Math.random()); // need to be random
		ArrayList<JVariableDeclarator> vars = new ArrayList<JVariableDeclarator>(2);
		vars.add(new JVariableDeclarator(line, index.name(), Type.INT, new JLiteralInt(line, "0")));
		vars.add(new JVariableDeclarator(line, param.name(), param.type(), null));
		JVariableDeclaration declaration = new JVariableDeclaration(line, new ArrayList<String>(), vars);
		JForInitVarDeclaration init = new JForInitVarDeclaration(line, declaration);
		
		//création du test
		JExpression test = new JLogicalNotOp(line, new JEqualOp(line, index, new JFieldSelection(line, expression, "length")));

		//création du forupdate qui va incrémenter l'index
		ArrayList<JStatement> forUpdate = new ArrayList<JStatement>();
		// incrémentation index
		forUpdate.add(new JPreIncrementOp(line, index));
		((JExpression) forUpdate.get(0)).isStatementExpression = true;
		// et on change la variable temporaire
		forUpdate.add(new JAssignOp(line, new JVariable(line, param.name()), new JArrayExpression(line, expression, index)));
		((JExpression) forUpdate.get(1)).isStatementExpression = true;

		this.basicForStatement = new JBasicForStatement(line,init , test, forUpdate, statement);
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
		this.basicForStatement.getForUpdate().get(1).codegen(output);
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
