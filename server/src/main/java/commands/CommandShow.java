package commands;

import utility.CollectionEditor;

/**
 * class for command "show" realization
 */
public class CommandShow implements Command {

    private CollectionEditor collectionEditor;

    public CommandShow(CollectionEditor collectionEditor) {
        this.collectionEditor = collectionEditor;
    }

    /**
     * @return information about all elements in the collection or says that collection is empty if it is
     */
    @Override
    @Deprecated
    public String execute() {
        return collectionEditor.getStringInterpretationOfCollection();
    }

    /**
     * @return information about all elements in the collection or says that collection is empty if it is
     */
    public String execute(String requesterLogin) {
        return collectionEditor.getStringInterpretationOfCollection(requesterLogin);
    }

    /**
     * @return description of a command
     */
    @Override
    public String getDescription() {
        return "print all elements of the collection to stdout in string representation";
    }
}
