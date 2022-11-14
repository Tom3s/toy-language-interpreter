package Menu.Commands;

public abstract class GenericCommand {
    private String key; 
    private String description;
    
    public GenericCommand(String key, String description) {
        this.key = key;
        this.description = description;
    }
    
    public abstract void execute();

    public String getKey() {
        return this.key;
    }

    public String getDescription() {
        return this.description;
    }
}
