package commands;


import utility.CollectionEditor;

/**
 * print the fuel Type field values of all elements in descending order
 */
public class CommandPrintFieldDescendingFuelType implements Command {

    private CollectionEditor collectionEditor;

    public CommandPrintFieldDescendingFuelType(CollectionEditor collectionEditor) {
        this.collectionEditor = collectionEditor;
    }

    /**
     * @return the fuel Type field values of all elements in descending order
     */
    @Override
    public String execute() {
       return collectionEditor.getTheFuelTypeFieldValuesInDescendingOrder();
    }

    @Override
    public String getDescription() {
        return "print the fuel Type field values of all elements in descending order";
    }
}
