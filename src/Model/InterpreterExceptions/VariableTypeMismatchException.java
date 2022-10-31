package Model.InterpreterExceptions;

public class VariableTypeMismatchException extends Exception {

    public VariableTypeMismatchException() {
        super("Variable types do not match!");
    }

    public VariableTypeMismatchException(String variable, String value){
        super(String.format("Cannot assign value of %s to variable of type %s!", variable, value));
    }
    
}
