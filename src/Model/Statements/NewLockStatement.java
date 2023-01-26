package Model.Statements;

import Model.ProgramState;
import Model.ADT.GenericDictionary;
import Model.InterpreterExceptions.VariableNotDeclaredException;
import Model.InterpreterExceptions.VariableNotLockableException;
import Model.Types.GenericType;
import Model.Types.IntegerType;
import Model.Values.IntegerValue;

public class NewLockStatement implements GenericStatement {
    private String id;

    public NewLockStatement(String id) {
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        var symbolTable = programState.getSymbolTable();
        
        if (!symbolTable.containsKey(this.id)){
            throw new VariableNotDeclaredException(this.id);
        }

        var variableType = symbolTable.lookUp(this.id).getType();
        if (!variableType.equals(new IntegerType())){
            throw new VariableNotLockableException(this.id, variableType.toString());
        }
        
        var lockTable = programState.getLockTable();
        var lockAddress = lockTable.add(-1);

        symbolTable.update(this.id, new IntegerValue(lockAddress));

        return null;
    }

    @Override
    public GenericDictionary<String, GenericType> typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception {
        var variableType = typeEnvironment.lookUp(this.id);
        if (!variableType.equals(new IntegerType())){
            throw new VariableNotLockableException(this.id, variableType.toString());
        }
        return typeEnvironment;
    }

    @Override
    public GenericStatement deepCopy() {
        return new NewLockStatement(new String(this.id));
    }

    @Override
    public String toString() {
        return String.format("newLock(%s)", this.id);
    }
}
