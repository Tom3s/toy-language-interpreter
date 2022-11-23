package Model.InterpreterExceptions;

public class HeapAllocationNotReferenceTypeException extends Exception {

    public HeapAllocationNotReferenceTypeException() {
        super("Cannot allocate heap to a variable that is not of type Ref");
    }

    public HeapAllocationNotReferenceTypeException(String name){
        super(String.format("Cannot allocate Heap memory to %s (not of type Ref)", name));
    }
    
}
