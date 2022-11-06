package Model.InterpreterExceptions;

public class InvalidArithmeticOperandException extends Exception{

    public InvalidArithmeticOperandException() {
        super("Operand types are not compatible with arithmetic operation!");
    }
    
    public InvalidArithmeticOperandException(String which, String type) {
        super(String.format("%s operand of type %s cannot be used in an arithmetic expression!", which, type));
    }
}
