package Model.InterpreterExceptions;

public class EmptyListException extends Exception {

    public EmptyListException() {
        super("List is empty!");
    }
}
