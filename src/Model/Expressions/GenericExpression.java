package Model.Expressions;

import Model.ADT.GenericDictionary;
import Model.ADT.GenericHeap;
import Model.Types.GenericType;
import Model.Values.GenericValue;

public interface GenericExpression {
    public GenericValue evaluate(GenericDictionary<String, GenericValue> symbolTable, GenericHeap<GenericValue> heap) throws Exception;
    public GenericType typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception;
    public GenericExpression deepCopy();
}
