package Model.InterpreterExceptions;

public class VariableNotLockableException extends Exception {
    public VariableNotLockableException(){
        super();
    }

    public VariableNotLockableException(String variableName, String type){
        super(String.format("Variable %s of type %s cannot be locked (onyl int is lockable)", variableName, type));
    }
}
