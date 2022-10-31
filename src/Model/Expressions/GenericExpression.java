package Model.Expressions;

import Model.ADT.GenericDictionary;
import Model.Value.GenericValue;

public interface GenericExpression {
    GenericValue evaluate(GenericDictionary<String, GenericValue> symbolTable) throws Exception;
}
