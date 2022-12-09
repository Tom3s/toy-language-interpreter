package Model.Statements;

import Model.ProgramState;
import Model.ADT.GenericDictionary;
import Model.Types.GenericType;

public interface GenericStatement {
    public ProgramState execute(ProgramState programState) throws Exception;
    public GenericDictionary<String, GenericType> typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception;
    public GenericStatement deepCopy();
    @Override
    public String toString();
}
