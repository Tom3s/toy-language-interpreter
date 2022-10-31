package Model.ADT;

import java.util.ArrayList;
import java.util.List;

public class CList<DataType> implements GenericList<DataType> {
    private List<DataType> out;
    
    public CList(){
        this.out = new ArrayList<DataType>();
    }

    @Override
    public void add(DataType element) {
        this.out.add(element);
    }

    @Override
    public String toString() {
        return this.out.toString();
    }
}
