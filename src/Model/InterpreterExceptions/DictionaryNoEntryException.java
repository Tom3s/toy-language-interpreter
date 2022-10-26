package Model.InterpreterExceptions;

public class DictionaryNoEntryException extends Exception {
    public DictionaryNoEntryException(){
        super("There is no such entry in the dictionary!");
    }
}
