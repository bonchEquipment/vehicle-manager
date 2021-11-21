package commands;

import utility.CollectionEditor;

/**
 * remove all elements with grater id
 */
public class CommandRemoveGrater implements CommandWithArgument {
    private CollectionEditor collectionEditor;
    private String userEnteredId;
    private int id;

    public CommandRemoveGrater(CollectionEditor collectionEditor) {
        this.collectionEditor = collectionEditor;
    }


    @Override
    @Deprecated
    public String execute() {
        if (!isArgumentANumber(userEnteredId)) {
            return "invalid number format \"" + userEnteredId + "\", try better";
        } else {
            if (collectionEditor.removeElementsWithIdGreaterThan(id))
                return "all elements with id greater that " + userEnteredId + " was successfully deleted";
            return "there is no elements with id greater that  " + userEnteredId;
        }
    }

    public String execute(String userLogin) {
        if (!isArgumentANumber(userEnteredId)) {
            return "invalid number format \"" + userEnteredId + "\", try better";
        } else {
            if (collectionEditor.removeElementsWithIdGreaterThan(id, userLogin))
                return "all elements with id greater that " + userEnteredId + " was successfully deleted";
            return "there is no elements with id greater that  " + userEnteredId;
        }
    }


    @Override
    public String getDescription() {
        return "remove all elements with grater id";
    }

    private boolean isArgumentANumber(String argument) {
        try {
            id = (int) Double.parseDouble(argument);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void setArgument(String argument) {
        userEnteredId = argument;
    }

    @Override
    public String getSyntacticsExample() {
        return getName() + " 11";
    }

}
