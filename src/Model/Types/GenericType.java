package Model.Types;

import Model.Values.GenericValue;

public interface GenericType {
    public boolean equals(Object other);
    public GenericType deepCopy();
    public String toString();
    public GenericValue defaultValue();
}
