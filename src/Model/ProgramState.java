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
import Model.ADT.GenericStack;
import Model.Statements.GenericStatement;
import Model.Values.GenericValue;
import Model.Values.StringValue;

public class ProgramState {
    private GenericStack<GenericStatement> executionStack; 
    private GenericDictionary<String, GenericValue> symbolTable;
    private GenericList<GenericValue> out;
    private GenericDictionary<StringValue, BufferedReader> fileTable;
    private GenericHeap<GenericValue> heap;
    private GenericStatement originalProgram;
    private int programId;
    private static int currentStaticId = -1;

    public ProgramState(
            GenericStack<GenericStatement> executionStack, 
            GenericDictionary<String, GenericValue> symbolTable, 
            GenericList<GenericValue> out, 
            GenericDictionary<StringValue, BufferedReader> fileTable, 
            GenericHeap<GenericValue> heap,
            GenericStatement originalProgram
        ) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
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

    public GenericStatement getOriginalProgram() {
        return this.originalProgram;
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

    @Override
    public String toString() {
        return 
        "ProgramState Id: " + this.programId +
        ",\nexecutionStack:" + this.executionStack.toString() + 
        ",\nsymbolTable=" + this.symbolTable.toString() + 
        ",\nout=" + this.out.toString() + 
        ",\nfileTable=" + this.fileTable.toString() + 
        ",\nheap=" + this.heap.toString() + "\n";
    }



    
    
}
