package Model.InterpreterExceptions;

public class FileNotOpenException extends Exception {
    public FileNotOpenException() {
        super("File with given name is not yet open!");        
    }

    public FileNotOpenException(String fileName) {
        super(String.format("The file \"%s\" is not yet open!", fileName));        
    }
}
