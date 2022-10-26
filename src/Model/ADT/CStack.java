package Model.ADT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import Model.InterpreterExceptions.EmptyStackException;

public class CStack<DataType> implements StackInterface<DataType> {

    private Stack<DataType> stack;

    public CStack(){
        this.stack = new Stack<DataType>();
    }

    @Override
    public void push(DataType element) {
        this.stack.push(element);
    }

    @Override
    public DataType pop() throws EmptyStackException {
        if (this.isEmpty()){
            throw new EmptyStackException();
        }
        return this.stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.empty();
    }

    @Override
    public List<DataType> getReversed() {
        var reversedList = new ArrayList<DataType>(this.stack);
        Collections.reverse(reversedList);
        return reversedList;
    }
    
}
