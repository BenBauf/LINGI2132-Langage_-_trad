package jminusminus;

import static jminusminus.CLConstants.*;
import java.util.ArrayList;
import java.util.Set; 

public class JEnhancedForStatement extends JStatement {

	private JFormalParameter param;
	private JExpression iterable;
	private JStatement body;
	private LocalContext context;
	private JForStatement equiv;

	public JEnhancedForStatement(int line, JFormalParameter param,
			JExpression iterable, JStatement body) {
		super(line);
		this.param = param;
		this.iterable = iterable;
		this.body = body;

	}

	@Override
	public JAST analyze(Context context) {		

		param = (JFormalParameter) param.analyze(context);
		iterable = (JExpression) iterable.analyze(context);
		if (!iterable.type().isArray()) {
			JAST.compilationUnit.reportSemanticError(line,
					"Iterable is not an array");
		}
		param.type().mustMatchExpected(line, iterable.type().componentType());


		this.context = new LocalContext(context);

		int offset =  this.context.nextOffset();
            	LocalVariableDefn defn = new LocalVariableDefn(param.type().resolve(
                    this.context), offset);

            	// First, check for shadowing
           	IDefn previousDefn = this.context.lookup(param.name());
            	if (previousDefn != null
                    && previousDefn instanceof LocalVariableDefn) {
                JAST.compilationUnit.reportSemanticError(line,
                        "The name " + param.name()
                                + " overshadows another local variable.");
            }

            // Then declare it in the local context
            this.context.addEntry(param.line(), param.name(), defn);

		String var = "_i";
		int i = 0;
		while (this.context.lookup(var) != null) {
			var = "_i" + i;
			i++;
		}

		JForInit init;
		JExpression condition;
		ArrayList<JStatement> update;
		JStatement body;

		// forInit
		JVariableDeclaration declaration;
		JVariableDeclarator declarator = new JVariableDeclarator(line, var,
				Type.INT, new JLiteralInt(line, "0"));
		ArrayList<JVariableDeclarator> decl = new ArrayList<JVariableDeclarator>();
		decl.add(declarator);
		declaration = new JVariableDeclaration(line, new ArrayList<String>(),
				decl);
		init = new JForInitVarDeclaration(line, declaration);

		// condition
		JExpression lhs, rhs;
		lhs = new JFieldSelection(line, iterable, "length");
		rhs = new JVariable(line, var);
		condition = new JGreaterThanOp(line, lhs, rhs);

		// forUpdate
		lhs = new JVariable(line, var);
		rhs = new JLiteralInt(line, "1");
		update = new ArrayList<JStatement>();
		update.add(new JPlusAssignOp(line, lhs, rhs));

		// body
		ArrayList<JStatement> stmnts = new ArrayList<JStatement>();
		lhs = new JVariable(line, param.name());
		rhs = new JArrayExpression(line, iterable, new JVariable(line, var));
		JStatement asgnmnt = new JAssignOp(line, lhs, rhs);

		stmnts.add(asgnmnt);
		stmnts.add(this.body);
		body = new JBlock(line, stmnts);
		
		equiv = new JForStatement(line, init, condition, update, body);
		equiv = (JForStatement) equiv.analyze(this.context);

		return this;
	}

	@Override
	public void codegen(CLEmitter output) {
		equiv.codegen(output);
	}

	@Override
	public void writeToStdOut(PrettyPrinter p) {
		p.printf("<JEnhancedForStatement>\n");
		p.indentRight();
		p.printf("<Recepient>\n");
		p.indentRight();
		param.writeToStdOut(p);
		p.indentLeft();
		p.printf("</Recipient>\n");
		p.printf("<Iterable>\n");
		p.indentRight();
		iterable.writeToStdOut(p);
		p.indentLeft();
		p.printf("</Iterable>\n");
		p.printf("<Body>\n");
		p.indentRight();
		body.writeToStdOut(p);
		p.indentLeft();
		p.printf("</Body>\n");
		p.indentLeft();
		p.printf("</JEnhancedForStatement>\n");
	}

}
