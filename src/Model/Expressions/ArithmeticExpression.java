package Model.Expressions;

import Model.ADT.GenericDictionary;
import Model.InterpreterExceptions.DivisionByZeroException;
import Model.InterpreterExceptions.InvalidOperandException;
import Model.Types.IntegerType;
import Model.Values.GenericValue;
import Model.Values.IntegerValue;

public class ArithmeticExpression implements GenericExpression {

    GenericExpression leftOperand;
    GenericExpression rightOperand;
    ArithmeticOperation operation;

    @Override
    public GenericValue evaluate(GenericDictionary<String, GenericValue> symbolTable) throws Exception {
        GenericValue leftValue = this.leftOperand.evaluate(symbolTable);
        if (!leftValue.getType().equals(new IntegerType())){
            throw new InvalidOperandException("Left", leftValue.getType().toString());
        }

        GenericValue rightValue = this.rightOperand.evaluate(symbolTable);
        if (!rightValue.getType().equals(new IntegerType())){
            throw new InvalidOperandException("Right", rightValue.getType().toString());
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
    public GenericExpression deepCopy() {
        return null;
    }
    
}
