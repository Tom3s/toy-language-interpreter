package Model.Statements;

import Model.ProgramState;
import Model.Expressions.GenericExpression;

public class PrintStatement implements GenericStatement {
    GenericExpression expression;
    
    public PrintStatement(GenericExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        // TODO print execution
        return null;
    }

    @Override
    public GenericStatement deepCopy() {
        return new PrintStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("print(%s)", this.expression.toString());
    }
}
