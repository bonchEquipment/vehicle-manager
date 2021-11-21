package commands;

import exceptions.NoElementWithSuchIdException;
import utility.CollectionEditor;

/**
 * remove the last element of the collection
 */
public class CommandRemoveLast implements Command {

    private CollectionEditor collectionEditor;

    public CommandRemoveLast(CollectionEditor collectionEditor) {
        this.collectionEditor = collectionEditor;
    }


    @Override
    public String execute() {
        if (collectionEditor.isCollectionEmpty())
            return "unable to delete last element because there is no elements in the collection";
        collectionEditor.removeLastElement();
        return "last element was successfully removed";

    }



    public String execute(String requesterLogin, String requesterPassword) {
        if (collectionEditor.isCollectionEmpty())
            return "unable to delete last element because there is no elements in the collection";
        try {
            if (collectionEditor.isVehicleOwnedBy(collectionEditor.getLastElement().getId(), requesterLogin)) {
                collectionEditor.removeLastElement();
                return "last element was successfully removed";
            }
        } catch (NoElementWithSuchIdException e) {
            return "unable to delete last element because there is no elements in the collection";
        }
        return "unable to delete last element because element is not owned by you";
    }


    @Override
    public String getDescription() {
        return "remove the last element of the collection";
    }
}
