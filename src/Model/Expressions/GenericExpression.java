package Model.Expressions;

import Model.ADT.GenericDictionary;
import Model.Values.GenericValue;

public interface GenericExpression {
    public GenericValue evaluate(GenericDictionary<String, GenericValue> symbolTable) throws Exception;
    public GenericExpression deepCopy();
}
