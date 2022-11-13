package Model.Values;

import Model.Types.GenericType;
import Model.Types.StringType;

public class StringValue implements GenericValue {

    private String value;
    
    public StringValue() {
        this.value = "";
    }

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public GenericType getType() {
        return new StringType();
    }
    
    @Override
    public String toString() {
        return String.format("\"%s\"", this.value);
    }
}
