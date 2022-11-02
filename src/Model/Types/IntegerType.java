package Model.Types;

public class IntegerType implements GenericType{

    
    public boolean equals(Object other){
        return (other instanceof IntegerType);
    }

    @Override
    public GenericType deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "int";
    }
    
}
