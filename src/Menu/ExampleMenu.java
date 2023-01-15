package Menu;

import Controller.ProgramController;
import Menu.Commands.ExitCommand;
import Menu.Commands.RunExampleCommand;
import Menu.GUI.Menu;
import Model.ProgramState;
import Model.ADT.CDictionary;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ArithmeticOperation;
import Model.Expressions.ReadHeapExpression;
import Model.Expressions.RelationalExpression;
import Model.Expressions.RelationalOperation;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableExpression;
import Model.Statements.AssignStatement;
import Model.Statements.CloseReadFileStatement;
import Model.Statements.CompoundStatement;
import Model.Statements.ForkStatement;
import Model.Statements.GenericStatement;
import Model.Statements.HeapAllocationStatement;
import Model.Statements.HeapWriteStatement;
import Model.Statements.IfStatement;
import Model.Statements.OpenReadFileStatement;
import Model.Statements.PrintStatement;
import Model.Statements.ReadFileStatement;
import Model.Statements.VariableDeclarationStatement;
import Model.Statements.WhileStatement;
import Model.Types.BooleanType;
import Model.Types.GenericType;
import Model.Types.IntegerType;
import Model.Types.ReferenceType;
import Model.Types.StringType;
import Model.Values.BooleanValue;
import Model.Values.IntegerValue;
import Model.Values.StringValue;
import Repository.ProgramStateRepository;

public class ExampleMenu {
    private Menu menu;

    public ExampleMenu() throws Exception {
                /* Example 1 */
        // int v; v=2; Print(v)
GenericStatement exampleProgram1 = new CompoundStatement(
        new VariableDeclarationStatement(new IntegerType(), "v"),new CompoundStatement(
        new AssignStatement("v", new ValueExpression(new IntegerValue(2))),
        new PrintStatement(new VariableExpression("v"))));

        exampleProgram1.typeCheck(new CDictionary<String, GenericType>());

        ProgramState exampleState1 = new ProgramState(exampleProgram1);

        ProgramController controller1 = new ProgramController(
        new ProgramStateRepository(exampleState1, "example1.log"));

        /* Example 2 */
        // int a; int b; a=2+3*5; b=a+1; Print(b)
GenericStatement exampleProgram2 =new CompoundStatement(
        new VariableDeclarationStatement(new IntegerType(), "a"),new CompoundStatement(
        new VariableDeclarationStatement(new IntegerType(), "b"),new CompoundStatement(
        new AssignStatement("a",
        new ArithmeticExpression(
        new ValueExpression(
        new IntegerValue(2)),
        new ArithmeticExpression(
        new ValueExpression(
        new IntegerValue(
        3)),
        new ValueExpression(
        new IntegerValue(
        5)),
        ArithmeticOperation.MULTIPLICATION),
        ArithmeticOperation.ADDITION)),new CompoundStatement(
        new AssignStatement("b",
        new ArithmeticExpression(
        new VariableExpression(
        "a"),
        new ValueExpression(
        new IntegerValue(
        1)),
        ArithmeticOperation.ADDITION)),
        new PrintStatement(
        new VariableExpression(
        "b"))))));

        exampleProgram2.typeCheck(new CDictionary<String, GenericType>());
        ProgramState exampleState2 = new ProgramState(exampleProgram2);

        ProgramController controller2 = new ProgramController(
                new ProgramStateRepository(exampleState2, "example2.log"));

        /* Example 3 */
        // bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
GenericStatement exampleProgram3 =new CompoundStatement(
        new VariableDeclarationStatement(new BooleanType(), "a"),new CompoundStatement(
        new VariableDeclarationStatement(new IntegerType(), "v"),new CompoundStatement(
        new AssignStatement("a",
        new ValueExpression(new BooleanValue(
        true))),new CompoundStatement(
        new IfStatement(new VariableExpression(
        "a"),
        new AssignStatement("v",
        new ValueExpression(
        new IntegerValue(
        2))),
        new AssignStatement("v",
        new ValueExpression(
        new IntegerValue(
        3)))),
        new PrintStatement(
        new VariableExpression(
        "v"))))));

        exampleProgram3.typeCheck(new CDictionary<String, GenericType>());
        ProgramState exampleState3 = new ProgramState(exampleProgram3);

        ProgramController controller3 = new ProgramController(
                new ProgramStateRepository(exampleState3, "example3.log"));

        /* Example 4 */
        // string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc);
        // print(varc); readFile(varf,varc); print(varc); closeRFile(varf)
GenericStatement exampleProgram4 =new CompoundStatement(
        new VariableDeclarationStatement(
        new StringType(), "varf"),new CompoundStatement(
        new AssignStatement("varf",
        new ValueExpression(new StringValue("test.in"))),new CompoundStatement(
        new OpenReadFileStatement(
        new VariableExpression("varf")),new CompoundStatement(
        new VariableDeclarationStatement(
        new IntegerType(),
        "varc"),new CompoundStatement(
        new ReadFileStatement(
        new VariableExpression(
        "varf"),
        "varc"),new CompoundStatement(
        new PrintStatement(
        new VariableExpression(
        "varc")),new CompoundStatement(
        new ReadFileStatement(
        new VariableExpression(
        "varf"),
        "varc"),new CompoundStatement(
        new PrintStatement(
        new VariableExpression(
        "varc")),
        new CloseReadFileStatement(
        new VariableExpression(
        "varf"))))))))));

        exampleProgram4.typeCheck(new CDictionary<String, GenericType>());
        ProgramState exampleState4 = new ProgramState(exampleProgram4);

        ProgramController controller4 = new ProgramController(
                new ProgramStateRepository(exampleState4, "example4.log"));

        /* Example 5 */
        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
GenericStatement exampleProgram5 =new CompoundStatement(
        new VariableDeclarationStatement(new IntegerType(), "v"),new CompoundStatement(
        new AssignStatement("v", new ValueExpression(new IntegerValue(4))),new CompoundStatement(
        new WhileStatement(new RelationalExpression(
        new VariableExpression("v"),
        new ValueExpression(
        new IntegerValue(0)),
        RelationalOperation.GREATER),new CompoundStatement(
        new PrintStatement(
        new VariableExpression(
        "v")),
        new AssignStatement("v",
        new ArithmeticExpression(
        new VariableExpression(
        "v"),
        new ValueExpression(
        new IntegerValue(
        1)),
        ArithmeticOperation.SUBTRACTION)))),
        new PrintStatement(new VariableExpression("v")))));

        exampleProgram5.typeCheck(new CDictionary<String, GenericType>());
        ProgramState exampleState5 = new ProgramState(exampleProgram5);

        ProgramController controller5 = new ProgramController(
                new ProgramStateRepository(exampleState5, "example5.log"));

        /* Example 6 */
        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)));
GenericStatement exampleProgram6 =new CompoundStatement(
        new VariableDeclarationStatement(new ReferenceType(new IntegerType()), "v"),new CompoundStatement(
        new HeapAllocationStatement("v",
        new ValueExpression(new IntegerValue(20))),new CompoundStatement(
        new VariableDeclarationStatement(
        new ReferenceType(new ReferenceType(
        new IntegerType())),
        "a"),new CompoundStatement(
        new HeapAllocationStatement("a",
        new VariableExpression(
        "v")),new CompoundStatement(
        new HeapAllocationStatement(
        "v",
        new ValueExpression(
        new IntegerValue(
        30))),
        new PrintStatement(
        new ReadHeapExpression(
        new ReadHeapExpression(
        new VariableExpression(
        "a")))))))));

        exampleProgram6.typeCheck(new CDictionary<String, GenericType>());
        ProgramState exampleState6 = new ProgramState(exampleProgram6);

        ProgramController controller6 = new ProgramController(
                new ProgramStateRepository(exampleState6, "example6.log"));

        /* Example 7 */
        // int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a)));
        // print(v);print(rH(a))
GenericStatement exampleProgram7 =new CompoundStatement(
new VariableDeclarationStatement(new IntegerType(), "v"),new CompoundStatement(
            new VariableDeclarationStatement(new ReferenceType(new IntegerType()),
            "a"),new CompoundStatement(
            new AssignStatement("v",
            new ValueExpression(
            new IntegerValue(10))),new CompoundStatement(
            new HeapAllocationStatement("a",
            new ValueExpression(
            new IntegerValue(
            22))),new CompoundStatement(
            new ForkStatement(new CompoundStatement(
                new HeapWriteStatement(
                "a",
                new ValueExpression(
                new IntegerValue(
                30))),new CompoundStatement(
                new AssignStatement(
                "v",
                new ValueExpression(
                new IntegerValue(
                32))),new CompoundStatement(
                new PrintStatement(
                new VariableExpression(
                "v")),
                new PrintStatement(
                new ReadHeapExpression(
                new VariableExpression(
                "a"))))))
            ),new CompoundStatement(
            new PrintStatement(
            new VariableExpression(
            "v")),
            new PrintStatement(
            new ReadHeapExpression(
            new VariableExpression(
            "a")))))))));

        exampleProgram7.typeCheck(new CDictionary<String, GenericType>());
        ProgramState exampleState7 = new ProgramState(exampleProgram7);

        ProgramController controller7 = new ProgramController(
            new ProgramStateRepository(exampleState7, "example7.log"));

        /* Example 8 */
        //int v; v=4; (while (v>0) fork((while (v > 0)print(v);v=v-1;));print(v);v=v-1);

        GenericStatement exampleProgram8 = new CompoundStatement(
                new VariableDeclarationStatement(new IntegerType(), "v"),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntegerValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntegerValue(0)),
                                                RelationalOperation.GREATER),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new WhileStatement(
                                                                        new RelationalExpression(
                                                                                new VariableExpression("v"),
                                                                                new ValueExpression(new IntegerValue(0)),
                                                                                RelationalOperation.GREATER),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(
                                                                                        new VariableExpression("v")),
                                                                                new AssignStatement(
                                                                                        "v",
                                                                                        new ArithmeticExpression(
                                                                                                new VariableExpression("v"),
                                                                                                new ValueExpression(new IntegerValue(1)),
                                                                                                ArithmeticOperation.SUBTRACTION)))),
                                                                new PrintStatement(
                                                                        new VariableExpression("v")))),
                                                new AssignStatement(
                                                        "v",
                                                        new ArithmeticExpression(
                                                                new VariableExpression("v"),
                                                                new ValueExpression(new IntegerValue(1)),
                                                                ArithmeticOperation.SUBTRACTION)))),
                                new PrintStatement(new VariableExpression("v")))));
        
        exampleProgram8.typeCheck(new CDictionary<String, GenericType>());
        ProgramState exampleState8 = new ProgramState(exampleProgram8);
        
        ProgramController controller8 = new ProgramController(
                new ProgramStateRepository(exampleState8, "example8.log"));
        



        this.menu = new Menu();
        // this.menu.addCommand(new ExitCommand("0", "Exit"));
        this.menu.addCommand(new RunExampleCommand("1", exampleProgram1.toString(), controller1));
        this.menu.addCommand(new RunExampleCommand("2", exampleProgram2.toString(), controller2));
        this.menu.addCommand(new RunExampleCommand("3", exampleProgram3.toString(), controller3));
        this.menu.addCommand(new RunExampleCommand("4", exampleProgram4.toString(), controller4));
        this.menu.addCommand(new RunExampleCommand("5", exampleProgram5.toString(), controller5));
        this.menu.addCommand(new RunExampleCommand("6", exampleProgram6.toString(), controller6));
        this.menu.addCommand(new RunExampleCommand("7", exampleProgram7.toString(), controller7));
        this.menu.addCommand(new RunExampleCommand("8", exampleProgram8.toString(), controller8));
        
    }

    public Menu getMenu() {
        return menu;
    }

    
}
