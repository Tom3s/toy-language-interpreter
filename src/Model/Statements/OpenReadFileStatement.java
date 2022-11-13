package Model.Statements;

import java.io.BufferedReader;
import java.io.FileReader;

import Model.ProgramState;
import Model.Expressions.GenericExpression;
import Model.InterpreterExceptions.FileAlreadyOpenException;
import Model.InterpreterExceptions.FileNameNotStringException;
import Model.Types.StringType;
import Model.Values.StringValue;

public class OpenReadFileStatement implements GenericStatement {
    
    GenericExpression expression;

    public OpenReadFileStatement(GenericExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        // var executionStack = programState.getExecutionStack();
        var symbolTable = programState.getSymbolTable();
        var evaluatedExpression = this.expression.evaluate(symbolTable);
        if (!evaluatedExpression.getType().equals(new StringType())){
            throw new FileNameNotStringException(evaluatedExpression);
        }

        StringValue stringExpression = (StringValue)evaluatedExpression;

        var fileTable = programState.getFileTable();

        if (fileTable.containsKey(stringExpression)) {
            throw new FileAlreadyOpenException(stringExpression);
        }

        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(stringExpression.getValue()));
        } catch (Exception e) {
            throw new Exception(e);
        }

        fileTable.add(stringExpression, bufferedReader);

        return programState;
    }
    
    @Override
    public GenericStatement deepCopy() {
        return new OpenReadFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("openRFile(%s)", this.expression.toString());
    }
}
