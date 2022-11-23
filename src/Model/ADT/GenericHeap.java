package Model.ADT;

import java.util.Map;

import Model.InterpreterExceptions.HeapNoEntryException;

public interface GenericHeap<Value> {
    public int add(Value value);
    public Value lookUp(int key) throws HeapNoEntryException;
    public void remove(int key) throws HeapNoEntryException;
    public boolean containsKey(int key);
    public void update(int key, Value newValue) throws HeapNoEntryException;
    public Map<Integer, Value> getContent();
    public void setContent(Map<Integer, Value> newContent);
}
