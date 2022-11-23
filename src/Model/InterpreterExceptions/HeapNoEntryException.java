package Model.InterpreterExceptions;

public class HeapNoEntryException extends Exception {
    public HeapNoEntryException(){
        super("There is no such entry in the Heap!");
    }
    
    public HeapNoEntryException(int address){
        super(String.format("There is no entry in the Heap with address %d", address));
    }
}
