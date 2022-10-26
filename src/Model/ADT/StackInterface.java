package Model.ADT;

import java.util.List;

import Model.InterpreterExceptions.EmptyStackException;

public interface StackInterface<DataType> {
    public void push(DataType element);
    public DataType pop() throws EmptyStackException;
    public boolean isEmpty();
    public List<DataType> getReversed();
}
