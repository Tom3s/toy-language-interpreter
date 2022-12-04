package Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


import Model.ProgramState;
import Model.Values.GenericValue;
import Model.Values.ReferenceValue;
import Repository.GenericRepository;

public class ProgramController {
    private GenericRepository repository;
    private ExecutorService executor;

    public ProgramController(GenericRepository repository) {
        this.repository = repository;
    }

    public void oneStepForAllPrograms(List<ProgramState> programList) throws Exception {
        programList.forEach(program -> {
            try {
                // if (!(program.getExecutionStack().top() instanceof CompoundStatement))
                // {
                    this.repository.logProgramStateExecution(program);
                // }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        List<Callable<ProgramState>> callList = programList.stream()
            .map((ProgramState program) -> (Callable<ProgramState>)(() -> {
                try {
                    return program.oneStep();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return null;
                }
            }))
            .collect(Collectors.toList());

        var newProgramList = executor.invokeAll(callList).stream()
            .map(future -> {
                try {
                    return future.get();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return null;
            })
            .filter(program -> program != null)
            .collect(Collectors.toList());
        programList.addAll(newProgramList);
        
        programList.forEach(program -> {
            try {
                // if ((program.getExecutionStack().isEmpty()))
                // {
                    this.repository.logProgramStateExecution(program);
                // }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        this.repository.setProgramStateList(programList);
    }

    public void allStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programList = removeCompletedPrograms(repository.getProgramStateList());
        var heap = programList.get(0).getHeap();
        while (programList.size() > 0) {
            heap.setContent(
                conservativeGarbageCollector(programList)
            );
            oneStepForAllPrograms(programList);
            programList = removeCompletedPrograms(repository.getProgramStateList());
        }
        executor.shutdownNow();
        repository.setProgramStateList(programList);
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStateList) throws Exception {
        return programStateList.stream()
        .filter(ProgramState::isNotCompleted)
        .collect(Collectors.toList());
    }


    public Map<Integer, GenericValue> conservativeGarbageCollector(List<ProgramState> programList){
        Collection<GenericValue> listOfSymbols = new ArrayList<GenericValue>();
        programList.forEach(program -> {
            listOfSymbols.addAll(program.getSymbolTable().getContent().values());
        });

        var symbolTableAddresses = getAddressFromSymbolTable(listOfSymbols);

        var heap = programList.get(0).getHeap().getContent();

        return heap.entrySet().stream()
        .filter(e->isAddressInSymbolTables(e.getKey(), symbolTableAddresses))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    

    private boolean isAddressInSymbolTables(int address, List<Integer> symbolTableAddresses) {
        return symbolTableAddresses.contains(address);
    }

    public List<Integer> getAddressFromSymbolTable(Collection<GenericValue> symbolTableValues) {
        return symbolTableValues.stream()
        .filter(v -> v instanceof ReferenceValue)
        .map(v -> {
            ReferenceValue v1 = (ReferenceValue) v;
            return v1.getAddress();
        })
        .collect(Collectors.toList());
    }
}
