package Model.InterpreterExceptions;

public class VariableAlreadyDeclaredException extends Exception {

    public VariableAlreadyDeclaredException() {
        super("Variable was already declared!");
    }
    
    public VariableAlreadyDeclaredException(String id){
        super(String.format("Variable %s was already declared!", id));
    }
}
