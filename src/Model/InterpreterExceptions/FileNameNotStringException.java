package Model.InterpreterExceptions;

import Model.Values.GenericValue;

public class FileNameNotStringException extends Exception {
    public FileNameNotStringException() {
        super("Given expression is not of type String!");        
    }

    public FileNameNotStringException(String fileName){
        super(String.format("Given expression (%s) is not of type String!", fileName));
    }
}
