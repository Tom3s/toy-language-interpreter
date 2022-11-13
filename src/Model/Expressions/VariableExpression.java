package Model.Expressions;

import Model.ADT.GenericDictionary;
import Model.Values.GenericValue;

public class VariableExpression implements GenericExpression {

    String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public GenericExpression deepCopy() {
        return new VariableExpression(id);
    }

    @Override
    public GenericValue evaluate(GenericDictionary<String, GenericValue> symbolTable) throws Exception {
        return symbolTable.lookUp(this.id);
    }

    @Override
    public String toString() {
        return id;
    }
    
    
}
