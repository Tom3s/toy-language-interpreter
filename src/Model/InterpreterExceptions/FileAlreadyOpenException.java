package Model.InterpreterExceptions;

public class FileAlreadyOpenException extends Exception {
    public FileAlreadyOpenException(){
        super("File with the same name is already open!");
    }
    
    public FileAlreadyOpenException(String fileName){
        super(String.format("The file %s is already open!", fileName));
    }
}
