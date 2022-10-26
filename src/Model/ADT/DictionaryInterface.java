package Model.ADT;

import Model.InterpreterExceptions.DictionaryNoEntryException;

public interface DictionaryInterface<Key, Value> {
    public void put(Key key, Value value);
    public Value lookUp(Key key) throws DictionaryNoEntryException;
    public boolean containsKey(Key key);
    public void update(Key key, Value newValue) throws DictionaryNoEntryException;
}
