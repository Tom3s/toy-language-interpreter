package Model.Values;

import Model.Types.GenericType;

public interface GenericValue {
    public GenericType getType();
    public boolean equals(Object other);
}
