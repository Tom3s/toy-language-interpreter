package Repository;

import Model.ProgramState;
import Model.InterpreterExceptions.EmptyListException;

public class ProgramStateRepository implements GenericRepository {

    // private GenericList<ProgramState> programStateList;
    private ProgramState state;

    public ProgramStateRepository() {
        // this.programStateList = new CList<ProgramState>();
    }

    public ProgramStateRepository(ProgramState state) {
        this.state = state;
    }

    @Override
    public ProgramState getCurrentProgramState() throws EmptyListException{
        // return programStateList.getLast();
        return this.state;
    }
    
}
