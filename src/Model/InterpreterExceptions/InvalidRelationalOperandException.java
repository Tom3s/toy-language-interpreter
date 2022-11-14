package Model.InterpreterExceptions;

public class InvalidRelationalOperandException extends Exception{
    public InvalidRelationalOperandException() {
        super("Operand types are not compatible with relational operation!");
    }
    
    public InvalidRelationalOperandException(String which, String type) {
        super(String.format("%s operand of type %s cannot be used in an relational expression!", which, type));
    }
}
