package Model.ADT;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import Model.ProgramState;
import Model.InterpreterExceptions.LockNoEntryException;
import Model.InterpreterExceptions.VariableNotDeclaredException;
import Model.InterpreterExceptions.VariableNotLockableException;
import Model.Types.IntegerType;
import Model.Values.IntegerValue;

public class LockDictionary<Value> implements GenericLock<Value>{

    private Map<Integer, Value> map;
    private final Semaphore semaphore = new Semaphore(1);
    
    public LockDictionary(){
        this.map = new ConcurrentHashMap<Integer, Value>();
    }

    @Override
    public int add(Value value) {
        int hash = value.hashCode();
        int key = Integer.valueOf(hash) + 100;
        while (this.map.containsKey(key)){
            key = Integer.valueOf(key + 17).hashCode();
        }
        this.map.put(key, value);
        return key;
    }

    @Override
    public Value lookUp(int key) throws LockNoEntryException{
        var value = this.map.get(key);
        if (value == null){
            throw new LockNoEntryException(key);
        }
        return value;
    }

    @Override
    public boolean containsKey(int key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean update(int key, Value newValue) throws LockNoEntryException {
        this.semaphore.acquireUninterruptibly();{

            if (!this.containsKey(key)) 
                throw new LockNoEntryException();
            
            int oldValue = ((Integer)this.lookUp(key));
            int newIntValue = ((Integer)newValue);

            if (oldValue != -1 &&  newIntValue != -1) {
                this.semaphore.release();
                return false;
            }
        }
        this.map.put(key, newValue);
        this.semaphore.release();

        return true;
    }

    @Override
    public void remove(int key) throws LockNoEntryException {
        if (!this.containsKey(key)) throw new LockNoEntryException();
        this.map.remove(key);        
    }

    @Override
    public String toString(){
        return this.map.toString();
    }

    @Override
    public Map<Integer, Value> getContent() {
        return this.map;
    }

    @Override
    public void setContent(Map<Integer, Value> newContent) {
        this.map = newContent;
    }
    
    public static int verifyLockPreconditions(String id, ProgramState programState) throws VariableNotDeclaredException, VariableNotLockableException, LockNoEntryException, Exception {
        var symbolTable = programState.getSymbolTable();

        if (!symbolTable.containsKey(id)) {
            throw new VariableNotDeclaredException(id);
        }

        var foundLocation = symbolTable.lookUp(id);

        if (!(foundLocation.getType().equals(new IntegerType()))) {
            throw new VariableNotLockableException(id, foundLocation.getType().toString());
        }

        int lockTableLocation = ((IntegerValue)foundLocation).getValue();

        var lockTable = programState.getLockTable();

        if (!lockTable.containsKey(lockTableLocation)) {
            throw new LockNoEntryException(lockTableLocation);
        }

        return lockTableLocation;
    }
}
