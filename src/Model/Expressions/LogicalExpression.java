package Model.Expressions;

import Model.ADT.GenericDictionary;
import Model.ADT.GenericHeap;
import Model.InterpreterExceptions.InvalidLogicalOperandException;
import Model.Types.BooleanType;
import Model.Types.GenericType;
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
    public GenericValue evaluate(GenericDictionary<String, GenericValue> symbolTable, GenericHeap<GenericValue> heap) throws Exception {
        GenericValue leftValue = this.leftOperand.evaluate(symbolTable, heap);
        if (!leftValue.getType().equals(new BooleanType())){
            throw new InvalidLogicalOperandException("Left", leftValue.getType().toString());
        }

        GenericValue rightValue = this.rightOperand.evaluate(symbolTable, heap);
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
    public GenericType typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception {
        GenericType leftType = this.leftOperand.typeCheck(typeEnvironment);
        if (!leftType.equals(new BooleanType())){
            throw new InvalidLogicalOperandException("Left", leftType.toString());
        }
        
        GenericType rightType = this.rightOperand.typeCheck(typeEnvironment);
        if (!rightType.equals(new BooleanType())){
            throw new InvalidLogicalOperandException("Right", rightType.toString());
        }

        return new BooleanType();
    }

    @Override
    public GenericExpression deepCopy() {
        return new LogicalExpression(this.leftOperand.deepCopy(), this.rightOperand.deepCopy(), this.operation);
    }

    @Override
    public String toString() {
        var stringOperation = switch (this.operation) {
            case AND -> "and";
            case OR -> "or";
        };
        return String.format("(%s %s %s)", this.leftOperand.toString(), stringOperation, this.rightOperand.toString());
    }

    public GenericExpression getLeftOperand() {
        return leftOperand;
    }

    public void setLeftOperand(GenericExpression leftOperand) {
        this.leftOperand = leftOperand;
    }

    public GenericExpression getRightOperand() {
        return rightOperand;
    }

    public void setRightOperand(GenericExpression rightOperand) {
        this.rightOperand = rightOperand;
    }

    public LogicalOperation getOperation() {
        return operation;
    }

    public void setOperation(LogicalOperation operation) {
        this.operation = operation;
    }

    
}
