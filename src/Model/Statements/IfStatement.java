package Model.Statements;

import Model.ProgramState;
import Model.Expressions.GenericExpression;
import Model.InterpreterExceptions.InvalidConditionExpressionException;
import Model.Types.BooleanType;
import Model.Values.BooleanValue;

public class IfStatement implements GenericStatement {

    private GenericExpression expression;
    private GenericStatement thenStatement;
    private GenericStatement elseStatement;

    public IfStatement(GenericExpression expression, GenericStatement thenStatement, GenericStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        var symbolTable = programState.getSymbolTable();
        var heap = programState.getHeap();
        var condition = this.expression.evaluate(symbolTable, heap);

        if (!condition.getType().equals(new BooleanType())){
            throw new InvalidConditionExpressionException();
        }

        var executionStack = programState.getExecutionStack();

        if (((BooleanValue)condition).getValue()){
            executionStack.push(this.thenStatement);
        } else {
            executionStack.push(this.elseStatement);
        }

        return programState;
    }

    @Override
    public GenericStatement deepCopy() {
        return new IfStatement(this.expression.deepCopy(), this.thenStatement.deepCopy(), this.elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("(IF(%s)THEN(%s)ELSE(%s))", this.expression.toString(), this.thenStatement.toString(), this.elseStatement.toString());
    }
}
