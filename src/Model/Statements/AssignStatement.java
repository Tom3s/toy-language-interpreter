package Model.Statements;

import Model.ProgramState;
import Model.Expressions.GenericExpression;
import Model.InterpreterExceptions.VariableNotDeclaredException;
import Model.InterpreterExceptions.VariableTypeMismatchException;
import Model.Type.GenericType;
import Model.Value.GenericValue;

public class AssignStatement implements GenericStatement {

    String id;
    GenericExpression expression;
    
    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        //var executionStack = programState.getExecutionStack();
        var symbolTable = programState.getSymbolTable();

        if (!symbolTable.containsKey(this.id)){
            throw new VariableNotDeclaredException(this.id);
        }
        
        GenericValue value = this.expression.evaluate(symbolTable);
        GenericType typeId = symbolTable.lookUp(this.id).getType();
        if (!value.getType().equals(typeId)) {
            throw new VariableTypeMismatchException(value.getType().toString(), typeId.toString());
        }
        symbolTable.update(this.id, value);
        return programState;
    }
    
    @Override
    public String toString() {
        return String.format("%s=%s", this.id.toString(), this.expression.toString());
    }
}
