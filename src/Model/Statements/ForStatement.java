package Model.Statements;

import Model.ProgramState;
import Model.ADT.GenericDictionary;
import Model.Expressions.GenericExpression;
import Model.Expressions.RelationalExpression;
import Model.Expressions.RelationalOperation;
import Model.Expressions.VariableExpression;
import Model.InterpreterExceptions.InvalidConditionExpressionException;
import Model.InterpreterExceptions.VariableAlreadyDeclaredException;
import Model.InterpreterExceptions.VariableTypeMismatchException;
import Model.Types.GenericType;
import Model.Types.IntegerType;


public class ForStatement implements GenericStatement {
    private String variableName;
    private GenericExpression initialValue;
    private GenericExpression continueValue;
    private GenericExpression postLoopExpression;
    private GenericStatement statement;

    public ForStatement(String variableName, GenericExpression initialValue, GenericExpression continueValue,
            GenericExpression postLoopExpression, GenericStatement statement) {
        this.variableName = variableName;
        this.initialValue = initialValue;
        this.continueValue = continueValue;
        this.postLoopExpression = postLoopExpression;
        this.statement = statement;
    }

    @Override
    public String toString() {
        return String.format("for(%s=%s;%s;%s) %s", 
            this.variableName, 
            this.initialValue.toString(),
            this.continueValue.toString(), 
            this.postLoopExpression.toString(), this.statement.toString());
    }

    @Override
    public GenericStatement deepCopy() {
        return new ForStatement(new String(this.variableName), this.initialValue.deepCopy(), this.continueValue.deepCopy(), this.postLoopExpression.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        var symbolTable = state.getSymbolTable();
        if (symbolTable.containsKey(this.variableName)){
            throw new VariableAlreadyDeclaredException(this.variableName);
        }

        // type check already done before running the program

        var whileStatement = new CompoundStatement(
            new VariableDeclarationStatement(new IntegerType(), this.variableName), new CompoundStatement(
            new AssignStatement(this.variableName, this.initialValue), 
            new WhileStatement(new RelationalExpression(new VariableExpression(this.variableName), this.continueValue, RelationalOperation.LESS), new CompoundStatement(
                this.statement, 
                new AssignStatement(this.variableName, this.postLoopExpression)))));
            
        var executionStack = state.getExecutionStack();
        executionStack.push(whileStatement);

        return null;
    }

    public GenericDictionary<String, GenericType> typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception {
        var initialValueType = this.initialValue.typeCheck(typeEnvironment);
        if (!initialValueType.equals(new IntegerType())){
            throw new VariableTypeMismatchException(this.initialValue.toString(), this.variableName);
        }

        var continueConditionType = this.continueValue.typeCheck(typeEnvironment);
        if (!continueConditionType.equals(new IntegerType())){
            throw new InvalidConditionExpressionException(this.continueValue.toString());
        }
        var postLoopExpressionType = this.postLoopExpression.typeCheck(typeEnvironment);
        if (!postLoopExpressionType.equals(new IntegerType())){
            throw new VariableTypeMismatchException(this.postLoopExpression.toString(), this.variableName);
        }
        return typeEnvironment;
    }
    


}
