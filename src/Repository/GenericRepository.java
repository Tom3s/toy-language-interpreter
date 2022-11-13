package Repository;

import Model.ProgramState;

public interface GenericRepository {
    public ProgramState getCurrentProgramState() throws Exception;
    public void logProgramStateExecution() throws Exception;
}
