package Model.Types;

import Model.Values.GenericValue;
import Model.Values.ReferenceValue;

public class ReferenceType implements GenericType {
    private GenericType inner;

    public ReferenceType(GenericType inner) {
        this.inner = inner;
    }

    public GenericType getInner() {
        return this.inner;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ReferenceType)) {
            return false;
        }

        return this.inner.equals(((ReferenceType) other).getInner());
    }

    @Override
    public String toString() {
        return "Ref " + inner.toString();
    }

    public GenericValue defaultValue() {
        return new ReferenceValue(0, this.inner);
    }

    @Override
    public GenericType deepCopy() {
        return new ReferenceType(this.inner.deepCopy());
    }

}
