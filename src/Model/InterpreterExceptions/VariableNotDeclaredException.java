package Model.InterpreterExceptions;

public class VariableNotDeclaredException extends Exception {

    public VariableNotDeclaredException() {
        super("Variable was not declared");
    }

    public VariableNotDeclaredException(String id){
        super(String.format("Variable %s was not declared!", id));
    }
    
}
