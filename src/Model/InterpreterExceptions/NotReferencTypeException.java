package Model.InterpreterExceptions;

public class NotReferencTypeException extends Exception{

    public NotReferencTypeException(String expression) {
        super(String.format("The expression %s is not of type Ref", expression));
    }
    
}
