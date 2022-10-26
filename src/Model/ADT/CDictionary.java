package Model.ADT;

import java.util.HashMap;
import java.util.Map;

import Model.InterpreterExceptions.DictionaryNoEntryException;

public class CDictionary<Key, Value> implements DictionaryInterface<Key, Value> {

    private Map<Key, Value> map;

    public CDictionary(){
        this.map = new HashMap<Key, Value>();
    }

    @Override
    public void put(Key key, Value value) {
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void update(Key key, Value newValue) {
        // TODO Auto-generated method stub
        
    }
}
