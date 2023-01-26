package Model.InterpreterExceptions;

public class LockNoEntryException extends Exception {
    public LockNoEntryException(){
        super("There is no such entry in the Lock Table!");
    }
    
    public LockNoEntryException(int location){
        super(String.format("There is no entry in the Lock Table with location %d", location));
    }
}
