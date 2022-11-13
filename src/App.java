import Controller.ProgramController;
import Model.ProgramState;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableExpression;
import Model.Statements.AssignStatement;
import Model.Statements.CloseReadFileStatement;
import Model.Statements.CompoundStatement;
import Model.Statements.GenericStatement;
import Model.Statements.OpenReadFileStatement;
import Model.Statements.PrintStatement;
import Model.Statements.ReadFileStatement;
import Model.Statements.VariableDeclarationStatement;
import Model.Types.IntegerType;
import Model.Types.StringType;
import Model.Values.StringValue;
import Repository.ProgramStateRepository;

public class App {
    public static void main(String[] args) throws Exception {
        GenericStatement ex1 = new CompoundStatement(
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
        
        var repo = new ProgramStateRepository(new ProgramState(ex1), "ex1.log");

        var controller = new ProgramController(repo);

        controller.allStep();
    }
}

// defaultValue(int) =0
// defaultValue(bool) = false
