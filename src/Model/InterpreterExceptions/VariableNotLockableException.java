package Model.InterpreterExceptions;

public class VariableNotLockableException  extends Exception {
    public VariableNotLockableException(String variableName, String type) {
        super(String.format("The variable %s of type %s is not lockable (it's not of type Integer)", variableName, type));
    }
}
