package Model.Statements;

import Model.ProgramState;
import Model.ADT.GenericDictionary;
import Model.ADT.LockDictionary;
import Model.InterpreterExceptions.VariableNotLockableException;
import Model.Types.GenericType;
import Model.Types.IntegerType;


public class LockStatement implements GenericStatement {
    
    private String id;

    public LockStatement(String id) {
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        var executionStack = programState.getExecutionStack();

        // executionStack.pop();

        int lockTableLocation = LockDictionary.verifyLockPreconditions(id, programState);
        
        var lockTable = programState.getLockTable();

        int lockTableValue = lockTable.lookUp(lockTableLocation);

        if (lockTableValue == -1) {
            lockTable.update(lockTableLocation, programState.getProgramId());
            return null;
        }

        executionStack.push(this);
        return null;
    }

    @Override
    public GenericStatement deepCopy() {
        return new LockStatement(new String(this.id));
    }

    @Override
    public String toString() {
        return String.format("lock(%s)", this.id);
    }

    @Override
    public GenericDictionary<String, GenericType> typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception {
        var variableType = typeEnvironment.lookUp(this.id);
        if (!variableType.equals(new IntegerType())){
            throw new VariableNotLockableException(this.id, variableType.toString());
        }
        return typeEnvironment;
    }
}
