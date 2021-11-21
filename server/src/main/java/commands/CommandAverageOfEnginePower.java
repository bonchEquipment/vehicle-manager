package commands;

import utility.CollectionEditor;

/**
 * count an average engine power of all vehicle in a collection
 */
public class CommandAverageOfEnginePower implements Command {

    private CollectionEditor collectionEditor;

    public CommandAverageOfEnginePower(CollectionEditor collectionEditor) {
        this.collectionEditor = collectionEditor;
    }

    @Override
    public String execute() {
        return collectionEditor.getAverageOfEnginePower();
    }

    @Override
    public String getDescription() {
        return "count an average engine power of all vehicle in a collection";
    }
}
