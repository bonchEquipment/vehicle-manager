package utility;

import collection.Vehicle;
import commands.*;

import java.sql.SQLException;
import java.util.*;

/**
 * class for constructing commands for later use
 */

public class FactoryOfCommands {

    private ArrayList<Command> commandsList;
    private List<Vehicle> collection;
    private CollectionEditor collectionEditor;

    public FactoryOfCommands(List<Vehicle> collection) {
        commandsList = new ArrayList<>();
        this.collection = collection;
        this.collectionEditor = new CollectionEditor(collection);
    }

    /**
     * creates and return list of commands,
     * also some commands needs scanner to be constructed, so we need to give it as a parameter
     *
     * @return list with all commands
     */
    public ArrayList<Command> getCommandList() {
        Command commandAverageOfEnginePower = new CommandAverageOfEnginePower(collectionEditor);
        Command help = new CommandHelp(commandsList);
        Command info = new CommandInfo(collectionEditor);
        Command commandPrintFieldDescendingFuelType = new CommandPrintFieldDescendingFuelType(collectionEditor);
        Command show = new CommandShow(collectionEditor);
        Command removeById = new CommandRemoveById(collectionEditor);
        Command removeGrater = new CommandRemoveGrater(collectionEditor);
        Command removeLast = new CommandRemoveLast(collectionEditor);
        Command countByType = new CommandCountByType(collectionEditor);
        Command removeLower = new CommandRemoveLower(collectionEditor);
        Command add = new CommandAdd(collectionEditor);
        Command update = new CommandUpdate(collectionEditor);
        Command execute_script = new CommandExecuteScript(collectionEditor);

        commandsList.add(commandPrintFieldDescendingFuelType);
        commandsList.add(help);
        commandsList.add(info);
        commandsList.add(show);
        commandsList.add(commandAverageOfEnginePower);
        commandsList.add(removeById);
        commandsList.add(removeGrater);
        commandsList.add(removeLast);
        commandsList.add(countByType);
        commandsList.add(removeLower);
        commandsList.add(add);
        commandsList.add(update);
        commandsList.add(execute_script);
        return commandsList;
    }


    public Optional<Command> getCommandByName(String name) {
        return getCommandList().stream().
                filter(x -> x.getName().
                        equals(name.toLowerCase())).findFirst();
    }

}
