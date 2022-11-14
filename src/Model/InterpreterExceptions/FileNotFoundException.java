package Model.InterpreterExceptions;

public class FileNotFoundException extends Exception {

    public FileNotFoundException() {
        super("File was not found!");
    }

    public FileNotFoundException(String fileName){
        super(String.format("The file \"%s\" could not be open (file not found)", fileName));
    }
    
}
