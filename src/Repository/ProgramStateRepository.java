package Repository;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import Model.ProgramState;
import Model.InterpreterExceptions.NoLogFilePathException;

public class ProgramStateRepository implements GenericRepository {

    private List<ProgramState> programStateList;
    private ProgramState originalState;
    private String logFilePath;

    public ProgramStateRepository() {
        // this.programStateList = new CList<ProgramState>();
        this.programStateList = new ArrayList<ProgramState>();
    }

    public ProgramStateRepository(ProgramState state) {
        this();
        this.programStateList.add(state);
        this.originalState = state;
        this.logFilePath = null;
    }
    
    public ProgramStateRepository(ProgramState state, String logFilePath) {
        this(state);
        this.logFilePath = logFilePath;
        try {
            PrintWriter writer = new PrintWriter(logFilePath);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            // System.out.println("File not found");
        }
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) throws Exception {
        if (this.logFilePath == null){
            throw new NoLogFilePathException();
        }

        var logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
        logFile.append(programState.toString() + "\n");
        logFile.close();
    }

    public List<ProgramState> getProgramStateList() {
        return programStateList;
    }

    public void setProgramStateList(List<ProgramState> programStateList) {
        this.programStateList = programStateList;
    }

    @Override
    public void restartExecution() {
        this.programStateList = new ArrayList<ProgramState>();
        this.originalState.reset();
        this.programStateList.add(this.originalState);

        try {
            PrintWriter writer = new PrintWriter(logFilePath);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            // System.out.println("File not found");
        }
    }
}
