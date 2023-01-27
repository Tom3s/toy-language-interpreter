package Model.Statements;

import Model.ProgramState;
import Model.ADT.GenericDictionary;
import Model.ADT.LockDictionary;
import Model.InterpreterExceptions.VariableNotLockableException;
import Model.Types.GenericType;
import Model.Types.IntegerType;

public class LockStatement implements GenericStatement{
    private String variableName;

    public LockStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return String.format("lock(%s)", this.variableName);
    }

    @Override
    public GenericStatement deepCopy() {
        return new LockStatement(new String(this.variableName));
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        var executionStack = state.getExecutionStack();
        var lockTableLocation = LockDictionary.verifyLockPreconditions(this.variableName, state);
        var lockTable = state.getLockTable();

        boolean success = false;

        if (lockTable.lookUp(lockTableLocation) == -1){
            success = lockTable.update(lockTableLocation, state.getProgramId());
            System.out.println("Lock acquired for " + state.getProgramId() + " at " + lockTableLocation);
            return null;
        }

        if (!success)
            executionStack.push(this);

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
}
