package commands;

import collection.Vehicle;
import exceptions.NoElementWithSuchIdException;
import utility.CollectionEditor;

import java.util.Scanner;

/**
 * updating element with the specified id
 */
public class CommandUpdate implements CommandWithArgument {

    private CollectionEditor collectionEditor;
    private String userEnteredId;
    private int id;

    public CommandUpdate(CollectionEditor collectionEditor) {
        this.collectionEditor = collectionEditor;
    }

    @Deprecated
    public String execute() {
       return "element was not updated because you are using deprecated method";

    }

    public String execute(Vehicle newVehicle) {

        if (!isArgumentANumber(userEnteredId)) {
            return "invalid id format \"" + userEnteredId + "\", try better";
        } else if (collectionEditor.isCollectionEmpty() || !collectionEditor.isThereAnElementWithSuchId(id))
            return "unable to edit element with id " + id + " because it not exist";

        try {
            Vehicle oldVehicle = collectionEditor.getVehicleById(id);
            newVehicle.setCreationDate(oldVehicle.getCreationDate());
            collectionEditor.updateElement(newVehicle);
            return "the values of the element was successfully update";
        } catch (NoElementWithSuchIdException e) {
            return "this message should never be shown, if it is, fix CommandUpdate";
        }

    }


    public void setArgument(String argument) {
        userEnteredId = argument;
    }

    @Override
    public String getSyntacticsExample() {
        return getName() + " 51";
    }

    private boolean isArgumentANumber(String argument) {
        try {
            id = Integer.parseInt(argument);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public String getDescription() {
        return "updating element with the specified id";
    }

}
