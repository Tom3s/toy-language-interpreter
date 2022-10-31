package Model.Statements;

import Model.ProgramState;
import Model.Expressions.GenericExpression;

public class IfStatement implements GenericStatement {

    GenericExpression expression;
    GenericStatement thenStatement;
    GenericStatement elseStatement;

    public IfStatement(GenericExpression expression, GenericStatement thenStatement, GenericStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        return programState;
    }
    
    @Override
    public String toString() {
        return String.format("(IF(%s)THEN(%s)ELSE(%s))", this.expression.toString(), this.thenStatement.toString(), this.elseStatement.toString());
    }
}
