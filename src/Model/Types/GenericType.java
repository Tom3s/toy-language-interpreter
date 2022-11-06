package Model.Types;

import Model.Values.GenericValue;

public interface GenericType {
    public GenericType deepCopy();
    public String toString();
    public GenericValue createValueOfType();
}
