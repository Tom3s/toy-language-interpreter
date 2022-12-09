package Model.Statements;

import Model.ProgramState;
import Model.ADT.GenericDictionary;
import Model.Expressions.GenericExpression;
import Model.Types.GenericType;

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
        return null;
    }

    @Override
    public GenericDictionary<String, GenericType> typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception {
        this.expression.typeCheck(typeEnvironment);
        return typeEnvironment;
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
