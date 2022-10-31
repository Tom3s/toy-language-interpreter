package Model.Statements;

import Model.ProgramState;

public interface GenericStatement {
    public ProgramState execute(ProgramState programState) throws Exception;
    @Override
    public String toString();
}
