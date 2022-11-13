package Model.ADT;

import Model.InterpreterExceptions.DictionaryNoEntryException;

public interface GenericDictionary<Key, Value> {
    public void add(Key key, Value value);
    public Value lookUp(Key key) throws DictionaryNoEntryException;
    public void remove(Key key) throws DictionaryNoEntryException;
    public boolean containsKey(Key key);
    public void update(Key key, Value newValue) throws DictionaryNoEntryException;
}
