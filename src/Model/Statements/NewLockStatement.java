package Model.Statements;

import Model.ProgramState;
import Model.ADT.GenericDictionary;
import Model.InterpreterExceptions.VariableNotDeclaredException;
import Model.InterpreterExceptions.VariableNotLockableException;
import Model.Types.GenericType;
import Model.Types.IntegerType;
import Model.Values.IntegerValue;

public class NewLockStatement implements GenericStatement {
    private String variableName;

    public NewLockStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public GenericStatement deepCopy() {
        return new NewLockStatement(new String(this.variableName));
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        var symbolTable = state.getSymbolTable();
        if (!symbolTable.containsKey(this.variableName)){
            throw new VariableNotDeclaredException(this.variableName);
        }
        var variableType = symbolTable.lookUp(this.variableName).getType();

        if (!variableType.equals(new IntegerType())){
            throw new VariableNotLockableException(this.variableName, variableType.toString());
        }

        var lockTable = state.getLockTable();

        var lockAddress = lockTable.add(-1);

        symbolTable.update(this.variableName, new IntegerValue(lockAddress));

        return null;
    }

    @Override
    public GenericDictionary<String, GenericType> typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception {
        var variableType = typeEnvironment.lookUp(this.variableName);
        if (!variableType.equals(new IntegerType())){
            throw new VariableNotLockableException(this.variableName, variableType.toString());
        }
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return String.format("newLock(%s)", this.variableName);
    }
}
