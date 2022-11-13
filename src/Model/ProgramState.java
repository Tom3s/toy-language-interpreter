package Model;

import java.io.BufferedReader;

import Model.ADT.CDictionary;
import Model.ADT.CList;
import Model.ADT.CStack;
import Model.ADT.GenericDictionary;
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
    private GenericStatement originalProgram;

    public ProgramState(GenericStack<GenericStatement> executionStack, GenericDictionary<String, GenericValue> symbolTable, GenericList<GenericValue> out, GenericDictionary<StringValue, BufferedReader> fileTable, GenericStatement originalProgram){
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        
        // implement deep copy
        this.originalProgram = originalProgram.deepCopy();
        this.executionStack.push(originalProgram);
    }

    public ProgramState(GenericStatement originalProgram) {
        this.executionStack = new CStack<GenericStatement>();
        this.symbolTable = new CDictionary<String, GenericValue>();
        this.out = new CList<GenericValue>();
        this.fileTable = new CDictionary<StringValue, BufferedReader>();
        this.originalProgram = originalProgram.deepCopy();
        this.executionStack.push(originalProgram);
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

    public GenericStatement getOriginalProgram() {
        return this.originalProgram;
    }

    @Override
    public String toString() {
        return "executionStack:" + executionStack.toString() + ",\nsymbolTable=" + symbolTable.toString() + ",\nout=" + out.toString() + "\n";
    }


    
    
}
