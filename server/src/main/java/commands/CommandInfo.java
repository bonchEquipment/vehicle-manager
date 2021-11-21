package commands;

import utility.CollectionEditor;

/**
 * gives print information about the collection
 */
public class CommandInfo implements Command {
    private CollectionEditor collectionEditor;

    public CommandInfo(CollectionEditor collectionEditor) {
        this.collectionEditor = collectionEditor;
    }

    @Override
    public String execute() {
      return collectionEditor.getInformationAboutCollection();
    }

    @Override
    public String getDescription() {
        return "gives print information about the collection " +
                "(type, date of initialization, number of elements, etc.)";
    }
}
