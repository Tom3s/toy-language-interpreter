package Model.Values;

import Model.Types.GenericType;
import Model.Types.IntegerType;

public class IntegerValue implements GenericValue {
    
    private int value;

    public IntegerValue() {
        this.value = 0;
    }

    public IntegerValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public GenericType getType() {
        return new IntegerType();
    }
    
    @Override
    public String toString() {
        return String.format("%d", this.value);
    }
}
