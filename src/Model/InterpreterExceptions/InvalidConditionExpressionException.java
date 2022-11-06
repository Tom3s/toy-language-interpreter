package Model.InterpreterExceptions;

public class InvalidConditionExpressionException extends Exception {

    public InvalidConditionExpressionException() {
        super("Expression cannot be evaluated as a boolean condition!");
    }
}
