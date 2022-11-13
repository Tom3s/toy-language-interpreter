package Model.InterpreterExceptions;

import Model.Values.StringValue;

public class FileAlreadyOpenException extends Exception {
    public FileAlreadyOpenException(){
        super("File with the same name is already open!");
    }
    
    public FileAlreadyOpenException(StringValue fileName){
        super(String.format("The file %s is already open!", fileName.getValue()));
    }
}
