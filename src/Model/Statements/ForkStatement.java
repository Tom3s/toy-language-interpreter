package Model.Statements;

import Model.ProgramState;
import Model.ADT.CStack;
import Model.ADT.GenericDictionary;
import Model.Types.GenericType;

public class ForkStatement implements GenericStatement {
    private GenericStatement statement;

    public ForkStatement(GenericStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        return new ProgramState(
            new CStack<GenericStatement>(),
            state.getSymbolTable().deepCopy(),
            state.getOut(),
            state.getFileTable(),
            state.getHeap(),
            this.statement
        );
    }

    @Override
    public GenericDictionary<String, GenericType> typeCheck(GenericDictionary<String, GenericType> typeEnvironment) throws Exception {
        this.statement.typeCheck(typeEnvironment.deepCopy());
        return typeEnvironment;
    }

    @Override
    public GenericStatement deepCopy() {
        return new ForkStatement(this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")";
    }
}
