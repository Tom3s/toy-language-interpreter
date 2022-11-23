package Model.InterpreterExceptions;

public class InvalidConditionExpressionException extends Exception {

    public InvalidConditionExpressionException() {
        super("Expression cannot be evaluated as a boolean condition!");
    }
    
    public InvalidConditionExpressionException(String expression) {
        super(String.format("Expression %s cannot be evaluated as a boolean condition!", expression));
    }
}
