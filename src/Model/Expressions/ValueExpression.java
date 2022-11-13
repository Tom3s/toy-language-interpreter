package Model.Expressions;

import Model.ADT.GenericDictionary;
import Model.Values.GenericValue;

public class ValueExpression implements GenericExpression{

    GenericValue value;

    public ValueExpression(GenericValue value) {
        this.value = value;
    }

    @Override
    public GenericExpression deepCopy() {
        return new ValueExpression(this.value);
    }

    @Override
    public GenericValue evaluate(GenericDictionary<String, GenericValue> symbolTable) throws Exception {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
    
    
}
