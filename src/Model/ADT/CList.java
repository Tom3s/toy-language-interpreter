package Model.ADT;

import java.util.ArrayList;
import java.util.List;

import Model.InterpreterExceptions.EmptyListException;

public class CList<DataType> implements GenericList<DataType> {
    private ArrayList<DataType> list;
    
    public CList(){
        this.list = new ArrayList<DataType>();
    }

    

    public CList(List<DataType> list) {
        this.list = new ArrayList<DataType>(list);
    }



    @Override
    public void add(DataType element) {
        this.list.add(element);
    }

    @Override
    public DataType getLast() throws EmptyListException {
        if (this.list.isEmpty()){
            throw new EmptyListException();
        }
        return this.list.get(this.list.size() - 1);
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
