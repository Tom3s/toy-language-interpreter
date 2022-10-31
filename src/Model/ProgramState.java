package Model;

import Model.ADT.GenericDictionary;
import Model.ADT.GenericList;
import Model.ADT.GenericStack;
import Model.Statements.GenericStatement;
import Model.Value.GenericValue;

public class ProgramState {
    private GenericStack<GenericStatement> executionStack; 
    private GenericDictionary<String, GenericValue> symbolTable;
    private GenericList<GenericValue> out;
    private GenericStatement originalProgram;

    public ProgramState(GenericStack<GenericStatement> executionStack, GenericDictionary<String, GenericValue> symbolTable, GenericList<GenericValue> out, GenericStatement originalProgram){
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        
        // implement deep copy
        //this.originalProgram =  (Statement) clone(originalProgram);
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

    public GenericStatement getOriginalProgram() {
        return this.originalProgram;
    }

    
}
