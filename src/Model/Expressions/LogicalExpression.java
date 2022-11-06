package Model.Expressions;

import Model.ADT.GenericDictionary;
import Model.InterpreterExceptions.InvalidLogicalOperandException;
import Model.Types.BooleanType;
import Model.Values.BooleanValue;
import Model.Values.GenericValue;

public class LogicalExpression implements GenericExpression {
    private GenericExpression leftOperand;
    private GenericExpression rightOperand;
    private LogicalOperation operation;

    public LogicalExpression(GenericExpression leftOperand, GenericExpression rightOperand,
            LogicalOperation operation) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operation = operation;
    }

    @Override
    public GenericValue evaluate(GenericDictionary<String, GenericValue> symbolTable) throws Exception {
        GenericValue leftValue = this.leftOperand.evaluate(symbolTable);
        if (!leftValue.getType().equals(new BooleanType())){
            throw new InvalidLogicalOperandException("Left", leftValue.getType().toString());
        }

        GenericValue rightValue = this.rightOperand.evaluate(symbolTable);
        if (!rightValue.getType().equals(new BooleanType())){
            throw new InvalidLogicalOperandException("Right", rightValue.getType().toString());
        }

        boolean leftBoolean = ((BooleanValue)leftValue).getValue();
        boolean rightBoolean = ((BooleanValue)rightValue).getValue();

        switch(this.operation){
            case AND:
                return new BooleanValue(leftBoolean && rightBoolean);
            case OR:
                return new BooleanValue(leftBoolean || rightBoolean);
            default:
                throw new Exception("Unknown exception occured");
        }
    }

    @Override
    public GenericExpression deepCopy() {
        return new LogicalExpression(this.leftOperand.deepCopy(), this.rightOperand.deepCopy(), this.operation);
    }

    
}
