package Model.Statements;

import java.io.BufferedReader;
import java.io.FileReader;

import Model.ProgramState;
import Model.Expressions.GenericExpression;
import Model.InterpreterExceptions.FileAlreadyOpenException;
import Model.InterpreterExceptions.FileNameNotStringException;
import Model.InterpreterExceptions.FileNotFoundException;
import Model.Types.StringType;
import Model.Values.StringValue;

public class OpenReadFileStatement implements GenericStatement {
    
    GenericExpression expression;

    public OpenReadFileStatement(GenericExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        var symbolTable = programState.getSymbolTable();
        var heap = programState.getHeap();
        var evaluatedExpression = this.expression.evaluate(symbolTable, heap);
        if (!evaluatedExpression.getType().equals(new StringType())){
            throw new FileNameNotStringException(evaluatedExpression.toString());
        }

        StringValue stringFileName = (StringValue)evaluatedExpression;

        var fileTable = programState.getFileTable();

        if (fileTable.containsKey(stringFileName)) {
            throw new FileAlreadyOpenException(stringFileName.getValue());
        }

        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(stringFileName.getValue()));
        } catch (Exception e) {
            throw new FileNotFoundException(stringFileName.getValue());
        }

        fileTable.add(stringFileName, bufferedReader);

        return null;
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
