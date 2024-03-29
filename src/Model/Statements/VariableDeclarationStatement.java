package Model.Statements;

import Model.ProgramState;
import Model.ADT.GenericDictionary;
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
        GenericValue value = this.type.defaultValue();

        symbolTable.add(this.name, value);
        return null;
    }
    
    @Override
    public GenericDictionary<String, GenericType> typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception {
        typeEnvironment.add(this.name, this.type);
        return typeEnvironment;
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
