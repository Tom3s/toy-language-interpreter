package Model.InterpreterExceptions;

public class VariableTypeMismatchException extends Exception {

    public VariableTypeMismatchException() {
        super("Variable types do not match!");
    }

    public VariableTypeMismatchException(String value, String type){
        super(String.format("Cannot assign value of %s to variable of type %s!", value, type));
    }
    
}
