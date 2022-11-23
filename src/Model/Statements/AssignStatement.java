package Model.Statements;

import Model.ProgramState;
import Model.Expressions.GenericExpression;
import Model.InterpreterExceptions.VariableNotDeclaredException;
import Model.InterpreterExceptions.VariableTypeMismatchException;
import Model.Types.GenericType;
import Model.Values.GenericValue;

public class AssignStatement implements GenericStatement {

    private String id;
    private GenericExpression expression;
    
    public AssignStatement(String id, GenericExpression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        //var executionStack = programState.getExecutionStack();
        var symbolTable = programState.getSymbolTable();

        if (!symbolTable.containsKey(this.id)){
            throw new VariableNotDeclaredException(this.id);
        }
        
        var heap = programState.getHeap();
        GenericValue value = this.expression.evaluate(symbolTable, heap);
        GenericType typeId = symbolTable.lookUp(this.id).getType();
        if (!value.getType().equals(typeId)) {
            throw new VariableTypeMismatchException(value.getType().toString(), typeId.toString());
        }
        symbolTable.update(this.id, value);
        return programState;
    }

    
    
    @Override
    public GenericStatement deepCopy() {
        return new AssignStatement(new String(this.id), this.expression.deepCopy());
    }



    @Override
    public String toString() {
        return String.format("%s=%s", this.id.toString(), this.expression.toString());
    }
}
