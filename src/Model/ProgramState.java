package Model;

import java.io.BufferedReader;
import java.util.EmptyStackException;

import Model.ADT.CDictionary;
import Model.ADT.CHeap;
import Model.ADT.CList;
import Model.ADT.CStack;
import Model.ADT.GenericDictionary;
import Model.ADT.GenericHeap;
import Model.ADT.GenericList;
import Model.ADT.GenericLock;
import Model.ADT.GenericStack;
import Model.ADT.LockDictionary;
import Model.Statements.GenericStatement;
import Model.Values.GenericValue;
import Model.Values.StringValue;

public class ProgramState {
    private GenericStack<GenericStatement> executionStack; 
    private GenericDictionary<String, GenericValue> symbolTable;
    private GenericList<GenericValue> out;
    private GenericDictionary<StringValue, BufferedReader> fileTable;
    private GenericHeap<GenericValue> heap;
    private GenericLock<Integer> lockTable;
    private GenericStatement originalProgram;
    private int programId;
    private static int currentStaticId = -1;

    public ProgramState(
            GenericStack<GenericStatement> executionStack, 
            GenericDictionary<String, GenericValue> symbolTable, 
            GenericList<GenericValue> out, 
            GenericDictionary<StringValue, BufferedReader> fileTable, 
            GenericHeap<GenericValue> heap,
            GenericLock<Integer> lockTable,
            GenericStatement originalProgram
        ) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.lockTable = lockTable;
        this.programId = getNextStaticId();

        this.originalProgram = originalProgram.deepCopy();
        this.executionStack.push(originalProgram);
    }

    public ProgramState(GenericStatement originalProgram) {
        this.executionStack = new CStack<GenericStatement>();
        this.symbolTable = new CDictionary<String, GenericValue>();
        this.out = new CList<GenericValue>();
        this.fileTable = new CDictionary<StringValue, BufferedReader>();
        this.heap = new CHeap<GenericValue>();
        this.lockTable = new LockDictionary<Integer>();
        this.programId = getNextStaticId();
        this.originalProgram = originalProgram.deepCopy();
        this.executionStack.push(originalProgram);
    }

    private static synchronized int getNextStaticId() {
        return currentStaticId++;
    }

    public GenericStack<GenericStatement> getExecutionStack() {
        return this.executionStack;
    }

    public GenericDictionary<String, GenericValue> getSymbolTable() {
        return this.symbolTable;
    }

    public GenericList<GenericValue> getOut() {
        return this.out;
    }

    public GenericDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public GenericHeap<GenericValue> getHeap() {
        return this.heap;
    }

    public GenericLock<Integer> getLockTable() {
        return this.lockTable;
    }

    public GenericStatement getOriginalProgram() {
        return this.originalProgram;
    }

    public int getProgramId() {
        return this.programId;
    }

    public Boolean isNotCompleted() {
        return !this.executionStack.isEmpty();
    }

    /**
     * @description: Executes the next statement in the execution stack
     * @return: The next program state 
     * @throws EmptyStackException if the execution stack is empty
     * @throws Exception if the execution of the statement throws an exception
     */
    public ProgramState oneStep() throws Exception, EmptyStackException {
        if (this.executionStack.isEmpty()) {
            throw new EmptyStackException();
        }
        var currentStatement = this.executionStack.pop();
        return currentStatement.execute(this);
    }

    public void reset() {
        this.executionStack = new CStack<GenericStatement>();
        this.symbolTable = new CDictionary<String, GenericValue>();
        this.out = new CList<GenericValue>();
        this.fileTable = new CDictionary<StringValue, BufferedReader>();
        this.heap = new CHeap<GenericValue>();
        this.lockTable = new LockDictionary<Integer>();
        this.programId = getNextStaticId();
        this.executionStack.push(originalProgram);
    }

    @Override
    public String toString() {
        return 
        "ProgramState Id: " + this.programId +
        ",\nexecutionStack:" + this.executionStack.toString() + 
        ",\nsymbolTable=" + this.symbolTable.toString() + 
        ",\nout=" + this.out.toString() + 
        ",\nfileTable=" + this.fileTable.toString() + 
        ",\nheap=" + this.heap.toString() + 
        ",\nlockTable=" + this.lockTable.toString() +"\n";
    }



    
    
}
