package Model.Types;

import Model.Values.GenericValue;
import Model.Values.IntegerValue;

public class IntegerType implements GenericType {

    @Override
    public boolean equals(Object other){
        return (other instanceof IntegerType);
    }

    @Override
    public GenericType deepCopy() {
        return new IntegerType();
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public GenericValue defaultValue() {
        return new IntegerValue();
    }
    
}
