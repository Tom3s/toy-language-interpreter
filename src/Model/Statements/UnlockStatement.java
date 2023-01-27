package Model.Statements;

import Model.ProgramState;
import Model.ADT.GenericDictionary;
import Model.ADT.LockDictionary;
import Model.InterpreterExceptions.VariableNotLockableException;
import Model.Types.GenericType;
import Model.Types.IntegerType;

public class UnlockStatement implements GenericStatement{
    private String variableName;

    public UnlockStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return String.format("unlock(%s)", this.variableName);
    }

    @Override
    public GenericStatement deepCopy() {
        return new UnlockStatement(new String(this.variableName));
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        var lockTableLocation = LockDictionary.verifyLockPreconditions(this.variableName, state);
        var lockTable = state.getLockTable();

        var lockState = lockTable.lookUp(lockTableLocation);

        if (lockState == state.getProgramId()){
            lockTable.update(lockTableLocation, -1);
            System.out.println("Lock released for " + state.getProgramId() + " at " + lockTableLocation);
        }

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
