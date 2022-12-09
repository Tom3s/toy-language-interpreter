package Model.Statements;

import Model.ProgramState;
import Model.ADT.GenericDictionary;
import Model.Types.GenericType;

public class CompoundStatement implements GenericStatement {

    private GenericStatement firstStatement;
    private GenericStatement secondStatement;

    public CompoundStatement(GenericStatement firstStatement, GenericStatement secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        var executionStack = programState.getExecutionStack();
        executionStack.push(secondStatement);
        executionStack.push(firstStatement);
        return null;
    }

    @Override
    public GenericDictionary<String, GenericType> typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception {
        return this.secondStatement.typeCheck(this.firstStatement.typeCheck(typeEnvironment));
    }

    @Override
    public GenericStatement deepCopy() {
        return new CompoundStatement(this.firstStatement.deepCopy(), this.secondStatement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("%s; %s", this.firstStatement.toString(), this.secondStatement.toString());
    }

}
