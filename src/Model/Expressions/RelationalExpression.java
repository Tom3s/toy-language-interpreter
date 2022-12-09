package Model.Expressions;

import Model.ADT.GenericDictionary;
import Model.ADT.GenericHeap;
import Model.InterpreterExceptions.InvalidRelationalOperandException;
import Model.Types.GenericType;
import Model.Types.IntegerType;
import Model.Values.BooleanValue;
import Model.Values.GenericValue;
import Model.Values.IntegerValue;

public class RelationalExpression implements GenericExpression{
    private GenericExpression leftOperand;
    private GenericExpression rightOperand;
    private RelationalOperation operation;
    public RelationalExpression(GenericExpression leftOperand, GenericExpression rightOperand,
            RelationalOperation operation) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operation = operation;
    }
    
    @Override
    public GenericValue evaluate(GenericDictionary<String, GenericValue> symbolTable, GenericHeap<GenericValue> heap) throws Exception {
        GenericValue leftValue = this.leftOperand.evaluate(symbolTable, heap);
        GenericValue rightValue = this.rightOperand.evaluate(symbolTable, heap);

        if (this.operation == RelationalOperation.EQUAL || this.operation == RelationalOperation.NOT_EQUAL){
            boolean twoOperandsAreEqual = leftValue.equals(rightValue);
            switch (this.operation) {
                case EQUAL:
                    return new BooleanValue(twoOperandsAreEqual);
                case NOT_EQUAL:
                    return new BooleanValue(!twoOperandsAreEqual);
                default:
                    break;
            }
        }

        if (!leftValue.getType().equals(new IntegerType())){
            throw new InvalidRelationalOperandException(this.leftOperand.toString(), leftValue.getType().toString());
        }
        
        if (!rightValue.getType().equals(new IntegerType())){
            throw new InvalidRelationalOperandException(this.rightOperand.toString(), rightValue.getType().toString());
        }

        int leftInt = ((IntegerValue)leftValue).getValue();
        int rightInt = ((IntegerValue)rightValue).getValue();

        switch(this.operation){
            case LESS:
                return new BooleanValue(leftInt < rightInt);
            case LESS_OR_EQUAL:
                return new BooleanValue(leftInt <= rightInt);
            case GREATER:
                return new BooleanValue(leftInt > rightInt);
            case GREATER_OR_EQUAL:
                return new BooleanValue(leftInt >= rightInt);
            default:
                throw new Exception("Unknown exception occured");
        }
    }
    
    

    @Override
    public GenericType typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception {
        GenericType leftType = this.leftOperand.typeCheck(typeEnvironment);
        if (!leftType.equals(new IntegerType())){
            throw new InvalidRelationalOperandException(this.leftOperand.toString(), leftType.toString());
        }
        
        GenericType rightType = this.rightOperand.typeCheck(typeEnvironment);
        if (!rightType.equals(new IntegerType())){
            throw new InvalidRelationalOperandException(this.rightOperand.toString(), rightType.toString());
        }

        return new IntegerType();
    }

    @Override
    public GenericExpression deepCopy() {
        return new RelationalExpression(this.leftOperand.deepCopy(), this.rightOperand.deepCopy(), this.operation);
    }

    @Override
    public String toString() {
        var stringOperation = switch (this.operation) {
            case LESS -> "<";
            case LESS_OR_EQUAL -> "<=";
            case GREATER -> ">";
            case GREATER_OR_EQUAL -> ">=";
            case EQUAL -> "==";
            case NOT_EQUAL -> "!=";
        };
        return String.format("(%s %s %s)", this.leftOperand.toString(), stringOperation, this.rightOperand.toString());
    }
        
}
