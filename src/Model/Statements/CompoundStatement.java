package Model.Statements;

import Model.ProgramState;

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
        return programState;
    }

    @Override
    public String toString() {
        return String.format("(%s;%s)", this.firstStatement.toString(), this.secondStatement.toString());
    }

}
