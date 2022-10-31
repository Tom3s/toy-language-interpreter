package Model.Statements;

import Model.ProgramState;
import Model.Type.GenericType;

public class VariableDeclarationStatement implements GenericStatement{

    String name;
    GenericType type;
    
    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}
