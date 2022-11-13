package Model.Types;

import Model.Values.GenericValue;
import Model.Values.StringValue;

public class StringType implements GenericType{

    

    @Override
    public boolean equals(Object other){
        return (other instanceof StringType);
    }

    @Override
    public GenericType deepCopy() {
        return new StringType();
    }
    
    @Override
    public String toString() {
        return "string";
    }

    @Override
    public GenericValue defaultValue() {
        return new StringValue();
    }
    
}
