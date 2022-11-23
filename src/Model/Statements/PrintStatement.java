package Model.Statements;

import Model.ProgramState;
import Model.Expressions.GenericExpression;

public class PrintStatement implements GenericStatement {
    private GenericExpression expression;
    
    public PrintStatement(GenericExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        var out = programState.getOut();
        var symbolTable = programState.getSymbolTable();
        var heap = programState.getHeap();
        out.add(this.expression.evaluate(symbolTable, heap));
        return programState;
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
