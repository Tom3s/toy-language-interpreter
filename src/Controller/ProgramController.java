package Controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Model.ProgramState;
import Model.InterpreterExceptions.EmptyStackException;
import Model.Values.GenericValue;
import Model.Values.ReferenceValue;
import Repository.GenericRepository;

public class ProgramController {
    private GenericRepository repository;

    public ProgramController(GenericRepository repository) {
        this.repository = repository;
    }

    public ProgramState oneStep(ProgramState programState) throws Exception {
        var executionStack = programState.getExecutionStack();
        if (executionStack.isEmpty()) {
            throw new EmptyStackException();
        }
        var currentStatement = executionStack.pop();
        return currentStatement.execute(programState);
    }

    public void allStep() throws Exception {
        var programState = this.repository.getCurrentProgramState();
        this.repository.logProgramStateExecution();
        // var executionStack = programState.getExecutionStack();
        while (!programState.getExecutionStack().isEmpty()) {
            this.oneStep(programState);

            programState.getHeap().setContent(
                unsafeGarbageCollector(
                    getAddressFromSymbolTable(
                        programState.getSymbolTable().getContent().values()
                    ),
                programState.getHeap().getContent()
                )
            );
            this.repository.logProgramStateExecution();
        }
    }

    Map<Integer, GenericValue> unsafeGarbageCollector(List<Integer> symbolTableAddress, Map<Integer, GenericValue> heap){
        return heap.entrySet()
        .stream()
        .filter(e->symbolTableAddress.contains(e.getKey()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddressFromSymbolTable(Collection<GenericValue> symbolTableValues) {
        return symbolTableValues.stream()
        .filter(v -> v instanceof ReferenceValue)
        .map(v -> {
            ReferenceValue v1 = (ReferenceValue) v;
            return v1.getAddress();
        })
        .collect(Collectors.toList());
    }
}
