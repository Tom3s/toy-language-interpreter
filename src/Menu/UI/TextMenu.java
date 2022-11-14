package Menu.UI;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Menu.Commands.ExitCommand;
import Menu.Commands.GenericCommand;

public class TextMenu {

    private Map<String, GenericCommand> commands;

    public TextMenu() {
        this.commands = new HashMap<String, GenericCommand>();
    }

    public void addCommand(GenericCommand command) {
        this.commands.put(command.getKey(), command);
    }

    private void printMenu() {
        for (GenericCommand command : this.commands.values()) {
            String line = String.format("%4s: %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            this.printMenu();
            System.out.printf("Select option: ");
            String key = scanner.nextLine();
            GenericCommand selectedCommand = this.commands.get(key);
            if (selectedCommand == null) {
                System.out.println("Invalid Option");
                continue;
            }

            if (selectedCommand instanceof ExitCommand) {
                scanner.close();                
            }
            selectedCommand.execute();
        }
    }

}
