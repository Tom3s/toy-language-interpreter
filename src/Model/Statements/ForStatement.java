package Model.Statements;

import Model.ProgramState;
import Model.ADT.GenericDictionary;
import Model.Expressions.GenericExpression;
import Model.Expressions.RelationalExpression;
import Model.Expressions.RelationalOperation;
import Model.Expressions.VariableExpression;
import Model.InterpreterExceptions.InvalidRelationalOperandException;
import Model.InterpreterExceptions.VariableTypeMismatchException;
import Model.Types.GenericType;
import Model.Types.IntegerType;

public class ForStatement implements GenericStatement {
    private String variableName;
    private GenericExpression initialValue;
    private GenericExpression continueCondition;
    private GenericExpression postLoopExpression;
    private GenericStatement statement;
    
    public ForStatement(
            String variableName,
            GenericExpression initialValue, 
            GenericExpression continueCondition,
            GenericExpression postLoopExpression,
            GenericStatement statement
    ) {
        this.variableName = variableName;
        this.initialValue = initialValue;
        this.continueCondition = continueCondition;
        this.postLoopExpression = postLoopExpression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        var executionStack = programState.getExecutionStack();

        // int variable; variable=initialValue;(while(variable<continueCondition) statement;variable=postLoopExpression)
        var convertedStatement = new CompoundStatement(
        new VariableDeclarationStatement( new IntegerType(), this.variableName), new CompoundStatement(
        new AssignStatement(this.variableName, this.initialValue),
        new WhileStatement(
            new RelationalExpression(new VariableExpression(this.variableName), this.continueCondition, RelationalOperation.LESS), 
            new CompoundStatement(this.statement, 
            new AssignStatement(this.variableName, this.postLoopExpression)))));
        
        executionStack.push(convertedStatement);

        return null;
    }

    @Override
    public GenericDictionary<String, GenericType> typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception {
        GenericType initialValueType = this.initialValue.typeCheck(typeEnvironment);
        if (!initialValueType.equals(new IntegerType())) {
            throw new VariableTypeMismatchException(initialValueType.toString(), this.variableName);
        }

        typeEnvironment.add(this.variableName, new IntegerType());

        GenericType continueConditionType = this.continueCondition.typeCheck(typeEnvironment);
        if (!continueConditionType.equals(new IntegerType())) {
            throw new InvalidRelationalOperandException(this.continueCondition.toString(), continueConditionType.toString());
        }

        GenericType postLoopExpressionType = this.postLoopExpression.typeCheck(typeEnvironment);
        if (!postLoopExpressionType.equals(new IntegerType())) {
            throw new VariableTypeMismatchException(postLoopExpressionType.toString(), this.variableName);
        }

        this.statement.typeCheck(typeEnvironment.deepCopy());

        return typeEnvironment;
    }

    @Override
    public GenericStatement deepCopy() {
        return new ForStatement(this.variableName, this.initialValue.deepCopy(), this.continueCondition.deepCopy(), this.postLoopExpression.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("for(%s=%s;%s<%s;%s=%s) %s;", 
            this.variableName, 
            this.initialValue.toString(), 
            this.variableName, 
            this.continueCondition.toString(), 
            this.variableName, 
            this.postLoopExpression.toString(), 
            this.statement.toString());
    }
}
