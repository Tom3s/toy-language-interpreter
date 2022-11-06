package Model.InterpreterExceptions;

public class InvalidLogicalOperandException extends Exception {

    public InvalidLogicalOperandException() {
        super("Operand types are not compatible with logical operation!");
    }
    
    public InvalidLogicalOperandException(String which, String type) {
        super(String.format("%s operand of type %s cannot be used in a logical expression!", which, type));
    }
}
