package Model.Expressions;

import Model.ADT.GenericDictionary;
import Model.ADT.GenericHeap;
import Model.InterpreterExceptions.DivisionByZeroException;
import Model.InterpreterExceptions.InvalidArithmeticOperandException;
import Model.Types.GenericType;
import Model.Types.IntegerType;
import Model.Values.GenericValue;
import Model.Values.IntegerValue;

public class ArithmeticExpression implements GenericExpression {

    private GenericExpression leftOperand;
    private GenericExpression rightOperand;
    private ArithmeticOperation operation;
    
    public ArithmeticExpression(GenericExpression leftOperand, GenericExpression rightOperand, ArithmeticOperation operation) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operation = operation;
    }

    @Override
    public GenericValue evaluate(GenericDictionary<String, GenericValue> symbolTable, GenericHeap<GenericValue> heap) throws Exception {
        GenericValue leftValue = this.leftOperand.evaluate(symbolTable, heap);
        if (!leftValue.getType().equals(new IntegerType())){
            throw new InvalidArithmeticOperandException("Left", leftValue.getType().toString());
        }

        GenericValue rightValue = this.rightOperand.evaluate(symbolTable, heap);
        if (!rightValue.getType().equals(new IntegerType())){
            throw new InvalidArithmeticOperandException("Right", rightValue.getType().toString());
        }

        int leftNumber = ((IntegerValue)leftValue).getValue();
        int rightNumber = ((IntegerValue)rightValue).getValue();

        switch(this.operation){
            case ADDITION:
                return new IntegerValue(leftNumber + rightNumber);
            case SUBTRACTION:
                return new IntegerValue(leftNumber - rightNumber);
            case MULTIPLICATION:
                return new IntegerValue(leftNumber * rightNumber);
            case DIVISION:
                if (rightNumber == 0){
                    throw new DivisionByZeroException();
                }
                return new IntegerValue(leftNumber / rightNumber);
            default:
                throw new Exception("Unknown exception occured");
        }
    }

    

    @Override
    public GenericType typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception {
        var leftType = this.leftOperand.typeCheck(typeEnvironment);
        if (!leftType.equals(new IntegerType())){
            throw new InvalidArithmeticOperandException("Left", leftType.toString());
        }

        var rightType = this.rightOperand.typeCheck(typeEnvironment);
        if (!rightType.equals(new IntegerType())){
            throw new InvalidArithmeticOperandException("Right", rightType.toString());
        }

        return new IntegerType();
    }

    @Override
    public GenericExpression deepCopy() {
        return new ArithmeticExpression(this.leftOperand.deepCopy(), this.rightOperand.deepCopy(), this.operation);
    }

    @Override
    public String toString() {
        String ret = this.leftOperand.toString();
        switch(this.operation){
            case ADDITION:
                ret += "+";
                break;
            case SUBTRACTION:
                ret += "-";
                break;
            case MULTIPLICATION:
                ret += "*";
                break;
            case DIVISION:
                ret += "/";
                break;
            default:
                break;
        }
        return ret + this.rightOperand.toString();
    }
    
    
}
