package Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Model.ProgramState;
import Model.ADT.GenericHeap;
import Model.InterpreterExceptions.HeapNoEntryException;
import Model.Values.GenericValue;
import Model.Values.ReferenceValue;
import Repository.GenericRepository;

public class ProgramController {
    private GenericRepository repository;
    private ExecutorService executor;

    public ProgramController(GenericRepository repository) {
        this.repository = repository;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void oneStepForAllPrograms(List<ProgramState> programList) throws Exception {
        programList.forEach(program -> {
            try {
                this.repository.logProgramStateExecution(program);
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
                this.repository.logProgramStateExecution(program);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        this.repository.setProgramStateList(programList);
    }

    public void allStep() throws Exception {
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

    public void runOneStep() throws Exception {
        List<ProgramState> programList = removeCompletedPrograms(repository.getProgramStateList());
        var heap = programList.get(0).getHeap();
        if (programList.size() > 0) {
            heap.setContent(
                conservativeGarbageCollector(programList)
            );
            oneStepForAllPrograms(programList);
            programList = removeCompletedPrograms(repository.getProgramStateList());
        } else {
            executor.shutdownNow();
            repository.setProgramStateList(programList);
        }
    }

    public List<ProgramState> getProgramStates(){
        return repository.getProgramStateList();
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStateList) throws Exception {
        return programStateList.stream()
        .filter(ProgramState::isNotCompleted)
        .collect(Collectors.toList());
    }


    public Map<Integer, GenericValue> conservativeGarbageCollector(List<ProgramState> programList){
        Collection<GenericValue> listOfSymbols = new HashSet<GenericValue>();
        programList.forEach(program -> {
            listOfSymbols.addAll(program.getSymbolTable().getContent().values());
        });

        var heap = programList.get(0).getHeap();

        var addressesWithReference = getAddressFromSymbolTableAndHeap(listOfSymbols, heap);

        var heapMap = heap.getContent();

        return heapMap.entrySet().stream()
        .filter(e-> addressesWithReference.contains(e.getKey()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddressFromSymbolTableAndHeap(Collection<GenericValue> symbolTableValues, GenericHeap<GenericValue> heap) {
        return symbolTableValues.stream()
        .filter(v -> v instanceof ReferenceValue)
        .flatMap(v -> {
            var listOfAddresses = new ArrayList<Integer>();
            ReferenceValue v1 = (ReferenceValue) v;
            var address = v1.getAddress();
            listOfAddresses.add(address);

            if (address == 0) {
                // Reference Value not initialized
                return listOfAddresses.stream();
            }

            return getNestedAddressesFromHeap(heap, listOfAddresses, address);
        })
        .collect(Collectors.toList());
    }

    private Stream<? extends Integer> getNestedAddressesFromHeap(GenericHeap<GenericValue> heap, ArrayList<Integer> listOfAddresses, int address) {
        try {
            while (heap.lookUp(address) instanceof ReferenceValue) {
                address = ((ReferenceValue) heap.lookUp(address)).getAddress();
                if (heap.containsKey(address) == false) {
                    return listOfAddresses.stream();
                }
                listOfAddresses.add(address);
            }
        } catch (HeapNoEntryException e) {
            System.out.println(e.getMessage());
        }
        return listOfAddresses.stream();
    }
    
    public void restartExecution() {
        this.repository.restartExecution();
        this.executor = Executors.newFixedThreadPool(2);
    }
}
