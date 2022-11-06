package Model.Statements;

import Model.ProgramState;
import Model.InterpreterExceptions.VariableAlreadyDeclaredException;
import Model.Types.GenericType;
import Model.Values.GenericValue;

public class VariableDeclarationStatement implements GenericStatement{

    private GenericType type;
    private String name;
    
    public VariableDeclarationStatement(GenericType type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        var symbolTable = programState.getSymbolTable();
        if (symbolTable.containsKey(this.name)){
            throw new VariableAlreadyDeclaredException(this.name);
        }
        GenericValue value = this.type.createValueOfType();

        symbolTable.add(this.name, value);
        return programState;
    }
    
    @Override
    public GenericStatement deepCopy() {
        return new VariableDeclarationStatement(this.type.deepCopy(), new String(this.name));
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.type.toString(), this.name);
    }
}
