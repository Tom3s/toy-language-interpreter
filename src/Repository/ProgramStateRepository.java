package Repository;

import Model.ProgramState;
import Model.ADT.CList;
import Model.ADT.GenericList;
import Model.InterpreterExceptions.EmptyListException;

public class ProgramStateRepository implements GenericRepository {

    private GenericList<ProgramState> programStateList;

    public ProgramStateRepository() {
        this.programStateList = new CList<ProgramState>();
    }

    @Override
    public ProgramState getCurrentProgramState() throws EmptyListException{
        return programStateList.getLast();
    }
    
}
