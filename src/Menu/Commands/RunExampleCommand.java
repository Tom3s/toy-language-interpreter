package Menu.Commands;

import Controller.ProgramController;

public class RunExampleCommand extends GenericCommand {
    private ProgramController controller;

    public RunExampleCommand(String key, String description, ProgramController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            this.controller.allStep();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ProgramController getController() {
        return controller;
    }

    
}
