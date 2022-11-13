package Model.Types;

import Model.Values.BooleanValue;
import Model.Values.GenericValue;

public class BooleanType implements GenericType {
    
    @Override
    public boolean equals(Object other){
        return (other instanceof BooleanType);
    }

    @Override
    public GenericType deepCopy() {
        return new BooleanType();
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public GenericValue defaultValue() {
        return new BooleanValue();
    }
}
