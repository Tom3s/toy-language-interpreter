package Model.ADT;

import java.util.List;

import Model.InterpreterExceptions.EmptyListException;

public interface GenericList<DataType> {
    public void add(DataType element);
    public DataType getLast() throws EmptyListException;
    public List<DataType> getContent();
}
