package Menu.Commands;

public class ExitCommand extends GenericCommand {

    public ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public ProgramController getController() {
        return null;
    }
    
}
