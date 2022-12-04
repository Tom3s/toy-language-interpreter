package Model.ADT;

import java.util.HashMap;
import java.util.Map;

import Model.InterpreterExceptions.DictionaryNoEntryException;

public class CDictionary<Key, Value> implements GenericDictionary<Key, Value> {

    private Map<Key, Value> map;

    public CDictionary(){
        this.map = new HashMap<Key, Value>();
    }

    public CDictionary(Map<Key, Value> map){
        this.map = map;
    }

    @Override
    public void add(Key key, Value value) {
        this.map.put(key, value);
    }

    @Override
    public Value lookUp(Key key) throws DictionaryNoEntryException{
        var value = this.map.get(key);
        if (value == null){
            throw new DictionaryNoEntryException();
        }
        return value;
    }

    @Override
    public boolean containsKey(Key key) {
        return this.map.containsKey(key);
    }

    @Override
    public void update(Key key, Value newValue) throws DictionaryNoEntryException {
        if (!this.containsKey(key)) throw new DictionaryNoEntryException();
        this.map.put(key, newValue);
    }

    
    //i implemented this again bc i didn't notice u already had it
    //it's not even well implemeneted lmao
    // u welcome :)
    // @Override
    // public boolean isVarDef(K key) {
    //     return this.map.containsKey((key));
    // }
    // ty tho :P

    @Override
    public void remove(Key key) throws DictionaryNoEntryException {
        if (!this.containsKey(key)) throw new DictionaryNoEntryException();
        this.map.remove(key);        
    }

    //for the MyStack class u need to add the toString method again, overriding from Object
    @Override
    public String toString(){
        return this.map.toString();
    }
    //i did a thing :D

    @Override
    public Map<Key, Value> getContent() {
        return this.map;
    }

    @Override
    public GenericDictionary<Key, Value> deepCopy() {
        var newMap = new HashMap<Key, Value>();
        for (var entry : this.map.entrySet()){
            newMap.put(entry.getKey(), entry.getValue());
        }
        return new CDictionary<Key, Value>(newMap);
    }

    @Override
    public void putAll(GenericDictionary<Key, Value> dictionary) {
        this.map.putAll(dictionary.getContent());
    }
}









// secret message