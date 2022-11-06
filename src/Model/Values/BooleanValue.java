package Model.Values;

import Model.Types.BooleanType;
import Model.Types.GenericType;

public class BooleanValue implements GenericValue {
    private boolean value;

    public BooleanValue() {
        this.value = false;
    }

    public BooleanValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    @Override
    public GenericType getType() {
        return new BooleanType();
    }
    
    @Override
    public String toString() {
        return this.value ? "true" : "false";
    }
}
