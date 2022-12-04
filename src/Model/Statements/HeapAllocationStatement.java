package Model.Statements;

import Model.ProgramState;
import Model.Expressions.GenericExpression;
import Model.InterpreterExceptions.HeapAllocationNotReferenceTypeException;
import Model.InterpreterExceptions.VariableNotDeclaredException;
import Model.InterpreterExceptions.VariableTypeMismatchException;
import Model.Types.ReferenceType;
import Model.Values.ReferenceValue;

public class HeapAllocationStatement implements GenericStatement {
    
    String variableName;
    GenericExpression expression;
    
    public HeapAllocationStatement(String variableName, GenericExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        var symbolTable = programState.getSymbolTable();

        if (!symbolTable.containsKey(this.variableName)){
            throw new VariableNotDeclaredException(this.variableName);
        }

        var variableValue = symbolTable.lookUp(this.variableName);
        var variableType = variableValue.getType();

        if (!(variableType instanceof ReferenceType)){
            throw new HeapAllocationNotReferenceTypeException(this.variableName);
        }

        var referenceValue = ((ReferenceValue)variableValue);

        var heap = programState.getHeap();
        var evaluatedExpression = this.expression.evaluate(symbolTable, heap);
        var expressionType = evaluatedExpression.getType();
        var locationType = referenceValue.getLocationType();
        
        if (!expressionType.equals(locationType)){
            throw new VariableTypeMismatchException(evaluatedExpression.toString(), locationType.toString());
        }

        int address = heap.add(evaluatedExpression);
        
        symbolTable.update(this.variableName, new ReferenceValue(address, locationType));

        return null;
    }

    @Override
    public GenericStatement deepCopy() {
        return new HeapAllocationStatement(new String(this.variableName), this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("new(%s,%s)", this.variableName.toString(), this.expression.toString());
    }
    
}
