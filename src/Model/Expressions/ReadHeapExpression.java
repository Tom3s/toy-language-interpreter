package Model.Expressions;

import Model.ADT.GenericDictionary;
import Model.ADT.GenericHeap;
import Model.InterpreterExceptions.HeapNoEntryException;
import Model.InterpreterExceptions.NotReferencTypeException;
import Model.Values.GenericValue;
import Model.Values.ReferenceValue;

public class ReadHeapExpression implements GenericExpression{
    private GenericExpression expression;

    public ReadHeapExpression(GenericExpression expression) {
        this.expression = expression;
    }

    @Override
    public GenericValue evaluate(GenericDictionary<String, GenericValue> symbolTable, GenericHeap<GenericValue> heap) throws Exception {
        var evaluatedExpression = this.expression.evaluate(symbolTable, heap);

        if (!(evaluatedExpression.getType() instanceof ReferenceValue)){
            throw new NotReferencTypeException(this.expression.toString());
        }

        var referenceExpression = ((ReferenceValue)evaluatedExpression);
        var address = referenceExpression.getAddress();

        if (!heap.containsKey(address)){
            throw new HeapNoEntryException(address);
        }

        return heap.lookUp(address);
    }

    @Override
    public GenericExpression deepCopy() {
        return new ReadHeapExpression(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("rH(%s)", this.expression.toString());
    }
    
}
