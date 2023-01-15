package Menu.GUI;

import java.util.HashMap;
import java.util.Map;

import Menu.Commands.GenericCommand;

public class Menu {
    private Map<String, GenericCommand> commands;

    public Menu() {
        this.commands = new HashMap<String, GenericCommand>();
    }

    public void addCommand(GenericCommand command) {
        this.commands.put(command.getKey(), command);
    }

    public Map<String, GenericCommand> getCommands() {
        return commands;
    }    
}
