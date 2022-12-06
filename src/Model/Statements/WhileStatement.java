package Model.Statements;

import Model.ProgramState;
import Model.Expressions.GenericExpression;
import Model.InterpreterExceptions.InvalidConditionExpressionException;
import Model.Types.BooleanType;
import Model.Values.BooleanValue;

public class WhileStatement implements GenericStatement {
    private GenericExpression condition;
    private GenericStatement statement;

    public WhileStatement(GenericExpression condition, GenericStatement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        var symbolTable = programState.getSymbolTable();
        var heap = programState.getHeap();

        var evaluatedCondition = this.condition.evaluate(symbolTable, heap);
        if (!evaluatedCondition.getType().equals(new BooleanType())){
            throw new InvalidConditionExpressionException();
        }

        var condition = ((BooleanValue)evaluatedCondition).getValue();

        if (condition){
            var executionStack = programState.getExecutionStack();
            executionStack.push(this);
            executionStack.push(this.statement);
        }

        return null;
    }

    @Override
    public GenericStatement deepCopy() {
        return new WhileStatement(this.condition.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("(while %s %s;)", this.condition.toString(), this.statement.toString());
    }
}
