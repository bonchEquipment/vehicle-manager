package commands;

import java.util.List;

public class CommandHelp implements Command {
    private final List<Command> commandsAvailable;


    public CommandHelp(List<Command> commandsAvailable) {
        this.commandsAvailable = commandsAvailable;
       // this.commandsAvailable.add(0, this);
    }

    @Override
    public String execute() {
        StringBuilder res = new StringBuilder();
        for (Command command : commandsAvailable) {
            res.append(command.getName()).append(" - ").append(command.getDescription()).append("\n");
        }
        return res.toString();
    }

    @Override
    public String getDescription() {
        return "display help for the available commands";
    }


}