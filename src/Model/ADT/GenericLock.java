package Model.ADT;

import java.util.Map;

import Model.InterpreterExceptions.LockNoEntryException;

public interface GenericLock<Value> {
    public int add(Value value);
    public Value lookUp(int key) throws LockNoEntryException;
    public void remove(int key) throws LockNoEntryException;
    public boolean containsKey(int key);
    public boolean update(int key, Value newValue) throws LockNoEntryException;
    public Map<Integer, Value> getContent();
    public void setContent(Map<Integer, Value> newContent);
}