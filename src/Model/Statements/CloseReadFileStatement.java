package Model.Statements;

import Model.ProgramState;
import Model.Expressions.GenericExpression;
import Model.InterpreterExceptions.FileNameNotStringException;
import Model.InterpreterExceptions.FileNotOpenException;
import Model.Types.StringType;
import Model.Values.StringValue;

public class CloseReadFileStatement implements GenericStatement {

    GenericExpression expression;

    public CloseReadFileStatement(GenericExpression expression) {
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

        if (!fileTable.containsKey(stringFileName)) {
            throw new FileNotOpenException(stringFileName.getValue());
        }

        var reader = fileTable.lookUp(stringFileName);

        reader.close();

        fileTable.remove(stringFileName);

        return null;
    }

    @Override
    public GenericStatement deepCopy() {
        return new CloseReadFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("closeRFile(%s)", this.expression.toString());
    }

    
    
}
