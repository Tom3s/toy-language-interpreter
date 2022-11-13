package Model.InterpreterExceptions;

import Model.Values.GenericValue;

public class FileNotOpenException extends Exception {
    public FileNotOpenException() {
        super("File with given name is not yet open!");        
    }

    public FileNotOpenException(GenericValue fileName) {
        super(String.format("The file \"%s\" is not yet open!", fileName.toString()));        
    }
}
