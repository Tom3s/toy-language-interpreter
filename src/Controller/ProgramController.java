package Controller;

import Model.ProgramState;
import Model.InterpreterExceptions.EmptyStackException;
import Repository.GenericRepository;

public class ProgramController {
    private GenericRepository repository;

    public ProgramController(GenericRepository repository) {
        this.repository = repository;
    }

    public ProgramState oneStep(ProgramState programState) throws Exception {
        var executionStack = programState.getExecutionStack();
        if (executionStack.isEmpty()){
            throw new EmptyStackException();
        }
        var currentStatement = executionStack.pop();
        return currentStatement.execute(programState);
    }

    public void allStep() throws Exception {
        var programState = this.repository.getCurrentProgramState();
        // var executionStack = programState.getExecutionStack();
        while (!programState.getExecutionStack().isEmpty()){
            this.oneStep(programState);
            // TODO display program state
            System.out.println(programState);
        }
    }
}
