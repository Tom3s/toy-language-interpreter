
import Controller.ProgramController;
import Model.ProgramState;
import Model.ADT.CDictionary;
import Model.ADT.CStack;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ArithmeticOperation;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableExpression;
import Model.Statements.AssignStatement;
import Model.Statements.CompoundStatement;
import Model.Statements.GenericStatement;
import Model.Statements.IfStatement;
import Model.Statements.PrintStatement;
import Model.Statements.VariableDeclarationStatement;
import Model.Types.BooleanType;
import Model.Types.IntegerType;
import Model.Values.BooleanValue;
import Model.Values.IntegerValue;
import Repository.ProgramStateRepository;

public class App {
    public static void main(String[] args) throws Exception {
        GenericStatement ex1 = new CompoundStatement(new VariableDeclarationStatement(new IntegerType(), "v"),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
    
        ProgramState state;
        state = new ProgramState(ex1);

        ProgramController controller = new ProgramController(new ProgramStateRepository(state));

        System.out.println("Example1\n");

        controller.allStep();

        System.out.println("\n\nExample2\n");

        GenericStatement ex2 = new CompoundStatement( new VariableDeclarationStatement(new IntegerType(), "a"),
        new CompoundStatement(new VariableDeclarationStatement(new IntegerType(), "b"),
        new CompoundStatement(new AssignStatement("a",
             new ArithmeticExpression(
                 new ValueExpression(new IntegerValue(2)),
                 new ArithmeticExpression(new ValueExpression(new IntegerValue(3)), new ValueExpression(new IntegerValue(5)), ArithmeticOperation.MULTIPLICATION), ArithmeticOperation.ADDITION)),
        new CompoundStatement(new AssignStatement("b",new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new
        IntegerValue(1)), ArithmeticOperation.ADDITION)), new PrintStatement(new VariableExpression("b"))))));
        
        state = new ProgramState(ex2);

        controller = new ProgramController(new ProgramStateRepository(state));
        controller.allStep();

        System.out.println("\n\nExample3\n");

        GenericStatement ex3 = new CompoundStatement(new VariableDeclarationStatement(new BooleanType(), "a"),
        new CompoundStatement(new VariableDeclarationStatement(new IntegerType(), "v"),
        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BooleanValue(true))),
        new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new
        IntegerValue(2))), new AssignStatement("v", new ValueExpression(new IntegerValue(3)))), new PrintStatement(new
        VariableExpression("v"))))));
    
        state = new ProgramState(ex3);

        controller = new ProgramController(new ProgramStateRepository(state));
        controller.allStep();
    }
}

// defaultValue(int) =0
// defaultValue(bool) = false
