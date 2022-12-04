package Repository;

import java.util.List;

import Model.ProgramState;

public interface GenericRepository {
    public void logProgramStateExecution(ProgramState programState) throws Exception;
    public List<ProgramState> getProgramStateList();
    public void setProgramStateList(List<ProgramState> programStateList);
}
