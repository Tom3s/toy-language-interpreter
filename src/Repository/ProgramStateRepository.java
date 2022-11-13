package Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import Model.ProgramState;
import Model.InterpreterExceptions.EmptyListException;
import Model.InterpreterExceptions.NoLogFilePathException;

public class ProgramStateRepository implements GenericRepository {

    // private GenericList<ProgramState> programStateList;
    private ProgramState state;
    private String logFilePath;

    public ProgramStateRepository() {
        // this.programStateList = new CList<ProgramState>();
    }

    public ProgramStateRepository(ProgramState state) {
        this.state = state;
        this.logFilePath = null;
    }

    public ProgramStateRepository(ProgramState state, String logFilePath) {
        this.state = state;
        this.logFilePath = logFilePath;
    }



    @Override
    public ProgramState getCurrentProgramState() throws EmptyListException{
        // return programStateList.getLast();
        return this.state;
    }

    @Override
    public void logProgramStateExecution() throws Exception {
        if (this.logFilePath == null){
            throw new NoLogFilePathException();
        }

        var logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
        logFile.append(this.getCurrentProgramState().toString() + "\n");
        logFile.close();
    }
    
}
