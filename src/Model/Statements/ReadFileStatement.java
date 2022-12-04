package Model.Statements;

import java.io.BufferedReader;

import Model.ProgramState;
import Model.Expressions.GenericExpression;
import Model.InterpreterExceptions.FileNameNotStringException;
import Model.InterpreterExceptions.FileNotOpenException;
import Model.InterpreterExceptions.VariableNotDeclaredException;
import Model.Types.StringType;
import Model.Values.IntegerValue;
import Model.Values.StringValue;

public class ReadFileStatement implements GenericStatement {
    private GenericExpression expression;
    private String variableName;
    
    public ReadFileStatement(GenericExpression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        var symbolTable = programState.getSymbolTable();
        if (!symbolTable.containsKey(this.variableName)){
            throw new VariableNotDeclaredException(this.variableName);
        }

        var heap = programState.getHeap();
        var evaluatedExpression = this.expression.evaluate(symbolTable, heap);
        if (!evaluatedExpression.getType().equals(new StringType())){
            throw new FileNameNotStringException(evaluatedExpression.toString());
        }

        StringValue stringFileName = (StringValue)evaluatedExpression;
        var fileTable = programState.getFileTable();
        
        if (!fileTable.containsKey(stringFileName)){
            throw new FileNotOpenException(stringFileName.getValue());
        }

        var reader = (BufferedReader)fileTable.lookUp(stringFileName);
        String readString = reader.readLine();
        int readInt = readString == null ? 0 : Integer.parseInt(readString);

        symbolTable.update(this.variableName, new IntegerValue(readInt));

        return null;
    }
    
    @Override
    public GenericStatement deepCopy() {
        return new ReadFileStatement(this.expression.deepCopy(), new String(this.variableName));
    }
    
    @Override
    public String toString() {
        return String.format("readFile(%s, %s)", this.expression.toString(), this.variableName);
    }
}
