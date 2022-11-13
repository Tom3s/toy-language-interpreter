package Model.InterpreterExceptions;

public class NoLogFilePathException extends Exception {

    public NoLogFilePathException() {
        super("Repository was created without a log file path!");
    }
}
