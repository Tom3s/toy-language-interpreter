package Model.Statements;

import Model.ProgramState;
import Model.Types.GenericType;

public class VariableDeclarationStatement implements GenericStatement{

    String name;
    GenericType type;
    
    public VariableDeclarationStatement(String name, GenericType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public GenericStatement deepCopy() {
        return new VariableDeclarationStatement(new String(this.name), this.type.deepCopy());
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}
