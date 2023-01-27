package Model.Statements;

import Model.ProgramState;
import Model.ADT.GenericDictionary;
import Model.Types.GenericType;

public class NoOperationStatement implements GenericStatement {
    
    @Override
    public String toString() {
        return "nop";
    }

    @Override
    public GenericStatement deepCopy() {
        return new NoOperationStatement();
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        return null;
    }

    @Override
    public GenericDictionary<String, GenericType> typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception {
        return typeEnvironment;
    }
}
