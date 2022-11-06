package Model.ADT;

import Model.InterpreterExceptions.EmptyListException;

public interface GenericList<DataType> {
    public void add(DataType element);
    public DataType getLast() throws EmptyListException;
}
