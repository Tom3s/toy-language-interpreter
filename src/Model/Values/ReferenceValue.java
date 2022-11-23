package Model.Values;

import Model.Types.GenericType;
import Model.Types.ReferenceType;

public class ReferenceValue implements GenericValue {
    int address;
    GenericType locationType;
    
    public ReferenceValue(int address, GenericType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return this.address;
    }

    public GenericType getType() {
        return new ReferenceType(this.locationType);
    }

    
    public GenericType getLocationType() {
        return this.locationType;
    }

    @Override
    public String toString() {
        return "Ref " + this.locationType.toString();
    }

    
    

}
