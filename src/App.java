import Controller.ProgramController;
import Menu.Commands.ExitCommand;
import Menu.Commands.RunExampleCommand;
import Menu.UI.TextMenu;
import Model.ProgramState;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ArithmeticOperation;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableExpression;
import Model.Statements.AssignStatement;
import Model.Statements.CloseReadFileStatement;
import Model.Statements.CompoundStatement;
import Model.Statements.GenericStatement;
import Model.Statements.IfStatement;
import Model.Statements.OpenReadFileStatement;
import Model.Statements.PrintStatement;
import Model.Statements.ReadFileStatement;
import Model.Statements.VariableDeclarationStatement;
import Model.Types.BooleanType;
import Model.Types.IntegerType;
import Model.Types.StringType;
import Model.Values.BooleanValue;
import Model.Values.IntegerValue;
import Model.Values.StringValue;
import Repository.ProgramStateRepository;

public class App {
    public static void main(String[] args) throws Exception {
        /*Example 1*/
            GenericStatement exampleProgram1 = new CompoundStatement(
                    new VariableDeclarationStatement(new IntegerType(), "v"),
                    new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(2))),
                            new PrintStatement(new VariableExpression("v"))));

            ProgramState exampleState1 = new ProgramState(exampleProgram1);

            ProgramController controller1 = new ProgramController(
                    new ProgramStateRepository(exampleState1, "example1.log"));
        
        /*Example 2*/
            GenericStatement exampleProgram2 = new CompoundStatement(
                    new VariableDeclarationStatement(new IntegerType(), "a"),
                    new CompoundStatement(new VariableDeclarationStatement(new IntegerType(), "b"),
                            new CompoundStatement(new AssignStatement("a",
                                    new ArithmeticExpression(
                                            new ValueExpression(new IntegerValue(2)),
                                            new ArithmeticExpression(new ValueExpression(new IntegerValue(3)),
                                                    new ValueExpression(new IntegerValue(5)),
                                                    ArithmeticOperation.MULTIPLICATION),
                                            ArithmeticOperation.ADDITION)),
                                    new CompoundStatement(
                                            new AssignStatement("b",
                                                    new ArithmeticExpression(new VariableExpression("a"),
                                                            new ValueExpression(new IntegerValue(1)),
                                                            ArithmeticOperation.ADDITION)),
                                            new PrintStatement(new VariableExpression("b"))))));

            ProgramState exampleState2 = new ProgramState(exampleProgram2);

            ProgramController controller2 = new ProgramController(new ProgramStateRepository(exampleState2, "example2.log"));
        
        /*Example 3*/
            GenericStatement exampleProgram3 = new CompoundStatement(
                    new VariableDeclarationStatement(new BooleanType(), "a"),
                    new CompoundStatement(new VariableDeclarationStatement(new IntegerType(), "v"),
                            new CompoundStatement(new AssignStatement("a", new ValueExpression(new BooleanValue(true))),
                                    new CompoundStatement(
                                            new IfStatement(new VariableExpression("a"),
                                                    new AssignStatement("v", new ValueExpression(new IntegerValue(2))),
                                                    new AssignStatement("v", new ValueExpression(new IntegerValue(3)))),
                                            new PrintStatement(new VariableExpression("v"))))));

            ProgramState exampleState3 = new ProgramState(exampleProgram3);

            ProgramController controller3 = new ProgramController(new ProgramStateRepository(exampleState3, "example3.log"));
        
        /*Example 4*/
            GenericStatement exampleProgram4 = new CompoundStatement(
            new VariableDeclarationStatement(
            new StringType(), "varf"), new CompoundStatement(
            new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))), new CompoundStatement(
            new OpenReadFileStatement(new VariableExpression("varf")), new CompoundStatement(
            new VariableDeclarationStatement(new IntegerType(), "varc"), new CompoundStatement(
            new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompoundStatement(
            new PrintStatement(new VariableExpression("varc")), new CompoundStatement(
            new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompoundStatement(
            new PrintStatement(new VariableExpression("varc")), 
            new CloseReadFileStatement(new VariableExpression("varf"))))))))));
            
            ProgramState exampleState4 = new ProgramState(exampleProgram4);

            ProgramController controller4 = new ProgramController(new ProgramStateRepository(exampleState4, "example4.log"));
        

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExampleCommand("1", exampleProgram1.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2", exampleProgram2.toString(), controller2));
        menu.addCommand(new RunExampleCommand("3", exampleProgram3.toString(), controller3));
        menu.addCommand(new RunExampleCommand("4", exampleProgram4.toString(), controller4));
        menu.show();
    }
}

// defaultValue(int) =0
// defaultValue(bool) = false
