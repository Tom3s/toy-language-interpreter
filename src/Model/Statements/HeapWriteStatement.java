package Model.Statements;

import Model.ProgramState;
import Model.Expressions.GenericExpression;
import Model.InterpreterExceptions.HeapNoEntryException;
import Model.InterpreterExceptions.NotReferencTypeException;
import Model.InterpreterExceptions.VariableNotDeclaredException;
import Model.InterpreterExceptions.VariableTypeMismatchException;
import Model.Types.ReferenceType;
import Model.Values.ReferenceValue;

public class HeapWriteStatement implements GenericStatement {
    
    private String variableName;
    private GenericExpression expression;

    public HeapWriteStatement(String variableName, GenericExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        var symbolTable = programState.getSymbolTable();

        if (!symbolTable.containsKey(this.variableName)){
            throw new VariableNotDeclaredException(this.variableName);
        }

        var variable = symbolTable.lookUp(this.variableName);
        var variableType = variable.getType();

        if (!(variableType instanceof ReferenceType)){
            throw new NotReferencTypeException(this.variableName);
        }

        var referenceValue = ((ReferenceValue)variable);
        var address = referenceValue.getAddress();
        var heap = programState.getHeap();
        if (!heap.containsKey(address)){
            throw new HeapNoEntryException(address);
        }

        var evaluatedExpression = this.expression.evaluate(symbolTable, heap);
        var evaluatedExpressionType = evaluatedExpression.getType();
        if (!evaluatedExpressionType.equals(referenceValue.getLocationType())){
            throw new VariableTypeMismatchException(evaluatedExpression.toString(), "Ref");
        }
        heap.update(address, evaluatedExpression);
        return programState;
    }

    @Override
    public GenericStatement deepCopy() {
        return new HeapWriteStatement(this.variableName, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("wH(%s, %s)", this.variableName, this.expression.toString());
    }
    
}
