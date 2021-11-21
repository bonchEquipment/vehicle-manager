package commands;

public interface CommandWithArgument extends Command {

    /**
     * get an argument for later use (for instance command "remove_by_id 36"
     * have argument id with value 36 (in this case)
     */
    void setArgument(String argument);

    /**
     * return SyntacticsExample
     * "count_by_type CHOPPER" (for instance)
     */
    String getSyntacticsExample();
}
